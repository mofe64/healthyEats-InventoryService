package com.semicolon.healthyeatsinventoryservice.service.dtos;


import lombok.Data;

@Data
public class IngredientDto {
    private String id;
    private String name;
    private Integer quantity;
    private boolean hasRunOut;
}
