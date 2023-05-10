package com.webgroupEproject.myproject23526.Controllers;


import com.webgroupEproject.myproject23526.Model.Document;
import com.webgroupEproject.myproject23526.Model.RecServices;
import com.webgroupEproject.myproject23526.Model.UserClient;
import com.webgroupEproject.myproject23526.Repository.DocumentRepository;
import com.webgroupEproject.myproject23526.Services.*;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author michael
 */
@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ClientService clientService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @Autowired
    private DocumentRepository documentRepository;

    @RequestMapping("/addprod")
    public String DirectToorder(Model model){
        model.addAttribute("client", new UserClient());
        model.addAttribute("Product", new RecServices());
        model.addAttribute("displayclient", "none");
        model.addAttribute("displayProduct", "block");
        model.addAttribute("bkh", "none");
        model.addAttribute("bkd", "block");
        model.addAttribute("url", "RecordProds");
        model.addAttribute("comment", "Record new Product✨✨");
        return "RecordProduct";
    }


    @RequestMapping("/RecordProds")
    public String SaveProduct(@ModelAttribute("Product") RecServices produc, BindingResult theBindingResult, Model model) throws IOException {


        if(theBindingResult.hasErrors()){
            model.addAttribute("Product",  produc);
            model.addAttribute("client",new UserClient());
            model.addAttribute("displayclient", "none");
            model.addAttribute("displayProduct", "block");
            model.addAttribute("url", "RecordProds");
            model.addAttribute("comment", "Oops!!\nwe faced an error");
            model.addAttribute("head", "Record Products and their details");
//
            return "RecordProduct";
        }else{

            productService.saveProduct(produc);
            model.addAttribute("Product", new RecServices());
            model.addAttribute("client", new UserClient());
            model.addAttribute("displayclient", "none");
            model.addAttribute("displayProduct", "block");
            model.addAttribute("url", "RecordProds");
            model.addAttribute("comment", "Thank you, Product Recorded");
            model.addAttribute("head", "Record Products and their details");
            return "RecordProduct";

        }
    }
    @RequestMapping("/EditProduct")
    public String EditOrder(@RequestParam("id") Long id, Model model){
        System.out.print("the id:::::::::::"+ id);
        RecServices recServices = productService.getProductById(id);

        model.addAttribute("Product", recServices);
        model.addAttribute("action", "Edit");
        model.addAttribute("client", new UserClient());
        model.addAttribute("displayclient", "none");
        model.addAttribute("displayProduct", "block");
        model.addAttribute("bkh", "none");
        model.addAttribute("bkd", "block");
        model.addAttribute("url", "Editp");
        model.addAttribute("head", "Update the Product details");

        return "RecordProduct";
    }



    @RequestMapping("/DeleteProduct")
    public String DeleteOrder(@RequestParam("id") Long id, Model model){
        System.out.print("the id:::::::::::"+ id);
        RecServices recServices = productService.getProductById(id);

        model.addAttribute("Product", recServices);
        model.addAttribute("action", "Delete");
        model.addAttribute("client", new UserClient());
        model.addAttribute("displayclient", "none");
        model.addAttribute("displayProduct", "block");
        model.addAttribute("bkh", "none");
        model.addAttribute("bkd", "block");
        model.addAttribute("url", "DeleteProd");
        model.addAttribute("head", "Delete Product");

        return "RecordProduct";
    }





    @RequestMapping("/Editp")
    public String UpdateProduct(@ModelAttribute("Product") RecServices produc, BindingResult theBindingResult, Model model){


        if(theBindingResult.hasErrors()){
            model.addAttribute("Product", produc);
            model.addAttribute("action", "Edit");
            model.addAttribute("client",new UserClient());
            model.addAttribute("displayclient", "none");
            model.addAttribute("displayProduct", "block");
            model.addAttribute("url", "Editp");
            model.addAttribute("head", "Edit Product");
            model.addAttribute("comment", "Oops!! we have not been able\nto apply the update ");

            return "RecordProduct";
        }else{
            productService.saveProduct(produc);
            model.addAttribute("Product",new RecServices());
            model.addAttribute("action", "Submit");
            model.addAttribute("client",new UserClient());
            model.addAttribute("displayclient", "none");
            model.addAttribute("displayProduct", "block");
            model.addAttribute("url", "RecordProds");
            model.addAttribute("head", "Record Products and their details");
            model.addAttribute("comment", "Thank you, Product Updated Successfully");

            return "RecordProduct";
        }
    }


    @RequestMapping("/DeleteProd")
    public String DeleteProducts(@ModelAttribute("Product") RecServices produc ,@RequestParam("id") long id, BindingResult theBindingResult, Model model){


        if(theBindingResult.hasErrors()){
            model.addAttribute("Product", produc);
            model.addAttribute("action", "Delete");
            model.addAttribute("client",new UserClient());
            model.addAttribute("displayclient", "none");
            model.addAttribute("displayProduct", "block");
            model.addAttribute("url", "DeleteProd");
            model.addAttribute("head", "Delete Product");
            model.addAttribute("comment", "Oops!! we have not been able\nto apply the update ");

            return "RecordProduct";
        }else{
            productService.deleteProductByid(id);
            model.addAttribute("Product", new RecServices());
            model.addAttribute("action", "Submit");
            model.addAttribute("client",new UserClient());
            model.addAttribute("displayclient", "none");
            model.addAttribute("displayProduct", "block");
            model.addAttribute("url", "RecordProds");
            model.addAttribute("head", "Record Products and their details");
            model.addAttribute("comment", "O, Product deleted Successfully");

            return "RecordProduct";
        }
    }
     @PostMapping("/upload")
    public String UploadFile(@RequestParam("document")MultipartFile multipartFile,Model model) throws IOException {


        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Document document = new Document();
        document.setName(filename);
        document.setContent(multipartFile.getBytes());
        document.setSize(multipartFile.getSize());
        document.setUplaodtime(new Date());

        documentRepository.save(document);

        model.addAttribute("client", clientService.getALLClient());
        model.addAttribute("prods",  productService.getALLProduct());
        model.addAttribute("comments", commentService.getALLComments());
        return "dashboard";

    }

    @GetMapping("/viewfile")
    public String ShowFileforuploadanddownload(Model model){
      List<Document> listofdoc  = documentRepository.findAll();
        model.addAttribute("listoffile", listofdoc);
        return "view-and-download-file";
    }

    @GetMapping("/download")
    public void download(@RequestParam("id")Long id, HttpServletResponse response) throws Exception {
        Optional<Document> result = documentRepository.findById(id);
        if(!result.isPresent()){
            throw new Exception("could not find the given document "+ id);
        }
        Document document = result.get();
        response.setContentType("application/octet-stream");
        String headerkey = "Content-Disposition";
        String headerValue = "attachement; filename=" + document.getName();

        response.setHeader(headerkey, headerValue);
        response.getOutputStream();

        ServletOutputStream outputStream = response.getOutputStream();

        outputStream.write(document.getContent());
        outputStream.close();
    }


}
