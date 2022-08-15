package app.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Folder {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String parentPath;
    private String path;
    @ManyToOne(cascade = {
            CascadeType.MERGE
    })
    private Person person;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "folder")
    private List<Note> notes;
}
