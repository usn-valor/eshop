package ru.home.contrroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AdminController {

    public String indexPage() {
        return "index";
    }
}
