package me.study.socket.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import me.study.socket.client.annotation.FullText;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CommonDto {

    @NotBlank
    @FullText(4)
    private String workType;

    @FullText(14)
    @Builder.Default
    private LocalDateTime sendDate = LocalDateTime.now();
}
