package app.model.dto;

import app.model.entity.Folder;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
public class CreatePersonDto  {
    @Size(min = 2, max = 20, message = "Необходимая длина имени от 2 до 20 символов")
    @Pattern(regexp = "[а-яА-ЯёЁA-Za-z\s-]+", message = "Используйте только латинские и русские символы")
    private String name;
    @Email(message = "Введите корректный Email")
    private String email;
    private String password;
}
