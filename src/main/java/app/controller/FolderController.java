package app.controller;

import app.model.dto.CreateFolderDto;
import app.service.FolderService;
import app.service.NoteService;
import app.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.AntPathMatcher;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/user/{user_id}/{path}")
public class FolderController {

    private FolderService service;
    private NoteService noteService;
    private PersonService personService;

    public FolderController(FolderService service, NoteService noteService, PersonService personService) {
        this.service = service;
        this.noteService = noteService;
        this.personService = personService;
    }

    @GetMapping()
    public String index(Model model, @PathVariable("user_id") Integer id, @PathVariable("path") String path) {
        model.addAttribute("folder", service.getFolderByPersonId(id,path));
        model.addAttribute("folders", service.getListFoldersPath(id,service.getFolderByPersonId(id,path).getParentPath()));
        model.addAttribute("notesDo", noteService.getSortListDoDate(id,path));
        model.addAttribute("notesDone", noteService.getSortListDoneDate(id,path));
        return "folder/index";
    }
    @GetMapping("/{paths}/**")
    public String show(Model model, @PathVariable("user_id") Integer id, HttpServletRequest request) {
        String path = new AntPathMatcher()
                .extractPathWithinPattern( "/{path}/{paths}/**", request.getRequestURI());
        if(service.getFolderByPersonId(id,path)!=null){
            model.addAttribute("folder", service.getFolderByPersonId(id,path));
            model.addAttribute("folders", service.getListFoldersPath(id,service.getFolderByPersonId(id,path).getParentPath()));
            model.addAttribute("notesDo", noteService.getSortListDoDate(id,path));
            model.addAttribute("notesDone", noteService.getSortListDoneDate(id,path));
            return "folder/index";
        }
        return "/404";

    }
    @GetMapping("/createfolder")
    public String newFolder(Model model,@ModelAttribute("model") CreateFolderDto folderDto, @PathVariable ("user_id") Integer userId) {
        model.addAttribute("folders", service.getList(userId));
        model.addAttribute("persona", personService.getPersonById(userId));
        return "folder/create";
    }
    @PostMapping("/createfolder")
    public String create(@ModelAttribute("model") @Valid CreateFolderDto folderDto,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "folder/create";
        System.out.println(folderDto.getPerson().getId() + " "+ folderDto.getName());
            service.save(folderDto);
            return "redirect:/user/"+folderDto.getPerson().getId()+"/"+folderDto.getParentPath()+"/"+folderDto.getName();

    }
    @PatchMapping("/edit")
    public String update(@ModelAttribute("model") @Valid CreateFolderDto folderDto,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "folder/create";
        service.editFolder(folderDto);
        return "redirect:/user/"+folderDto.getPerson().getId()+"/"+folderDto.getParentPath()+"/"+folderDto.getName();

    }
    @GetMapping("/edit")
    public String edit(Model model, @PathVariable ("user_id") Integer userId,HttpServletRequest request) {
        String path = new AntPathMatcher()
                .extractPathWithinPattern( "/{path}/{paths}/**", request.getRequestURI());
        model.addAttribute("folder", service.getFolderByPersonId(userId,path));
        model.addAttribute("folders", service.getList(userId));
        return "folder/create";
    }
    @DeleteMapping("/{paths}/**")
    public String delete(@PathVariable ("user_id") Integer userId, HttpServletRequest request) {
        String path = new AntPathMatcher()
                .extractPathWithinPattern( "/{path}/{paths}/**", request.getRequestURI());
        service.deleteFolder(userId,path);
        return "redirect:/user/"+userId+"/root";
    }

}
