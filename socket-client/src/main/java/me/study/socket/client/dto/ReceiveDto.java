package me.study.socket.client.dto;

import me.study.socket.client.annotation.FullText;

public class ReceiveDto {

    @FullText(1)
    private boolean approvalYn;

    @FullText(10)
    private Long approvalAmount;
}
