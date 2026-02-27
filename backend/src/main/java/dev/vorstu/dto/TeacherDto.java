package dev.vorstu.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.Set;

@Data
public class TeacherDto {
    private Long id;
    @NotBlank(message = "ФИО не может быть пустым")
    private String fio;
    private Set <GroupDto> groups;
}
