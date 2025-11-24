package com.example.foodknowledgehub.repo;

import com.example.foodknowledgehub.modal.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {
}
