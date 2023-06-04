package com.example.MyBMS;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("book")
public class BookController {

    @Autowired
    BmsRepository bmsRepository;

    @GetMapping("find")
    public String find(Model model, SearchForm form) {
        model.addAttribute("searchForm", form);
        model.addAttribute("books", new ArrayList<Book>());
        return "book/find";
    }

    @GetMapping("view/{bookID}")
    public String view(Model model, @PathVariable("bookID") int bookID) {
        Book book = bmsRepository.findById(bookID);
        model.addAttribute("book", book);
        return "book/view";
    }

    @PostMapping("search")
    public String search(Model model, @Validated SearchForm form, BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("books", new ArrayList<Book>());
        } else {
            ArrayList<Book> books = bmsRepository.search(form.getBook(), form.getAuthor(), form.getPublisher(), form.getISBN(), form.getClassCode());
            model.addAttribute("books", books);
        }
        model.addAttribute("searchForm", form);
        return "book/find";
    }
    
}
