package com.semicolon.healthyeatsinventoryservice.service;
import com.semicolon.healthyeatsinventoryservice.data.models.Inventory;
import com.semicolon.healthyeatsinventoryservice.exceptions.IngredientException;
import com.semicolon.healthyeatsinventoryservice.exceptions.InventoryException;
import com.semicolon.healthyeatsinventoryservice.service.dtos.IngredientDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {
    Inventory createInventory(String name) throws InventoryException;

    Inventory updateInventory(String id, String name) throws InventoryException;

    List<Inventory> getAllInventories();

    void deleteInventory(String id) throws InventoryException, IngredientException;

    Inventory getInventory(String name) throws InventoryException;

    Inventory getInventoryById(String id) throws InventoryException;

    IngredientDto addIngredientToInventory(String inventoryName, IngredientDto newIngredient) throws InventoryException, IngredientException;

    IngredientDto increaseIngredientQty(String inventoryName, String ingredientName, int qty);

    IngredientDto decreaseIngredientQty(String inventoryName, String ingredientName, int qty);

    void deleteIngredient(String inventoryName, String ingredientName);
}
