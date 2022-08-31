package app.controller;

import app.model.dto.CreateNoteDto;
import app.service.FolderService;
import app.service.NoteService;
import app.service.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/user/{user_id}/{path}/**notes")
public class NoteController {

    private NoteService service;
    private FolderService folderService;
    private PersonService personService;

    public NoteController(NoteService service, FolderService folderService, PersonService personService) {
        this.service = service;
        this.folderService = folderService;
        this.personService = personService;
    }

    @GetMapping("/createnote")
    public String newNote(Model model, @ModelAttribute("model") CreateNoteDto noteDto, @PathVariable ("user_id") Integer userId) {
        model.addAttribute("folders", folderService.getList(userId));
        model.addAttribute("persona", personService.getPersonById(userId));
        return "note/create";
    }
    @PostMapping("/createnote")
    public String create(@ModelAttribute("model") @Valid CreateNoteDto noteDto,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "note/create";
        service.save(noteDto);
        return "redirect:/user/"+noteDto.getFolder().getPerson().getId()+"/"+noteDto.getFolder().getPath();

    }
    @PatchMapping("/{id}/edit")
    public String update(@ModelAttribute("model") @Valid CreateNoteDto noteDto,
                         BindingResult bindingResult, @PathVariable ("id") Integer id) {
        if (bindingResult.hasErrors())
            return "note/edit";
        service.editNote(noteDto, id);
        return "redirect:/user/"+noteDto.getFolder().getPerson().getId()+"/"+noteDto.getFolder().getPath();

    }
    @GetMapping("/{id}/edit")
    public String edit(Model model,@PathVariable ("user_id") Integer userId, @PathVariable ("id") Integer id) {
        model.addAttribute("note", service.getNoteById(id));
        model.addAttribute("folders", folderService.getList(userId));
        model.addAttribute("persona", personService.getPersonById(userId));
        return "note/edit";
    }
    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable ("id") Integer id) {
        Integer userId = service.getNoteById(id).getPerson().getId();
        service.deleteNote(id);
        return "redirect:/user/"+userId+"/root";
    }



}
