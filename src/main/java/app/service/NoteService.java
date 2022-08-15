package app.service;

import app.model.dto.CreateNoteDto;
import app.model.dto.NoteDto;
import app.model.dto.NotesListDto;
import app.model.entity.Note;
import app.repository.FolderRepository;
import app.repository.NoteRepository;
import app.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NoteService {

    private NoteRepository noteRepository;
    private PersonRepository personRepository;
    private FolderRepository folderRepository;
    @Autowired
    public NoteService(NoteRepository noteRepository, PersonRepository personRepository, FolderRepository folderRepository) {
        this.noteRepository = noteRepository;
        this.personRepository = personRepository;
        this.folderRepository = folderRepository;
    }

    public void save(CreateNoteDto createDto) {
        Note note = getNote(createDto);
        noteRepository.save(note);
    }
    private Note getNote(CreateNoteDto createDto) {
        return Note.builder()
                .title(createDto.getTitle())
                .description(createDto.getDescription())
                .status(createDto.getStatus())
                .date(Date.valueOf(createDto.getDate().toLocalDate().toString()))
                .folder(createDto.getFolder())
                .build();
    }
    public Note getNoteById(Integer id) {
        return noteRepository.getReferenceById(id);
    }

    private NoteDto createNoteDto(Note note) {
        return NoteDto.builder()
                .date(note.getDate().toLocalDate().toString())
                .title(note.getTitle())
                .description(note.getDescription())
                .status(note.getStatus())
                .person(note.getPerson())
                .parentFolder(note.getFolder())
                .build();
    }
    public void editNote (CreateNoteDto createDto, Integer id){
        Note note = getNoteById(id);
        note.setTitle(createDto.getTitle());
        note.setDescription(createDto.getDescription());
        note.setStatus(createDto.getStatus());
        note.setDate(createDto.getDate());
        note.setFolder(createDto.getFolder());
        noteRepository.save(note);
    }
    public void deleteNote (Integer id){
        Note note = getNoteById(id);
        noteRepository.delete(note);
    }
       public NotesListDto getList(Integer id, String path) {
        List<NoteDto> notes = new ArrayList<>();
        folderRepository.getFolderByPersonId(id,path).getNotes().forEach(note -> notes.add(createNoteDto(note)));
        return new NotesListDto(notes);
    }
    public NotesListDto getSortListDoDate(Integer id, String path) {
        List<NoteDto> notes = new ArrayList<>();
        noteRepository.getAllNotesSortingDoDate(id,path,"do").forEach(note -> notes.add(createNoteDto(note)));
        return new NotesListDto(notes);
    }
    public NotesListDto getSortListDoneDate(Integer id, String path) {
        List<NoteDto> notes = new ArrayList<>();
        noteRepository.getAllNotesSortingDoDate(id,path,"do").forEach(note -> notes.add(createNoteDto(note)));
        return new NotesListDto(notes);
    }




}
