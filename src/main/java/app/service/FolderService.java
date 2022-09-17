package app.service;

import app.model.dto.*;
import app.model.entity.Folder;
import app.model.entity.Note;
import app.model.entity.Person;
import app.repository.FolderRepository;
import app.repository.NoteRepository;
import app.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FolderService {

    private FolderRepository folderRepository;
    private PersonRepository personRepository;
    private NoteRepository noteRepository;

    @Autowired
    public FolderService(FolderRepository folderRepository, PersonRepository personRepository, NoteRepository noteRepository) {
        this.folderRepository = folderRepository;
        this.personRepository = personRepository;
        this.noteRepository = noteRepository;
    }
    @Transactional
    public void save(CreateFolderDto createDto) throws RuntimeException {
        Folder folder = getFolder(createDto);
        if(checkNameInFolders(folder.getPerson().getId(),folder.getPath())){
            folderRepository.save(folder);
        }else {
            throw new RuntimeException("Это имя папки уже есть");
        }


    }

    private Folder getFolder(CreateFolderDto createDto) {
        Person person = personRepository.getReferenceById(createDto.getPerson().getId());
        if (createDto.getParentPath()!=null) {
            return Folder.builder()
                    .name(createDto.getName())
                    .path(createDto.getParentPath() + "/" + createDto.getName())
                    .person(person)
                    .parentPath(createDto.getParentPath())
                    .build();
        }
        return Folder.builder()
                .name(createDto.getName())
                .path("root")
                .person(person)
                .parentPath(createDto.getParentPath())
                .build();
    }

    public Folder getFolderByPersonId(Integer personId, String path) {
        return folderRepository.getFolderByPersonId (personId,path);
    }
    public boolean checkNameInFolders (Integer personId, String path){
        if(folderRepository.getFolderByPersonId (personId,path)!=null){
            return false;
        }else {
            return true;
        }
    }

    public void editFolder (CreateFolderDto createDto){
        Folder folder = getFolderByPersonId(createDto.getPerson().getId(), createDto.getPath());
        folder.setName(createDto.getName());
        folder.setParentPath(createDto.getParentPath());
        folder.setPath(createDto.getPath());
        folderRepository.save(folder);
    }
    public void deleteFolder (Integer personId, String path){
        Folder folder =  getFolderByPersonId(personId,path);
        folderRepository.delete(folder);
    }
    //Тут начинаются вопросы по "рекурсии"
    public FoldersListDto getList(Integer id) {
        List <FolderDto> folders = new ArrayList<>();
        folderRepository.getAllFoldersByPersonId(id).forEach(folder -> folders.add(createNewFolderDto(folder)));
        return new FoldersListDto (folders);
    }
    public FolderDto createNewFolderDto (Folder folder){
        return FolderDto.builder()
                .id(folder.getId())
                .parentPath(folder.getParentPath())
                .name(folder.getName())
                .notes(getNoteListDoDto(folder))
                .path(folder.getPath())
                .build();
    }
    public NotesListDto getNoteListDoDto(Folder folder){
        List<NoteDto> notes =new ArrayList<>();
        noteRepository.getAllNotesSortingDoDate(folder.getPerson().getId(), folder.getPath(), "do").forEach(note->notes.add(createNewNoteDto(note)));
        return new NotesListDto(notes);
    }
    //В каждом сервисе для всех элменетов дто нужно будет прописывать конвертер дто->энтити ?
    public NoteDto createNewNoteDto (Note note){
        return NoteDto.builder()
                .status(note.getStatus())
                .title(note.getTitle())
                .description(note.getDescription())
                .build();
    }


    public List <FoldersListDto> getListFoldersPath(Integer id, String parentPath) {
        return folderRepository.getAllFoldersByParentPath(id,parentPath);
    }




}
