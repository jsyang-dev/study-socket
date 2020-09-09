package me.study.socket.client.common;

import lombok.extern.slf4j.Slf4j;
import me.study.socket.client.annotation.FullText;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class FullTextConverter {

    public static final int SIZE_OF_LEN_FIELD = 4;

    public <T> String dtoToFullText(T instance) {

        StringBuilder fullText = new StringBuilder();
        Field[] superFields = instance.getClass().getSuperclass().getDeclaredFields();
        Field[] fields = instance.getClass().getDeclaredFields();
        AtomicInteger length = new AtomicInteger();

        makeFullText(instance, superFields, fullText, length);
        makeFullText(instance, fields, fullText, length);

        return fullText.insert(0, fillNumber(String.valueOf(length.addAndGet(SIZE_OF_LEN_FIELD)), SIZE_OF_LEN_FIELD)).toString();
    }

    private <T> void makeFullText(T instance, Field[] fields, StringBuilder sb, AtomicInteger length) {

        for (Field field : fields) {
            Optional.ofNullable(field.getAnnotation(FullText.class))
                    .ifPresent(annotation -> {
                        String fieldValue = getFieldValue(field, instance, annotation.value());
                        sb.append(fieldValue);
                        length.addAndGet(annotation.value());
                    });
        }
    }

    private <T> String getFieldValue(Field field, T instance, int length) {

        Class<?> type = field.getType();
        field.setAccessible(true);

        try {
            if (type == LocalDateTime.class) {
                LocalDateTime localDateTime = (LocalDateTime) field.get(instance);
                return fillText(localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")), length);
            }
            if (type == Integer.class || type == Long.class) {
                return fillNumber(String.valueOf(field.get(instance)), length);
            }
            return fillText((String) field.get(instance), length);

        } catch (IllegalAccessException e) {
            log.error(e.toString());
        }

        return fillText("_", length);
    }

    private String fillText(String text, int length) {
        return fillChars(text, length, ' ', false);
    }

    private String fillNumber(String text, int length) {
        return fillChars(text, length, '0', true);
    }

    private String fillChars(String text, int length, char fillChar, boolean alignRight) {

        if (text.length() == length) {
            return text;
        }
        if (text.length() > length) {
            return text.substring(0, length);
        }

        StringBuilder sb = new StringBuilder(text);
        while (sb.length() < length) {
            if (alignRight) {
                sb.insert(0, fillChar);
            } else {
                sb.append(fillChar);
            }
        }
        return sb.toString();
    }

    public <T> T fullTextToDto(String fullText, Class<T> clazz) {

        final T instance;
        Field[] superFields = clazz.getSuperclass().getDeclaredFields();
        Field[] fields = clazz.getDeclaredFields();
        AtomicInteger position = new AtomicInteger(SIZE_OF_LEN_FIELD);

        int length = Integer.parseInt(fullText.substring(0, SIZE_OF_LEN_FIELD));
        if (length != fullText.length()) {
            throw new RuntimeException("Invalid full text");
        }

        try {
            instance = clazz.newInstance();
        } catch (Exception e) {
            log.error(e.toString());
            throw new RuntimeException("Cannot make instance");
        }

        makeDto(fullText, instance, superFields, position);
        makeDto(fullText, instance, fields, position);

        return instance;
    }

    private <T> void makeDto(String fullText, T instance, Field[] superFields, AtomicInteger position) {

        for (Field field : superFields) {
            Optional.ofNullable(field.getAnnotation(FullText.class))
                    .ifPresent(annotation -> {
                        field.setAccessible(true);
                        try {
                            field.set(instance,
                                    castFieldValue(
                                            fullText.substring(position.get(), position.get() + annotation.value()),
                                            field.getType()
                                    )
                            );
                        } catch (IllegalAccessException e) {
                            log.error(e.toString());
                        }
                        position.addAndGet(annotation.value());
                    });
        }
    }

    private <T> T castFieldValue(String value, Class<T> type) {

        if (type == LocalDateTime.class) {
            return type.cast(LocalDateTime.parse(value, DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        }
        if (type == Boolean.class) {
            return type.cast("1".equals(value)  ? Boolean.TRUE : Boolean.FALSE);
        }
        if (type == Long.class) {
            return type.cast(Long.valueOf(value));
        }
        return type.cast(value);
    }
}
