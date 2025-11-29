package com.example.foodknowledgehub.repo;

import com.example.foodknowledgehub.modal.vitamin.Vitamin;
import com.example.foodknowledgehub.modal.vitamin.VitaminInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VitaminInfoRepository extends JpaRepository<VitaminInfo, Long> {
    Optional<VitaminInfo> findByVitamin(Vitamin vitamin);
}
