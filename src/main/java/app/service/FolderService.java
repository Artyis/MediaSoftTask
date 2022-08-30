package app.service;

import app.model.dto.*;
import app.model.entity.Folder;
import app.model.entity.Person;
import app.repository.FolderRepository;
import app.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FolderService {

    private FolderRepository folderRepository;
    private PersonRepository personRepository;
    @Autowired
    public FolderService(FolderRepository folderRepository,PersonRepository personRepository) {
        this.folderRepository = folderRepository;
        this.personRepository = personRepository;
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
        if (createDto.getParentPath()!=null) {
            return Folder.builder()
                    .name(createDto.getName())
                    .path(createDto.getParentPath() + "/" + createDto.getName())
                    .person(createDto.getPerson())
                    .parentPath(createDto.getParentPath())
                    .build();
        }
        return Folder.builder()
                .name(createDto.getName())
                .path("root")
                .person(createDto.getPerson())
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
    public List <Folder> getList(Integer id) {
        return folderRepository.getAllFoldersByPersonId(id);
    }
    public List <Folder> getListFoldersPath(Integer id, String parentPath) {
        return folderRepository.getAllFoldersByParentPath(id,parentPath);
    }




}
