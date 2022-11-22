package org.example.springMVC.controllers;

import org.example.springMVC.dao.DAO;
import org.example.springMVC.util.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.http.HttpServletRequest;

@Controller
public class FirstController {

    private DAO dao;

    public FirstController(DAO dao) {
        this.dao = dao;
    }

    @GetMapping("/")
    public String mainPage() {
        return "first/index";
    }
    @GetMapping("/login")
    public String loginPage( Model model) {
        model.addAttribute("user",new User());
        return "first/login";
    }
    @PostMapping("/me")
    public String me(@ModelAttribute User user,Model model) {
        System.out.println(user.getLogin());
        user = dao.getUserObject(user.getLogin(),user.getPassword());
        model.addAttribute("user",user);
        return "first/profile";
    }
    @GetMapping("/users/{id}")
    public String profile(@PathVariable("id") int id,Model model) {
        model.addAttribute("user",dao.getUserObject(id));
        return "first/profile";
    }
    @PatchMapping("/users/{id}")
    public String update(@ModelAttribute User user, Model model,@PathVariable("id") int id) {
        dao.updateUser(user);
        System.out.println("patch");
        return "redirect:/users/"+id;
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        dao.deleteUser(id);
        return "redirect:/users";
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("usersList",dao.getAllUsers());
        return "first/users";
    }
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("dao",dao);
        model.addAttribute("user",new User());
        return "first/register";
    }
    @PostMapping("/register")
    public String registerPost(@ModelAttribute User user,Model model) {
        System.out.println(user.getName()+" "+user.getLogin());
        dao.createUser(user);
        model.addAttribute("user",user);
        return "redirect:users";
    }

    @GetMapping("/users/{id}/edit")
    public String editForm(@PathVariable("id") int id,Model model) {
        model.addAttribute("user",dao.getUserObject(id));
        System.out.println(dao.getUserObject(id).getName());
        return "first/edit";
    }
}
