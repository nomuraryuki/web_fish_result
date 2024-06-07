package com.example.springfishproject.controller;

import com.example.springfishproject.Exception.ProductNotFoundException;
import com.example.springfishproject.form.AddResultFishingForm;
import com.example.springfishproject.form.AddUserForm;
import com.example.springfishproject.form.LoginUserForm;
import com.example.springfishproject.form.SearchFishNameFrom;
import com.example.springfishproject.record.*;
import com.example.springfishproject.service.PgFishingResultsService;
import com.example.springfishproject.service.PgPlaceService;
import com.example.springfishproject.service.PgTideService;
import com.example.springfishproject.service.PgUserService;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException;
@Controller
public class FishResultsController {

    @Autowired
    PgUserService pgUserService;

    @Autowired
    PgFishingResultsService pgFishingResultsService;

    @Autowired
    PgPlaceService pgPlaceService;

    @Autowired
    PgTideService pgTideService;

    @Autowired
    private HttpSession session;

    @Autowired
    private MessageSource messageSource;

    private  String successMessage;
    int i=0;

    @GetMapping("/login")
    public String login(@ModelAttribute("loginUserForm") LoginUserForm loginUserForm) throws ParseException {
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
        if(session.getAttribute("user")==null) return "redirect:/login";
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
    public  String infoList(@ModelAttribute("SearchFishNameFrom") SearchFishNameFrom searchFishNameFrom, @ModelAttribute("loginUserForm") LoginUserForm loginUserForm, Model model){
        if(session.getAttribute("user")==null) return "redirect:/login";
        model.addAttribute("MenuMessage", successMessage);
        model.addAttribute("SearchFishNameFrom",searchFishNameFrom);
        model.addAttribute("resultList", pgFishingResultsService.findAll());
        model.addAttribute("countDB", pgFishingResultsService.findAll().size());
        return "infolist";
    }

    @GetMapping("/place/{locate}")
    public  String place(@PathVariable("locate") String locate,@ModelAttribute("SearchFishNameFrom") SearchFishNameFrom searchFishNameFrom, @ModelAttribute("loginUserForm") LoginUserForm loginUserForm, Model model){
        if(session.getAttribute("user")==null) return "redirect:/login";
        var resultByPlacelist =pgFishingResultsService.findByPlace(locate);

        model.addAttribute("MenuMessage", successMessage);
        model.addAttribute("SearchFishNameFrom",searchFishNameFrom);
        model.addAttribute("resultList", resultByPlacelist);
        model.addAttribute("countDB", resultByPlacelist.size());
        return "place";
    }

    @GetMapping("/detail/{id}")
    public  String detail(@PathVariable("id") int id, Model model ){
        successMessage="";
        if(session.getAttribute("user")==null) return "redirect:/login";
        model.addAttribute("fishData", pgFishingResultsService.findById(id));
        return "detail";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, Model model ){
        try {
            model.addAttribute("fishData", pgFishingResultsService.findById(id));
            pgFishingResultsService.delete(id);
            successMessage = "削除に成功しました";
            return "redirect:/success";
        }catch (Exception e){
            successMessage = "削除に失敗しました";
//            return "redirect:/detail/{pId}";
            return "redirect:/detail{id}";
        }
    }

    @GetMapping("success")
    public String success(){
        return "redirect:/infolist";
    }

    @GetMapping("/insert")
    public String insert(@ModelAttribute("addResultFishingForm") AddResultFishingForm addResultFishingForm, Model model){
        successMessage="";
        if(session.getAttribute("user")==null) return "redirect:/login";
        model.addAttribute("tideList", pgTideService.findAll());
        model.addAttribute("placeList", pgPlaceService.findAll());
        return "insert";
    }

    @PostMapping("/insert")
    public String addProduct(@Validated @ModelAttribute("addResultFishingForm") AddResultFishingForm addResultFishingForm, BindingResult bindingResult, @RequestParam("file") MultipartFile file, Model model) {
        if(session.getAttribute("user")==null) return "redirect:/login";
        String fileName = file.getOriginalFilename();

        model.addAttribute("tideList", pgTideService.findAll());
        model.addAttribute("placeList", pgPlaceService.findAll());

        if(bindingResult.hasErrors()) {
            return "insert";
        }
        else{

            var fish_name = addResultFishingForm.getAddFishName();
            var fish_size = Double.parseDouble(addResultFishingForm.getAddFishSize());
            var date = addResultFishingForm.getDate();
            var weather = addResultFishingForm.getWeather();
            var description = addResultFishingForm.getAddFishDescription();
            var tideId = Integer.parseInt(addResultFishingForm.getTideId());
            var placeId = Integer.parseInt(addResultFishingForm.getPlaceId());
            var year = addResultFishingForm.getDateYear();
            var month = addResultFishingForm.getDateMonth();
            var day = addResultFishingForm.getDateDay();
            var img_path = fileName;
//            var date = year+"-"+month+"-"+day;
//            if(date.equals("--"))date=null;

            try {
                var insertresult = new InsertFishingResultRecord(-1,fish_name,fish_size,date,weather,tideId,placeId,description,img_path);
                pgFishingResultsService.insert(insertresult);
                successMessage = "登録が完了しました";
                return "redirect:/infolist";
            }catch (ProductNotFoundException e){
                String errorMessage = messageSource.getMessage("productid.error.message",null, Locale.JAPAN);
                model.addAttribute("errorMessage", errorMessage);
                return "insert";
            }
        }
    }

    @GetMapping("/updateInput/{id}")
    public String updateInput(@PathVariable("id") int id, @ModelAttribute("addResultFishingForm") AddResultFishingForm addResultFishingForm, Model model){
        if (session.getAttribute("user") == null) return "redirect:/login";

        model.addAttribute("fishData", pgFishingResultsService.findById(id));
        model.addAttribute("tideList", pgTideService.findAll());
        model.addAttribute("placeList", pgPlaceService.findAll());

        FishingResultsRecord FresultRecord = pgFishingResultsService.findById(id);
        String tide_id = String.valueOf(pgTideService.findIdByName(FresultRecord.tide_kinds()).id());
        String place_id = String.valueOf(pgPlaceService.findIdByName(FresultRecord.place_name()).id());

        addResultFishingForm.setAddFishName(FresultRecord.fish_name());
        addResultFishingForm.setAddFishSize(String.valueOf(FresultRecord.fish_size()));
        addResultFishingForm.setDate(FresultRecord.dating());
        addResultFishingForm.setTideId(tide_id);
        addResultFishingForm.setPlaceId(place_id);
        addResultFishingForm.setAddFishDescription(FresultRecord.description());
//        addResultFishingForm.setImgPath(FresultRecord.img_path());
        System.out.println(addResultFishingForm.getImgPath());
        return "updateInput";
    }

    @PostMapping("/updateInput/{id}")
    public String updatePost(@Validated @ModelAttribute("addResultFishingForm") AddResultFishingForm addResultFishingForm,BindingResult bindingResult,@PathVariable("id") int id,@RequestParam("file") MultipartFile file, Model model){
        if(bindingResult.hasErrors()) {
            model.addAttribute("fishData", pgFishingResultsService.findById(id));
            model.addAttribute("tideList", pgTideService.findAll());
            model.addAttribute("placeList", pgPlaceService.findAll());

//            String errorUpdateMessage = messageSource.getMessage("update.product.error.message",null, Locale.JAPAN);
//            model.addAttribute("errorUpdateMessage", errorUpdateMessage);
            return "updateInput";
        }

        else if(session.getAttribute("user")==null) return "redirect:/index";

        else{
            model.addAttribute("tideList", pgTideService.findAll());
            model.addAttribute("placeList", pgPlaceService.findAll());

            String fileName = file.getOriginalFilename();

            var fish_name = addResultFishingForm.getAddFishName();
            var fish_size = Double.parseDouble(addResultFishingForm.getAddFishSize());
            var dating = addResultFishingForm.getDate();
            var weather = addResultFishingForm.getWeather();
            var tideId = Integer.parseInt(addResultFishingForm.getTideId());
            var placeId = Integer.parseInt(addResultFishingForm.getPlaceId());
            var description = addResultFishingForm.getAddFishDescription();
            var img_path = fileName;
            var updateResult = new InsertFishingResultRecord(id,fish_name,fish_size,dating,weather,tideId,placeId,description,img_path);
            System.out.println(updateResult);
            try {
                pgFishingResultsService.update(updateResult);
//                pgFishingResultsService.update()
                successMessage = "更新が完了しました";
                return "redirect:/infolist";
            }catch (RuntimeException e){
                return "updateInput";
            }
        }
    }

    @GetMapping("/secret")
    public String secret(){
        return "secret";
    }

}
