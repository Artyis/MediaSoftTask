package app.model.dto;


import app.model.entity.Folder;
import app.model.entity.Person;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
@Builder
public class CreateNoteDto {
    @Size(min = 1, max = 20, message = "Необходимая длина имени от 1 до 20 символов")
    @Pattern(regexp = "[а-яА-ЯёЁA-Za-z\s-]+", message = "Используйте только латинские и русские символы")
    private String title;
    @Size(min = 1, max = 1500, message = "Необходимая длина имени от 1 до 1500 символов")
    @Pattern(regexp = "[а-яА-ЯёЁA-Za-z\s-]+", message = "Используйте только латинские и русские символы")
    private String description;
    private String status;
    private Folder folder;
    private Person person;
}
