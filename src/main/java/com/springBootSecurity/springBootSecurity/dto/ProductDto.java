package com.springBootSecurity.springBootSecurity.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductDto {

    public  int id;

    public  String productName;
    public  double price;
    public  int quantity;
}
