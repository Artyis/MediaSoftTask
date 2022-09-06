package app.repository;

import app.model.entity.Folder;
import app.model.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

    @Query("""
            select distinct p from Note p 
            where p.person.id =?1 and p.folder.path like ?2 and p.status like ?3 order by p.date""")
    List<Note> getAllNotesSortingDoDate(@Nullable Integer personId, @Nullable String path, @Nullable String status);
}
