package me.study.socket.client.dto;

import lombok.Getter;
import lombok.ToString;
import me.study.socket.client.annotation.FullText;

@Getter
@ToString
public class ResponseDto extends FullTextHead {

    @FullText(1)
    private Boolean approvalYn;

    @FullText(10)
    private Long approvalAmount;
}
