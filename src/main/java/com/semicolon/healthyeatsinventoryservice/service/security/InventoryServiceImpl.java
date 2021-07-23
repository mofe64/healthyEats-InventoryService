package com.semicolon.healthyeatsinventoryservice.service.security;

import com.semicolon.healthyeatsinventoryservice.data.models.Ingredient;
import com.semicolon.healthyeatsinventoryservice.data.models.Inventory;
import com.semicolon.healthyeatsinventoryservice.data.repository.InventoryRepository;
import com.semicolon.healthyeatsinventoryservice.exceptions.IngredientException;
import com.semicolon.healthyeatsinventoryservice.exceptions.InventoryException;
import com.semicolon.healthyeatsinventoryservice.service.IngredientService;
import com.semicolon.healthyeatsinventoryservice.service.InventoryService;
import com.semicolon.healthyeatsinventoryservice.service.dtos.IngredientDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Inventory createInventory(String name) throws InventoryException {
        if (inventoryExists(name)) {
            throw new InventoryException("Inventory with that name already exists ");
        }
        Inventory inventory = new Inventory();
        inventory.setName(name);
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory updateInventory(String id, String name) throws InventoryException {
        Inventory inventoryToUpdate = getInventoryById(id);
        inventoryToUpdate.setName(name);
        return inventoryRepository.save(inventoryToUpdate);
    }

    @Override
    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    @Override
    public void deleteInventory(String id) throws InventoryException, IngredientException {
        Inventory inventoryToDelete = getInventoryById(id);
        Map<String, Ingredient> ingredientItems = inventoryToDelete.getItems();
        for (String key : ingredientItems.keySet()) {
            Ingredient inventoryIngredient = ingredientItems.get(key);
            ingredientService.deleteIngredient(inventoryIngredient.getId());
        }
        inventoryRepository.delete(inventoryToDelete);
    }

    @Override
    public Inventory getInventory(String name) throws InventoryException {
        return inventoryRepository.findInventoryByName(name)
                .orElseThrow(() -> new InventoryException("No Inventory found with that Id"));
    }

    @Override
    public Inventory getInventoryById(String id) throws InventoryException {
        return inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryException("No Inventory found with that Id"));
    }

    @Override
    public IngredientDto addIngredientToInventory(String inventoryName, IngredientDto newIngredient) throws InventoryException, IngredientException {
        Inventory inventory = getInventory(inventoryName);
        Ingredient ingredient = modelMapper.map(newIngredient, Ingredient.class);
        Map<String, Ingredient> inventoryItems = inventory.getItems();
        String ingredientName = ingredient.getName();
        if (inventoryItems.containsKey(ingredientName)) {
            throw new InventoryException("Ingredient already exists in inventory, consider increasing it's quantity instead");
        }
        Ingredient savedIngredient = ingredientService.addNewIngredient(ingredient);
        inventoryItems.put(savedIngredient.getName(), savedIngredient);
        inventory.setItems(inventoryItems);
        return modelMapper.map(savedIngredient, IngredientDto.class);

    }

    @Override
    public IngredientDto increaseIngredientQty(String inventoryName, String ingredientName, int qty) {
        return null;
    }

    @Override
    public IngredientDto decreaseIngredientQty(String inventoryName, String ingredientName, int qty) {
        return null;
    }

    @Override
    public void deleteIngredient(String inventoryName, String ingredientName) {

    }

    private boolean inventoryExists(String name) {
        return inventoryRepository.findInventoryByName(name).isPresent();
    }
}
