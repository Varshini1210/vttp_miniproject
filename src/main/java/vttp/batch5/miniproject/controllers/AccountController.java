package vttp.batch5.miniproject.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import vttp.batch5.miniproject.models.NewPerson;
import vttp.batch5.miniproject.models.User;
import vttp.batch5.miniproject.services.BookService;
import vttp.batch5.miniproject.services.PersonService;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    PersonService personService;

    @Autowired 
    BookService bookService;

    @GetMapping("/create")
    public String getCreateForm(Model model){
        List<String> listNames = bookService.getListNames();
        model.addAttribute("listNames",listNames);
        NewPerson np = new NewPerson();
        model.addAttribute("newPerson",np);
        return "newacc";
    }

    @PostMapping("/create")
    public String postCreate(@Valid @ModelAttribute("newPerson") NewPerson np, BindingResult result, Model model){
        
        if(result.hasErrors()){
            return "newacc";
        }
        String username = np.getUsername();
        // check if username exists in db
        Boolean userExists = personService.userExists(username);
        //return error message if it exists
        if (userExists == true){
            ObjectError err = new ObjectError("globalError", "Username already exists. Please enter a different username");
            result.addError(err);
            return "newacc";
        }
        //check if passwords match
        Boolean passwordMatch = personService.passwordMatch(np);
        //return error message if no match
        if(passwordMatch == false){
            ObjectError err = new ObjectError("globalError","Your passwords do not match");
            result.addError(err);
            return "newacc";
        }
        //create new user and redirect to login
        personService.createNewAcc(np);
        return "redirect:/account/login";
    }

    @GetMapping("/login")
    public String getLoginForm(Model model){
        
        List<String> listNames = bookService.getListNames();
        model.addAttribute("listNames",listNames);
        User user = new User();
        model.addAttribute("user",user);
        return "login";
    }

    @PostMapping("/login")
    public String postLoginForm(@Valid @ModelAttribute("user") User user, BindingResult result, Model model, HttpSession session){
        if(result.hasErrors()){
            return "login";
        }
        
        // check if username exists. 
        Boolean userExists = personService.userExists(user.getUsername());
        // if it does not, return error message
        if (userExists == false){
            ObjectError err = new ObjectError("globalError", "Incorrect username or password. Please try again");
            result.addError(err);
            return "login";
        }
        // check if password matches username
        Boolean passwordCheck = personService.verifyPassword(user);
        // return error message if no match
        if (passwordCheck == false){
            ObjectError err = new ObjectError("globalError", "Incorrect username or password. Please try again");
            result.addError(err);
            return "login";
        }
        // login if match and create session variable
        session.setAttribute("username", user.getUsername());
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logoutUser (HttpSession session){
        String username = (String)session.getAttribute("username");
        session.invalidate();
        return "redirect:/home";

    }
    
    

}
