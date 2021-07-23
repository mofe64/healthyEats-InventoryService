package com.semicolon.healthyeatsinventoryservice.data.repository;

import com.semicolon.healthyeatsinventoryservice.data.models.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface InventoryRepository extends MongoRepository<Inventory, String> {
    Optional<Inventory> findInventoryByName(String name);
}
