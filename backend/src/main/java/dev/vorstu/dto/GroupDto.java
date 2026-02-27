package dev.vorstu.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GroupDto {
    private Long id;
    @NotBlank(message = "Название группы не может быть пустым")
    private String name;
}
