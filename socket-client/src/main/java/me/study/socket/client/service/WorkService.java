package me.study.socket.client.service;

import me.study.socket.client.dto.RequestDto;
import me.study.socket.client.dto.ResponseDto;

public interface WorkService {
    ResponseDto work(RequestDto requestDto);
}
