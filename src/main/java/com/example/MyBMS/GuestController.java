package com.example.MyBMS;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String rentalList(Model model, BookIdList rendingBookIDList, BookIdList candidateBookIDList, @AuthenticationPrincipal UserDetails user, @ModelAttribute("rentFaied") String rentFaied, @ModelAttribute("emptyCB") String emptyCB) {
        String username = user.getUsername();

        // model.addAttribute("bookidlist", bookidlist);
        model.addAttribute("rendingBookIDList", rendingBookIDList);
        ArrayList<Book> rentBooks = bmsRepository.findAllRentBooks(username);
        model.addAttribute("candidateBookIDList", candidateBookIDList);
        ArrayList<Book> candidateRentBooks = bmsRepository.findAllCandidateRentBooks(username);
        model.addAttribute("rentBooks", rentBooks);
        model.addAttribute("candidateRentBooks", candidateRentBooks);

        ArrayList<RentalInfomation> infos = bmsRepository.getRendingInfo(username);
        model.addAttribute("infos", infos);
        return "guest/rentalList";
    }

    @PostMapping("return")
    public String returnBook(RedirectAttributes redirectAttributes, @Validated BookIdList bookidlist, BindingResult result, @AuthenticationPrincipal UserDetails user) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("emptyCB", "図書が選択されていません");
            return "redirect:rentalList";
        }

        String username = user.getUsername();

        bmsRepository.returnBooks(username, bookidlist.getSelectedBooks());

        return "redirect:rentalList";
    }

    @PostMapping("rent")
    public String rent(RedirectAttributes redirectAttributes, @Validated BookIdList candidateBookIDList, BindingResult result, @AuthenticationPrincipal UserDetails user) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("emptyCB", "図書が選択されていません");
            return "redirect:rentalList";
        }

        String username = user.getUsername();
        int maxRentBookNumber = 5;

        int rendingBookNumber = bmsRepository.checkRentBookNumber(username);
        if (rendingBookNumber >= maxRentBookNumber){
            redirectAttributes.addFlashAttribute("rentFailed", String.format("貸出は最大%d冊までです", maxRentBookNumber));
            return "redirect:rentalList";
        }

        boolean isRented = bmsRepository.rentBooks(username, candidateBookIDList.getSelectedBooks());

        if (!isRented){
            redirectAttributes.addFlashAttribute("rentFailed", "貸出に失敗しました");
        }
        return "redirect:rentalList";
    }

    @PostMapping("cancel")
    public String cancel(RedirectAttributes redirectAttributes, @Validated BookIdList candidateBookIDList, BindingResult result, @AuthenticationPrincipal UserDetails user) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("emptyCB", "図書が選択されていません");
            return "redirect:rentalList";
        }

        String username = user.getUsername();

        bmsRepository.cancelCandidateItem(username, candidateBookIDList.getSelectedBooks());
        return "redirect:rentalList";
    }

    @GetMapping("view")
    public String view(Model model, @AuthenticationPrincipal UserDetails user) {
        model.addAttribute("user", user);
        return "guest/view";
    }
}
