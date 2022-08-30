package app.controller;

import app.model.dto.CreatePersonDto;
import app.model.entity.Person;
import app.service.PersonService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class PersonController {

    private PersonService service;

    public PersonController(PersonService service) {
        this.service = service;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("person", service.getAll());
        return "person/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("person", service.getPersonDto(id));
        return "person/show";
    }
    @GetMapping("/registration")
    public String newPerson(@AuthenticationPrincipal @ModelAttribute("person") CreatePersonDto personDto) {
        return "person/create";
    }
    @PostMapping("/registration")
    public String create( @AuthenticationPrincipal @ModelAttribute("person") @Valid CreatePersonDto personDto,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "person/create";
        System.out.println(personDto.toString());
        service.save(personDto);
        return "redirect:/login";
    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("person", service.getPersonDto(id));
        return "person/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid CreatePersonDto personDto, BindingResult bindingResult,
                         @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors())
            return "person/edit";

        service.editPerson(personDto,id);
        return "redirect:/"+id+"/notes";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Integer id) {
        service.deletePerson(id);
        return "redirect:/registration";
    }

}
