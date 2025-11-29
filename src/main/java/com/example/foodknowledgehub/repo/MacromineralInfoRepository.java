package com.example.foodknowledgehub.repo;

import com.example.foodknowledgehub.modal.miniral.Macromineral;
import com.example.foodknowledgehub.modal.miniral.MacromineralInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MacromineralInfoRepository extends JpaRepository<MacromineralInfo, Long> {
    Optional<MacromineralInfo> findByMacromineral(Macromineral macromineral);
}

