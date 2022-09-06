package app.repository;

import app.model.entity.Folder;
import app.model.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Integer> {
    @Query("""
            select distinct p from Folder p left join p.person person
            where person.id= ?1 and p.path like ?2""")
    Folder getFolderByPersonId( @Nullable Integer id, @Nullable String path);

    @Query("""
            select distinct p from Folder p 
            where p.person.id =?1""")
    List<Folder> getAllFoldersByPersonId(@Nullable Integer personId);
    @Query("""
            select distinct p from Folder p 
            where p.person.id =?1 and p.parentPath like ?2""")
    List<Folder> getAllFoldersByParentPath(@Nullable Integer personId, @Nullable String parentPath);
}
