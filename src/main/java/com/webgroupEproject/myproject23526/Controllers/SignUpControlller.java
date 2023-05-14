package com.webgroupEproject.myproject23526.Controllers;


import com.webgroupEproject.myproject23526.Dto.UserDto;
import com.webgroupEproject.myproject23526.Model.*;
import com.webgroupEproject.myproject23526.Services.ClientService;
import com.webgroupEproject.myproject23526.Services.ProductService;
import com.webgroupEproject.myproject23526.Services.CommentService;


import com.webgroupEproject.myproject23526.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.*;

import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author michael
 */
@Controller
@RequiredArgsConstructor
public class SignUpControlller {

    @Autowired
    private ClientService clientService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private final CustomUserDetailsService customUserDetailsService;

    private final PasswordEncoder passwordEncoder;

    @InitBinder
    public void initbinder(WebDataBinder databinder){
        StringTrimmerEditor stringTimereditor = new StringTrimmerEditor(true);
        databinder.registerCustomEditor(String.class, stringTimereditor);
    }


    @GetMapping("/")
    public String AuthController(Model model){
        model.addAttribute("comments",new Comment());
        System.out.println("here in home");
        return "homepage";
    }


//    @RequestMapping("/login")
//    public String checkloginAsync(Model model){
//
//
//        // Determine the appropriate redirect based on the user's role
//        if (";;"=="ss") {
//            model.addAttribute("client", clientService.getALLClient());
//            model.addAttribute("prods",  productService.getALLProduct());
//            model.addAttribute("comments", commentService.getALLComments());
//            return "dashboard";
//        } else {
//            UserClient user = new UserClient();
//            model.addAttribute("comments", new Comment());
//            model.addAttribute("admin", user.getFullname());
//            return "Homepage";
//        }


    // handler method to handle user registration form submit request
        @PostMapping("/sign")
        public String registration(@Valid @ModelAttribute("signup") UserDto userDto,
                BindingResult result,
                Model model){
            User existingUser = userService.findUserByEmail(userDto.getEmail());

            if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()){
                model.addAttribute("feedback",
                        "Oops we faced an error");
                UserDto user = new UserDto();

                model.addAttribute("signup", user);
                model.addAttribute("logging", new UserDto());
                return "LogingSignUpPage";
            }

            if(result.hasErrors()){

                model.addAttribute("signup", userDto);
                model.addAttribute("logging", new UserDto());
                return "LogingSignUpPage";
            }

            userService.saveUser(userDto);

            model.addAttribute("feedback", "account created!");
            model.addAttribute("logging", new UserDto());
            model.addAttribute("signup", new UserDto());
            return "LogingSignUpPage";

        }

//    @PostMapping("/homepage")
//    public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model) {
//        try {
//            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
//            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
//            authenticationManager.authenticate(token);
//            if (token.isAuthenticated()) {
//                // Check if user has the ROLE_ADMIN role
//                if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
//                    return "redirect:/dashboard";
//                }
//                // Check if user has the ROLE_USER role
//                else if (userDetails.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
//                    model.addAttribute("comments", new Comment());
//                    return "redirect:/homepage";
//                }
//            }
//        } catch (AuthenticationException ex) {
//            model.addAttribute("feedback", "Oops!! we faced a problem!. \ntry again alter");
//            return "LogingSignUpPage";
//        }
//
//        model.addAttribute("comments", new Comment());
//        model.addAttribute("feedback", "Oops!! we faced a problem!. \ntry again alter");
//        return "LogingSignUpPage";
//    }

    @PostMapping(value ="/log")
    public String processLogin(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, HttpServletRequest request, Model model) {
        User user = userService.findUserByEmail(email);
        if(email == null && password == null){
            model.addAttribute("feedback", "please fields are required");
            model.addAttribute("signup", new UserDto());
            model.addAttribute("logging", new UserDto());
            return "LogingSignUpPage";
        }
        else if (user == null) {
            model.addAttribute("feedback", "no such account please register");
            model.addAttribute("signup", new UserDto());
            model.addAttribute("logging", new UserDto());
            return "LogingSignUpPage";
        }

        else if (!passwordEncoder.matches(password, user.getPassword()) && !(user.getEmail().equals(email))) {
            model.addAttribute("feedback", "incorrect user name\nor password");
            model.addAttribute("logging", new UserDto());
            model.addAttribute("signup", new UserDto());
            return "LogingSignUpPage";
        }

        // Authentication succe model.addAttribute("signup", new UserDto());ssful
        session.setAttribute("user", user);
        String requestUrl = request.getRequestURI();

        Set<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
            System.out.println("roles"+ roles +" user email "+ user.getEmail()+" the password "+ user.getPassword());
            System.out.println("role of user "+ roles);
            UserClient userClient = new UserClient();
            //getting the user email
            userClient.setEmail(email);
            //extracting the name from the email
            String[] parts = email.split("@");
           // Extract the name from the first part of the email address
            String name = parts[0];
           // Remove any numbers or special characters from the name
            name = name.replaceAll("[^a-zA-Z\\s]", "");
            //getting it now... i just populate these two field to alley user the task of filling out all the fields
            userClient.setFullname(name.trim());
        if (requestUrl.equals("/log") && roles.contains("ROLE_USER")) {
            model.addAttribute("client", userClient);
            model.addAttribute("Product", new RecServices());
            model.addAttribute("displayclient", "block");
            model.addAttribute("displayProduct", "none");
            model.addAttribute("bkh", "block");
            model.addAttribute("bkd", "none");
            model.addAttribute("comment", "Welcome!!!");
            model.addAttribute("url", "RecordProds");
            return "RecordProduct";

        }else if(requestUrl.equals("/log") && roles.contains("ROLE_ADMIN")){

            model.addAttribute("client", clientService.getALLClient());
            model.addAttribute("prods",  productService.getALLProduct());
            model.addAttribute("comments", commentService.getALLComments());
            model.addAttribute("admin",email.toUpperCase().charAt(0)); // getting the first character of user name to be displayed so you can know you is actually logged in
            model.addAttribute("ncomment", commentService.getNumberofComment());
            return "dashboard";
        }
        System.out.println(requestUrl);
        model.addAttribute("feedback", "Oops!!! we faced a problem");
        model.addAttribute("logging", new UserDto());
        model.addAttribute("signup", new UserDto());
        return "LogingSignUpPage";
    }



        @GetMapping("logout")
        public String logoutuser(Model model){
            UserDto user = new UserDto();
            model.addAttribute("signup", user);
            model.addAttribute("logging", new UserDto());
            return "LogingSignUpPage";
        }
}






