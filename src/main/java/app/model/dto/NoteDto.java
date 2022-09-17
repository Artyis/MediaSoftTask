package app.model.dto;

import app.model.entity.Folder;
import app.model.entity.Person;
import lombok.*;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteDto {
    private Integer id;
    private String title;
    private String description;
    private String date;
    private String status;
    private PersonDto person;
    private FolderDto parentFolder;
}
