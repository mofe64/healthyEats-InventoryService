package com.semicolon.healthyeatsinventoryservice.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    @Id
    private String id;
    private String name;
    @Field(targetType = FieldType.INT64)
    private Integer quantity;
    private boolean hasRunOut;

    public void incrementQuantity() {
        quantity++;
    }

    public void increaseQuantityBy(int amount) {
        quantity += amount;
    }

    public void decreaseQuantityBy(int amount) {
        quantity -= amount;
    }

    public void decrementQuantity() {
        quantity--;
    }
}
