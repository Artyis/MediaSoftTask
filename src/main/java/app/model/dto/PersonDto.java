package app.model.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PersonDto {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private List<NotesListDto> notes;
    private List<FoldersListDto> folders;
}
