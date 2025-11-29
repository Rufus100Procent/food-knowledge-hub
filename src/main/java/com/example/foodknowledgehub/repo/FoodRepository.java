package com.example.foodknowledgehub.repo;

import com.example.foodknowledgehub.modal.Food;
import com.example.foodknowledgehub.modal.miniral.Macromineral;
import com.example.foodknowledgehub.modal.miniral.Micromineral;
import com.example.foodknowledgehub.modal.vitamin.Vitamin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Long> {

    List<Food> findDistinctByMacrominerals_Macromineral(Macromineral macromineral);

    List<Food> findDistinctByMicrominerals_Micromineral(Micromineral micromineral);

    List<Food> findDistinctByVitamins_Vitamin(Vitamin vitamin);
}
