package me.study.socket.client.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import me.study.socket.client.annotation.FullText;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto extends FullTextHead {

    @NotBlank
    @FullText(10)
    private String name;

    @NotNull
    @FullText(10)
    private Long requestAmount;
}
