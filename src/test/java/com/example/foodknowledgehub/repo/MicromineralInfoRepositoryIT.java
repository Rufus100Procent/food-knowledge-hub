package com.example.foodknowledgehub.repo;

import com.example.foodknowledgehub.helper.AbstractPostgresContainer;
import com.example.foodknowledgehub.modal.miniral.Micromineral;
import com.example.foodknowledgehub.modal.miniral.MicromineralInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class MicromineralInfoRepositoryIT extends AbstractPostgresContainer {

    @Autowired
    private MicromineralInfoRepository repo;

    @Test
    void shouldFindMicromineralInfoByMicromineral() {
        MicromineralInfo info = new MicromineralInfo();
        info.setMicromineral(Micromineral.IRON);
        info.setOverview("Iron overview");

        repo.save(info);

        Optional<MicromineralInfo> result =
                repo.findByMicromineral(Micromineral.IRON);

        assertTrue(result.isPresent());
        assertEquals(Micromineral.IRON, result.get().getMicromineral());
        assertEquals("Iron overview", result.get().getOverview());
    }
}
