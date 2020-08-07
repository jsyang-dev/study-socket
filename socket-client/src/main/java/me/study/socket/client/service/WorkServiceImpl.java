package me.study.socket.client.service;

import lombok.RequiredArgsConstructor;
import me.study.socket.client.common.FullTextUtil;
import me.study.socket.client.common.SocketSender;
import me.study.socket.client.dto.RequestDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkServiceImpl implements WorkService {

    private final SocketSender socketSender;
    private final FullTextUtil fullTextUtil;

    @Override
    public String work(RequestDto requestDto) {
        return socketSender.send(fullTextUtil.makeFullText(requestDto));
    }
}
