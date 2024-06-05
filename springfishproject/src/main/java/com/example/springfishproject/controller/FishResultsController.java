package com.example.springfishproject.controller;

import com.example.springfishproject.Exception.ProductNotFoundException;
import com.example.springfishproject.form.AddUserForm;
import com.example.springfishproject.form.LoginUserForm;
import com.example.springfishproject.record.SessionUserRecord;
import com.example.springfishproject.record.UserRecord;
import com.example.springfishproject.service.PgFishingResultsService;
import com.example.springfishproject.service.PgUserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Locale;

@Controller
public class FishResultsController {

    @Autowired
    PgUserService pgUserService;

    @Autowired
    PgFishingResultsService pgFishingResultsService;

    @Autowired
    private HttpSession session;

    @Autowired
    private MessageSource messageSource;

    private  String successMessage;

    @GetMapping("/login")
    public String login(@ModelAttribute("loginUserForm") LoginUserForm loginUserForm){
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@Validated @ModelAttribute("loginUserForm") LoginUserForm loginUserForm, BindingResult bindingResult, Model model){
        try {
            var userList = pgUserService.findUser(loginUserForm.getLoginUserId(), loginUserForm.getLoginUserPassword());

            if (bindingResult.hasErrors()) {
                System.out.println(-1);
                return "login";

            } else if (loginUserForm.getLoginUserId().equals(userList.login_id()) && loginUserForm.getLoginUserPassword().equals(userList.password())) {
                var user = new SessionUserRecord(userList.id(), userList.login_id(), userList.name(),userList.role());
                session.setAttribute("user", user);
                return "redirect:/top";
            }

        }catch (ProductNotFoundException e){
            System.out.println(0);
            String errorLogin = messageSource.getMessage("login.error.message",null, Locale.JAPAN);
            model.addAttribute("errorLogin", errorLogin);
            return "login";
        }
        System.out.println(1);
        return "login";
    }

    @GetMapping("/logout")
    public String logoutget(@ModelAttribute("loginForm") LoginUserForm loginUserForm) {
        session.invalidate();
        return "logout";
    }

    @GetMapping("/top")
    public String top(){
        return "top";
    }

    @GetMapping("/adduser")
    public String adduser(@ModelAttribute("addUserForm") AddUserForm addUserForm, Model model){
        return  "adduser";
    }

    @PostMapping("/adduser")
    public  String adduserPost(@Validated @ModelAttribute("addUserForm") AddUserForm addUserForm, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) {
            return "adduser";
        }
        try {
            var login_id = addUserForm.getAddUserLoginId();
            var password = addUserForm.getAddUserPassword();
            var name = addUserForm.getAddUserName();
            var insertUser = new UserRecord(-1,login_id,password,name,-1);
            pgUserService.insertUser(insertUser);
            return "redirect:/login";

        }catch (ProductNotFoundException e){
            return "adduser";
        }

    }

    @GetMapping("/infolist")
    public  String infoList(@ModelAttribute("loginUserForm") LoginUserForm loginUserForm,Model model){

        model.addAttribute("MenuMessage", successMessage);
        model.addAttribute("resultList", pgFishingResultsService.findAll());
        model.addAttribute("countDB", pgFishingResultsService.findAll().size());
        return "infolist";
    }

}
