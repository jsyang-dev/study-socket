package me.study.socket.client.controller;

import lombok.RequiredArgsConstructor;
import me.study.socket.client.dto.RequestDto;
import me.study.socket.client.dto.ResponseDto;
import me.study.socket.client.service.WorkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class WorkController {

    private final WorkService workService;

    @PostMapping("/work")
    public ResponseEntity<ResponseDto> work(@RequestBody @Valid RequestDto requestDto) {
        return ResponseEntity.ok(workService.work(requestDto));
    }
}
