package me.study.socket.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import me.study.socket.client.annotation.FullText;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FullTextHead {

    @FullText(4)
    private String workType;

    @FullText(14)
    private LocalDateTime sendDate;
}
