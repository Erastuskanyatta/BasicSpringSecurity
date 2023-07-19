package com.springBootSecurity.springBootSecurity.controller;


import com.springBootSecurity.springBootSecurity.dto.ProductDto;
import com.springBootSecurity.springBootSecurity.model.AppUser;
import com.springBootSecurity.springBootSecurity.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/greetings")
    public  String Greeting(){
        return "Greetings at User!";
    }

    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("/products/all")
    public List<ProductDto> allProducts(){
        return  productService.getProducts();
    }
    @PreAuthorize("hasAuthority('User')")
    @GetMapping("/products/{id}")
    public  ProductDto getProductById(@PathVariable int id){
        return  productService.getProductById(id);
    }

    //save User
    @PostMapping("/uploadUser")
    public AppUser appUser(@RequestBody AppUser appUser){
        return  productService.uploadUser(appUser);

    }
}
