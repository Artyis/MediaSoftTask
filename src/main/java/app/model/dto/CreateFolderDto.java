package app.model.dto;

import app.model.entity.Person;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
@Builder
public class CreateFolderDto {
    @Pattern(regexp = "[a-z]+", message = "Используйте только латинские символы в нижнем регистре")
    private String name;
    private String path;
    private Person person;
    private String parentPath;

}
