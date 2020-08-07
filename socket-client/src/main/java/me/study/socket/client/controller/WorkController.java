package me.study.socket.client.controller;

import lombok.RequiredArgsConstructor;
import me.study.socket.client.dto.RequestDto;
import me.study.socket.client.service.WorkService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WorkController {

    private final WorkService workService;

    @PostMapping("/work")
    public String work(@RequestBody RequestDto requestDto) {

        return workService.work(requestDto);
    }
}
