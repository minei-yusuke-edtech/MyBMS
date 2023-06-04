package com.example.MyBMS;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("guest")
public class GuestController {

    @Autowired
    BmsRepository bmsRepository;
    
    @GetMapping("myPage")
    public String myPage(Model model) {
        return "guest/myPage";
    }

    @GetMapping("rentalList")
    public String rentalList(Model model, BookIdList bookidlist) {
        // debug用
        String username = "0";

        model.addAttribute("bookidlist", bookidlist);
        ArrayList<Book> rentBooks = bmsRepository.findAllRentBooks(username);
        ArrayList<Book> candidateRentBooks = bmsRepository.findAllCandidateRentBooks(username);
        model.addAttribute("rentBooks", rentBooks);
        model.addAttribute("candidateRentBooks", candidateRentBooks);
        return "guest/rentalList";
    }

    @PostMapping("return")
    public String returnBook(Model model, BookIdList bookidlist) {
        return "redirect:rentalList";
    }

    @PostMapping("rent")
    public String rent(Model model) {
        return "redirect:rentalList";
    }

    @PostMapping("cancel")
    public String cancel(Model model) {
        return "redirect:rentalList";
    }
}
