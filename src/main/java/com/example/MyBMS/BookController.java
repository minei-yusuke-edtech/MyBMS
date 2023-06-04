package com.example.MyBMS;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("book")
public class BookController {

    @GetMapping("find")
    public String find(Model model) {
        return "book/find";
    }

    @GetMapping("view")
    public String view(Model model) {
        return "book/view";
    }
    
}
