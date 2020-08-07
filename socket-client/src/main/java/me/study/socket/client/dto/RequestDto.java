package me.study.socket.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import me.study.socket.client.annotation.FullText;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto extends FullTextHead {

    @FullText(10)
    private String name;

    @FullText(10)
    private Long applyAmount;
}
