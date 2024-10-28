package com.travelport.projectone.controller;

import com.travelport.projectone.model.LoginModel;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Stream;

import java.util.List;

@RestController
public class LoginController {

    private static final String LOGIN_VIEW_NAME = "login";

    @GetMapping("/login")
    public String login() {
        return LOGIN_VIEW_NAME;
    }

    @PostMapping(value = "/login")
    public String doLogin(
            @Valid @ModelAttribute LoginModel loginModel, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", mapErrors(bindingResult));
            return LOGIN_VIEW_NAME;
        }
        if (areValidCredentials(loginModel)) {
            return "redirect:/" + loginModel.getName();
        }
        model.addAttribute("errors", List.of("Wrong credentials."));
        return LOGIN_VIEW_NAME;
    }

    private boolean areValidCredentials(LoginModel loginModel) {
        return true;//(loginModel.getName().equals("123") && loginModel.getNif().equals("123"));
    }

    private List<String> mapErrors(BindingResult bindingResult) {
        var fieldErrors =
                bindingResult.getFieldErrors().stream()
                        .map(
                                x ->
                                        x.getField()
                                                + " "
                                                + x.getDefaultMessage()
                                                + ". Received: "
                                                + x.getRejectedValue());
        var otherErrors =
                bindingResult.getGlobalErrors().stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage);
        return Stream.concat(fieldErrors, otherErrors).toList();
    }
}
