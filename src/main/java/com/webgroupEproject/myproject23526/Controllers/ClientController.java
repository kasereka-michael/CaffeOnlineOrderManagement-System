package com.webgroupEproject.myproject23526.Controllers;


import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;



import com.webgroupEproject.myproject23526.Model.Comment;
import com.webgroupEproject.myproject23526.Model.RecServices;
import com.webgroupEproject.myproject23526.Model.UserClient;
import com.webgroupEproject.myproject23526.Services.ClientService;
import com.webgroupEproject.myproject23526.Services.ProductService;
import com.webgroupEproject.myproject23526.Services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;


/**
 *
 * @author michael
 */

@Controller
@RequiredArgsConstructor
public class ClientController {

    @Autowired
    private ClientService clientService;
    @Autowired
    private ProductService productService;

    @Autowired
    private CommentService commentService;

    @InitBinder
    public void initbinder(WebDataBinder databinder){
        StringTrimmerEditor stringTimereditor = new StringTrimmerEditor(true);
        databinder.registerCustomEditor(String.class, stringTimereditor);
    }

    @RequestMapping("/ordersubmission")
    public String SaveClientData(@Valid @ModelAttribute("client") UserClient userclient, BindingResult theBindingResult, Model model, @ModelAttribute("message") String message) throws Exception{
        if(theBindingResult.hasErrors()){
            model.addAttribute("client", userclient);
            model.addAttribute("comments",new Comment());
            model.addAttribute("comment", "Oops!!\nWe faced an error");
            model.addAttribute("displayclient", "block");
            model.addAttribute("displayProduct", "none");
            model.addAttribute("url", "RecordProds");
            return "RecordProduct";
        } else{
            userclient.setDate(new Date());
            clientService.saveClientOrder(userclient);
            SendEmail(userclient.getEmail(),userclient.getFullname(),userclient.getAddress());
            model.addAttribute("client", new UserClient()); // Create new instance of UserClient
            model.addAttribute("Product", new RecServices()); // Create new instance of Comment
            model.addAttribute("displayclient", "block");
            model.addAttribute("displayProduct", "none");
            model.addAttribute("url", "RecordProds");
            model.addAttribute("comment", "Thank you!!\nPlease check your Email.");
            return "RecordProduct";
        }
    }

       @RequestMapping("/backhome")
       public String DealWithHome(Model model){
        model.addAttribute("client",new UserClient());
        model.addAttribute("comments",new Comment());
        return "Homepage";
      }

      @RequestMapping("/backdashboard")
      public String gobacktoDashboard(Model model){
        model.addAttribute("client", clientService.getALLClient());
        model.addAttribute("prods",  productService.getALLProduct());
        model.addAttribute("comments", commentService.getALLComments());
        return "dashboard";
     }

    @RequestMapping("/makeorder")
    public String DirectToorder(Model model){
        model.addAttribute("client", new UserClient());
        model.addAttribute("Product", new RecServices());
        model.addAttribute("displayclient", "block");
        model.addAttribute("displayProduct", "none");
        model.addAttribute("bkh", "block");
        model.addAttribute("bkd", "none");
        model.addAttribute("comment", "Welcome!!!");
        model.addAttribute("url", "RecordProds");
        return "RecordProduct";
    }

    @GetMapping("/searching")
    public String searchProducts(Model model, @RequestParam("query") String query) {
        List<RecServices> products = productService.getALLProduct();

        List<RecServices> filteredProducts = products.stream()
                .filter(product -> {
                    for (char c : query.toLowerCase().toCharArray()) {
                        if (product.getProdname().toLowerCase().indexOf(c) < 0) {
                            return false;
                        }
                    }
                    return true;
                })
                .collect(Collectors.toList());

        if(filteredProducts.isEmpty()){
            model.addAttribute("search", "Oops no such product in the house store.");
            return "search-results";
        }

        model.addAttribute("search", "Result");
        model.addAttribute("products", filteredProducts);
System.out.println("the actual size of the list "+ filteredProducts.size());
        return "search-results";
    }



    @RequestMapping("/commentaire")
    public String SaveClientComment(@Valid @ModelAttribute("comments") Comment comments, BindingResult theBindingResult, Model model) throws Exception {
       System.out.println("this is the commentatire"+comments.getComment());
        if(theBindingResult.hasErrors()){
            model.addAttribute("comments", comments);
            model.addAttribute("comment", "Oops!!\nwe faced an error");
            return "Homepage";
        } else{
            comments.setCommentdate(new Date());
            commentService.saveClientcomment(comments);
            SendEmailForComment(comments.getEmail());
            System.out.println("have been saved!!!!!!!!!!!!!1");
            model.addAttribute("client",new UserClient());
            model.addAttribute("comments",new Comment());
            model.addAttribute("comment", "Thank you, We do consider your comment.");
            return "Homepage";
        }
    }





    public void SendEmail(String userEmail, String fn,String ln) throws Exception {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        final String fromEmail = "kaserekamichael23526@gmail.com";
        final String password = "vraazvscxbmzvowu";


        Session session = Session.getDefaultInstance(properties, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(fromEmail,password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
            message.setSubject("Confirmation message");
            String messageBody = "Dear " + fn + " from " + ln + ",\n\nThank you for choosing us. We have received your Order and we will process it and come back to you as soon as possible.\n\nBest regards,\nThe Form Team";
            message.setText(messageBody);
            Transport.send(message);
            System.out.println("Message sent successfully.");
        } catch (MessagingException e) {
            System.out.println("Error sending message: " + e.getMessage());
        }


    }


    public void SendEmailForComment(String userEmail) throws Exception {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        final String fromEmail = "kaserekamichael23526@gmail.com";
        final String password = "vraazvscxbmzvowu";


        Session session = Session.getDefaultInstance(properties, new jakarta.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication(){
                return new PasswordAuthentication(fromEmail,password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
            message.setSubject("Confirmation message");
            String messageBody = "Dear " + userEmail + ",\n\nyou commented on our Website!!(CoffeonlineOrdermanagement system) thank you.\n\nBest regards,\nThe Form Team";
            message.setText(messageBody);
            Transport.send(message);
            System.out.println("Message sent successfully.");
        } catch (MessagingException e) {
            System.out.println("Error sending message: " + e.getMessage());
        }


    }








}
