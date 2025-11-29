package com.example.foodknowledgehub.repo;

import com.example.foodknowledgehub.modal.miniral.Micromineral;
import com.example.foodknowledgehub.modal.miniral.MicromineralInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MicromineralInfoRepository extends JpaRepository<MicromineralInfo, Long> {
    Optional<MicromineralInfo> findByMicromineral(Micromineral micromineral);
}
