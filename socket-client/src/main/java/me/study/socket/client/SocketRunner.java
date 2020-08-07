package me.study.socket.client;

import lombok.RequiredArgsConstructor;
import me.study.socket.client.common.FullTextUtil;
import me.study.socket.client.common.SocketSender;
import me.study.socket.client.dto.RequestDto;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.time.LocalDateTime;

//@Component
@RequiredArgsConstructor
public class SocketRunner implements ApplicationRunner {

    private final SocketSender socketSender;

    @Override
    public void run(ApplicationArguments args) {

        FullTextUtil fullTextUtil = new FullTextUtil();
        RequestDto requestDto = RequestDto.builder()
                .workType("A001")
                .name("YANG")
                .sendDate(LocalDateTime.now())
                .applyAmount(100000000L)
                .build();

        System.out.println(socketSender.send(fullTextUtil.makeFullText(requestDto)));
    }
}
