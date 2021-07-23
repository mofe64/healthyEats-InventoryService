package com.semicolon.healthyeatsinventoryservice.data.repository;

import com.semicolon.healthyeatsinventoryservice.data.models.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface IngredientRepository extends MongoRepository<Ingredient, String> {
    Optional<Ingredient> findIngredientByName(String name);

    List<Ingredient> findIngredientByHasRunOut(boolean hasRunOut);
}
