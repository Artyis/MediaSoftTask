package app.model.dto;

import app.model.entity.Folder;
import app.model.entity.Note;
import app.model.entity.Person;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FolderDto {
    private Integer id;
    private String name;
    private String parentPath;
    private String path;
    private PersonDto person;
    private List<NoteDto> notes;
}
