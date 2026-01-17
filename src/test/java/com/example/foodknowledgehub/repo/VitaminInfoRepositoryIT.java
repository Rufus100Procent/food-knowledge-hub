package com.example.foodknowledgehub.repo;

import com.example.foodknowledgehub.helper.AbstractPostgresContainer;
import com.example.foodknowledgehub.modal.vitamin.Vitamin;
import com.example.foodknowledgehub.modal.vitamin.VitaminInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class VitaminInfoRepositoryIT extends AbstractPostgresContainer {

    @Autowired
    private VitaminInfoRepository repo;

    @Test
    void shouldFindVitaminInfoByVitamin() {
        VitaminInfo info = new VitaminInfo();
        info.setVitamin(Vitamin.A);
        info.setOverview("Vitamin A overview");

        repo.save(info);

        Optional<VitaminInfo> result =
                repo.findByVitamin(Vitamin.A);

        assertTrue(result.isPresent());
        assertEquals(Vitamin.A, result.get().getVitamin());
        assertEquals("Vitamin A overview", result.get().getOverview());
    }

}
