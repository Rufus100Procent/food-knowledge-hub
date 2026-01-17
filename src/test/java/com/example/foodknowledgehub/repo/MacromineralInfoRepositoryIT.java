package com.example.foodknowledgehub.repo;

import com.example.foodknowledgehub.helper.AbstractPostgresContainer;
import com.example.foodknowledgehub.modal.miniral.Macromineral;
import com.example.foodknowledgehub.modal.miniral.MacromineralInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class MacromineralInfoRepositoryIT extends AbstractPostgresContainer {

    @Autowired
    private MacromineralInfoRepository repository;

    @Test
    void shouldFindMacromineralInfoByMacromineral() {
        MacromineralInfo info = new MacromineralInfo();
        info.setMacromineral(Macromineral.CALCIUM);
        info.setOverview("Calcium overview");
        info.setVerified(true);

        repository.save(info);

        Optional<MacromineralInfo> result =
                repository.findByMacromineral(Macromineral.CALCIUM);

        assertTrue(result.isPresent());
        assertEquals(Macromineral.CALCIUM, result.get().getMacromineral());
        assertEquals("Calcium overview", result.get().getOverview());
    }

}
