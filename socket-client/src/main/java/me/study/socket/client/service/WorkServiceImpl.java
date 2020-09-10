package me.study.socket.client.service;

import lombok.RequiredArgsConstructor;
import me.study.socket.client.common.FullTextConverter;
import me.study.socket.client.common.SocketSender;
import me.study.socket.client.dto.RequestDto;
import me.study.socket.client.dto.ResponseDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkServiceImpl implements WorkService {

    private final SocketSender socketSender;
    private final FullTextConverter fullTextUtil;

    @Override
    public ResponseDto work(RequestDto requestDto) {

        String resFullText = socketSender.send(fullTextUtil.objectToFullText(requestDto));
        return fullTextUtil.fullTextToObject(resFullText, ResponseDto.class);
    }
}
