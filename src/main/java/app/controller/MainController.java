package app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("title", "Вроде запустил");
        return "index";
    }
    @GetMapping("/main")
    public String main(Model model) {
        model.addAttribute("title", "Вроде запустил мейн");
        return "main";
    }
}
