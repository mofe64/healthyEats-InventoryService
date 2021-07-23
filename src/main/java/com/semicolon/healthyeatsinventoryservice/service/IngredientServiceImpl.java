package com.semicolon.healthyeatsinventoryservice.service;

import com.semicolon.healthyeatsinventoryservice.data.models.Ingredient;
import com.semicolon.healthyeatsinventoryservice.data.repository.IngredientRepository;
import com.semicolon.healthyeatsinventoryservice.exceptions.IngredientException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Ingredient addNewIngredient(Ingredient ingredient) throws IngredientException {
        if (ingredientExists(ingredient.getName())) {
            throw new IngredientException("Ingredient with this name already exists, consider incrementing it's quantity instead");
        }
        return ingredientRepository.save(ingredient);
    }

    @Override
    public Ingredient updateIngredient(String ingredientId, Ingredient newDetails) throws IngredientException {
        Ingredient ingredientToUpdate = getIngredientById(ingredientId);
        modelMapper.map(newDetails, ingredientToUpdate);
        return ingredientRepository.save(ingredientToUpdate);
    }

    @Override
    public void deleteIngredient(String ingredientId) throws IngredientException {
        Ingredient ingredientToDelete = getIngredientById(ingredientId);
        ingredientRepository.delete(ingredientToDelete);
    }

    @Override
    public void incrementIngredientQuantity(String ingredientId, int amount) throws IngredientException {
        if (amount < 1) {
            throw new IngredientException("Ingredient amount cannot be less than 1");
        }
        Ingredient ingredientToUpdate = getIngredientById(ingredientId);
        ingredientToUpdate.increaseQuantityBy(amount);
        ingredientRepository.save(ingredientToUpdate);
    }

    @Override
    public void decrementIngredientQuantity(String ingredientId, int amount) throws IngredientException {
        if (amount < 1) {
            throw new IngredientException("Ingredient amount cannot be less than 1");
        }
        Ingredient ingredientToUpdate = getIngredientById(ingredientId);
        int currentIngredientAmount = ingredientToUpdate.getQuantity();
        if (amount > currentIngredientAmount) {
            throw new IngredientException(String.format("Ingredient's current amount is %d and cannot be decremented by %d", currentIngredientAmount, amount));
        }
        ingredientToUpdate.decreaseQuantityBy(amount);
        ingredientRepository.save(ingredientToUpdate);
    }

    @Override
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    @Override
    public Ingredient getIngredientById(String Id) throws IngredientException {
        return ingredientRepository.findById(Id)
                .orElseThrow(() -> new IngredientException(String.format("No Ingredient found with id %S", Id)));
    }

    @Override
    public Ingredient getIngredientByName(String name) throws IngredientException {
        return ingredientRepository.findIngredientByName(name)
                .orElseThrow(() -> new IngredientException(String.format("No Ingredient found with name %S", name)));
    }

    private boolean ingredientExists(String name) {
        return ingredientRepository.findIngredientByName(name).isPresent();
    }
}
