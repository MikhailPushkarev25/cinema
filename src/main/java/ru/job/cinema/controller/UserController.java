package ru.job.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job.cinema.model.User;
import ru.job.cinema.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage(Model model, @RequestParam(value = "fail", required = false) Boolean fail) {
       model.addAttribute("fail", fail != null);
       return "login";
   }

   @GetMapping("/logout")
   public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/index";
   }

   @GetMapping("/fail")
   public String fail(Model model) {
        return "fail";
   }

   @GetMapping("/success")
   public String success(Model model) {
        return "success";
   }

   @GetMapping("registration")
   public String linkReg() {
        return "registration";
   }

   @PostMapping("/registration")
   public String registration(Model model, @ModelAttribute User user) {
        Optional<User> userDb = userService.add(user);
        if (userDb.isEmpty()) {
            model.addAttribute("message", "Пользователь с такой почтой уже существует!");
            return "redirect:/fail";
        }
        return "redirect:/success";
   }

   @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = userService.findByEmailAndPhone(
                user.getEmail(), user.getPhone()
        );
        if (userDb.isEmpty()) {
            return "redirect:/login?file=true";
        }
       HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
   }
}
