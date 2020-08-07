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
public class FullTextUtil {

    public static final int LEN_FIELD_SIZE = 4;

    public <T> String makeFullText(T instance) {

        StringBuilder fullText = new StringBuilder();
        Field[] superFields = instance.getClass().getSuperclass().getDeclaredFields();
        Field[] fields = instance.getClass().getDeclaredFields();
        AtomicInteger length = new AtomicInteger();

        buildFullText(instance, superFields, fullText, length);
        buildFullText(instance, fields, fullText, length);

        return fullText.insert(0, fillNumber(String.valueOf(length.addAndGet(LEN_FIELD_SIZE)), LEN_FIELD_SIZE)).toString();
    }

    private <T> void buildFullText(T instance, Field[] superFields, StringBuilder sb, AtomicInteger length) {

        for (Field field : superFields) {
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
            log.error(e.getMessage());
        }

        return fillText("_", length);
    }

    private String fillText(String text, int length) {
        return fillChars(text, length, ' ', true);
    }

    private String fillNumber(String text, int length) {
        return fillChars(text, length, '0', false);
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
                sb.append(fillChar);
            } else {
                sb.insert(0, fillChar);
            }
        }
        return sb.toString();
    }
}
