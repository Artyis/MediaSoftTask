package app.model.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Setter
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private Date date;
    private String status;
    @ManyToOne(cascade = CascadeType.ALL)
    private Person person;
    @ManyToOne(cascade = CascadeType.MERGE)
    private Folder folder;

}
