package com.springBootSecurity.springBootSecurity.services;

import com.springBootSecurity.springBootSecurity.dto.ProductDto;
import com.springBootSecurity.springBootSecurity.model.AppUser;
import com.springBootSecurity.springBootSecurity.repo.UserRepo;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ProductService {


    @Autowired
   private PasswordEncoder passwordEncoder;
    @Autowired
      public UserRepo userRepo;
    List<ProductDto> productList = null;


    @PostConstruct
    public  void loadProducts(){
        productList= IntStream.rangeClosed(1,20)
                .mapToObj(i->ProductDto.builder()
                        .id(i)
                        .productName(String.valueOf("product " + i))
                        .quantity(new  Random().nextInt(20))
                        .price(new Random().nextInt(500))
                        .build()
                ).collect(Collectors.toList());
    }

    public List<ProductDto> getProducts() {
        return  productList;
    }

    public ProductDto getProductById(int id) {
        return  productList.stream()
                .filter(ProductDto->ProductDto.getId() == id)
                .findAny()
                .orElseThrow(()-> new RuntimeException("Product with id " + id + "not found"));
    }

    public AppUser uploadUser(AppUser appUser) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return  userRepo.save(appUser);
    }
}
