package com.fujitsu.app.controller;

import com.fujitsu.app.BaseTest;
import com.fujitsu.app.domain.ExtraFee;
import org.junit.jupiter.api.Test;

import static com.fujitsu.app.factory.TestDataFactory.createExtraFee;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Class for testing extra fee controller (fees based on weather conditions).
 */
public class ExtraFeeControllerTest extends BaseTest {

    private static final String PATH = "extra-fee";

    private static final String PATH_WITH_ID = PATH + "/{id}";

    @Test
    void testGetAll() throws Exception {
        performGet(PATH)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(12));
    }

    @Test
    void testGetById() throws Exception {
        performGet(PATH_WITH_ID, 1L)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(1));

        performGet(PATH_WITH_ID, 6L)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(0));
    }

    @Test
    void testGetByIdNotFound() throws Exception {
        performGet(PATH_WITH_ID, 100L)
                .andExpect(status().isNotFound());
    }

    @Test
    void testAdd() throws Exception {
        ExtraFee fee = createExtraFee();
        Long createdId = 20L;

        performPost(PATH, fee)
                .andExpect(status().isOk())
                .andExpect(content().string(createdId.toString()));

        performGet(PATH_WITH_ID, createdId)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(fee.getAmount()));
    }

    @Test
    void testAddCannotSetId() throws Exception {
        ExtraFee fee = createExtraFee();
        fee.setId(1L);

        performPost(PATH, fee)
                .andExpect(status().isOk());

        performGet(PATH_WITH_ID, fee.getId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(1));
    }

    @Test
    void testAddInvalidVehicle() throws Exception {
        ExtraFee fee = createExtraFee();
        fee.setVehicleTypeId(10L);

        performPost(PATH, fee)
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAddInvalidAmount() throws Exception {
        ExtraFee fee = createExtraFee();
        fee.setAmount(-1);

        performPost(PATH, fee)
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAddInvalidCondition() throws Exception {
        ExtraFee fee = createExtraFee();
        fee.setCondition("");

        performPost(PATH, fee)
                .andExpect(status().isBadRequest());

        fee.setCondition("age == 10");

        performPost(PATH, fee)
                .andExpect(status().isBadRequest());

        fee.setCondition("airTemperature > 'a'");

        performPost(PATH, fee)
                .andExpect(status().isBadRequest());

        fee.setCondition("T(java.lang.System).getenv('PATH')");

        performPost(PATH, fee)
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdate() throws Exception {
        ExtraFee fee = createExtraFee();

        performPut(PATH_WITH_ID, fee.getId(), fee)
                .andExpect(status().isOk());

        performGet(PATH_WITH_ID, fee.getId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(fee.getAmount()));
    }

    @Test
    void testUpdateCannotChangeId() throws Exception {
        ExtraFee fee = createExtraFee();
        Long id = 5L;

        performPut(PATH_WITH_ID, id, fee)
                .andExpect(status().isOk());

        performGet(PATH_WITH_ID, id)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(fee.getAmount()));

        performGet(PATH_WITH_ID, fee.getId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(1));
    }

    @Test
    void testUpdateNotFound() throws Exception {
        ExtraFee fee = createExtraFee();

        performPut(PATH_WITH_ID, 100L, fee)
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateInvalidVehicle() throws Exception {
        ExtraFee fee = createExtraFee();
        fee.setVehicleTypeId(10L);

        performPut(PATH_WITH_ID, fee.getId(), fee)
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateInvalidAmount() throws Exception {
        ExtraFee fee = createExtraFee();
        fee.setAmount(-1);

        performPut(PATH_WITH_ID, fee.getId(), fee)
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdateInvalidCondition() throws Exception {
        ExtraFee fee = createExtraFee();
        fee.setCondition("");

        performPut(PATH_WITH_ID, fee.getId(), fee)
                .andExpect(status().isBadRequest());

        fee.setCondition("age == 10");

        performPut(PATH_WITH_ID, fee.getId(), fee)
                .andExpect(status().isBadRequest());

        fee.setCondition("airTemperature > 'a'");

        performPut(PATH_WITH_ID, fee.getId(), fee)
                .andExpect(status().isBadRequest());

        fee.setCondition("T(java.lang.System).getenv('PATH')");

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
}
