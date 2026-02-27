package dev.vorstu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudentDto {
    private Long id;
    @NotBlank(message = "ФИО не может быть пустым")
    @Size(min = 3, max = 50, message = "ФИО должно быть от 3 до 50 символов")
    private String fio;
    @NotBlank(message = "Телефон обязателен")
    private String phoneNumber;
    private GroupDto group;
}
