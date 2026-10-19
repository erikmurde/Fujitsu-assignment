package com.fujitsu.app.controller;

import com.fujitsu.app.BaseTest;
import com.fujitsu.app.domain.RegionalBaseFee;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class for testing regional base fee controller.
 */
public class RegionalBaseFeeControllerTest extends BaseTest {

    private static final String PATH = "regional-fee";

    private static final String PATH_WITH_ID = PATH + "/{id}";

    @Test
    void testGetAll() throws Exception {
        performGet(PATH)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(9));
    }

    @Test
    void testGetById() throws Exception {
        performGet(PATH_WITH_ID, 1L)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(4));

        performGet(PATH_WITH_ID, 6L)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(2.5));
    }

    @Test
    void testGetByIdNotFound() throws Exception {
        performGet(PATH_WITH_ID, 100L)
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdate() throws Exception {
        RegionalBaseFee fee = createRegionalBaseFee();

        performPut(PATH_WITH_ID, fee.getId(), fee)
                .andExpect(status().isOk());

        performGet(PATH_WITH_ID, fee.getId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(fee.getAmount()));
    }

    @Test
    void testUpdateInvalidId() throws Exception {
        RegionalBaseFee fee = createRegionalBaseFee();

        performPut(PATH_WITH_ID, 20L, fee)
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateInvalidCity() throws Exception {
        RegionalBaseFee fee = createRegionalBaseFee();
        fee.setCityId(10L);

        performPut(PATH_WITH_ID, fee.getId(), fee)
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateInvalidVehicle() throws Exception {
        RegionalBaseFee fee = createRegionalBaseFee();
        fee.setVehicleTypeId(10L);

        performPut(PATH_WITH_ID, fee.getId(), fee)
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateDuplicateFee() throws Exception {
        RegionalBaseFee fee = createRegionalBaseFee();
        fee.setCityId(2L);

        performPut(PATH_WITH_ID, fee.getId(), fee)
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDelete() throws Exception {
        Long id = 1L;

        performDelete(PATH_WITH_ID, id)
                .andExpect(status().isOk());

        performGet(PATH_WITH_ID, id)
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteNotFound() throws Exception {
        performDelete(PATH_WITH_ID, 100L)
                .andExpect(status().isNotFound());
    }

    private static RegionalBaseFee createRegionalBaseFee() {
        return new RegionalBaseFee(1L, 1L, 1L, 10);
    }
}
