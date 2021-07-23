package com.semicolon.healthyeatsinventoryservice.service;

import com.semicolon.healthyeatsinventoryservice.data.models.Ingredient;
import com.semicolon.healthyeatsinventoryservice.exceptions.IngredientException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IngredientService {
    Ingredient addNewIngredient(Ingredient ingredient) throws IngredientException;

    Ingredient updateIngredient(String ingredientId, Ingredient ingredient) throws IngredientException;

    void deleteIngredient(String ingredientId) throws IngredientException;

    void incrementIngredientQuantity(String ingredientId,int amount) throws IngredientException;

    void decrementIngredientQuantity(String ingredientId,int amount) throws IngredientException;

    List<Ingredient> getAllIngredients();

    Ingredient getIngredientById(String Id) throws IngredientException;

    Ingredient getIngredientByName(String name) throws IngredientException;
}
