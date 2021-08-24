package br.com.starter.springwebapp.controllers;

import br.com.starter.springwebapp.entities.User;
import br.com.starter.springwebapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "updateForm";
    }

    @PostMapping("/edit")
    public String update(@Valid User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "updateForm";
        }
        userRepository.save(user);
        return "redirect:/";
    }

    @PostMapping("/create")
    public String save(@Valid User user, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "createForm";
        }
        userRepository.save(user);
        return "redirect:/";
    }

    @GetMapping("/create")
    public String create(User user) {
        return "createForm";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        userRepository.delete(user);
        return "redirect:/";
    }
}
