package com.challenge.todo.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.challenge.todo.model.User;
import com.challenge.todo.service.UserService;

/**
 * Controller utilizado para gerenciar o usuário	
 *
 * @author Arthur Cruz	
 * @version	1.0
*/ 
@Controller
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping(value="/registration")
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @PostMapping(value = "/registration")
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            modelAndView.addObject("errorMessage", "Já existe um usuário com o email selecionado");
            modelAndView.setViewName("login");

            return modelAndView;
        }
        
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("errorMessage", "Erro ao criar usuário");
            modelAndView.setViewName("login");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "Usuário cadastrado com sucesso");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("login");

        }
        return modelAndView;
    }

}
