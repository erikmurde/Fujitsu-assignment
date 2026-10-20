package com.fujitsu.app.controller;

import com.fujitsu.app.BaseTest;
import com.fujitsu.app.domain.City;
import com.fujitsu.app.domain.RegionalBaseFee;
import com.fujitsu.app.domain.VehicleType;
import com.fujitsu.app.service.CityService;
import com.fujitsu.app.service.VehicleTypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.fujitsu.app.factory.TestDataFactory.createRegionalBaseFee;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Class for testing regional base fee controller.
 */
public class RegionalBaseFeeControllerTest extends BaseTest {

    private static final String PATH = "regional-fee";

    private static final String PATH_WITH_ID = PATH + "/{id}";

    @Autowired
    private CityService cityService;

    @Autowired
    private VehicleTypeService vehicleTypeService;

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
    void testAdd() throws Exception {
        RegionalBaseFee fee = createRegionalBaseFee();
        Long createdId = 20L;

        fee.setCityId(createTestCity());
        fee.setVehicleTypeId(createTestVehicleType());

        performPost(PATH, fee)
                .andExpect(status().isOk())
                .andExpect(content().string(createdId.toString()));

        performGet(PATH_WITH_ID, createdId)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(fee.getAmount()));
    }

    @Test
    void testAddCannotSetId() throws Exception {
        RegionalBaseFee fee = createRegionalBaseFee();
        fee.setId(1L);

        fee.setCityId(createTestCity());
        fee.setVehicleTypeId(createTestVehicleType());

        performPost(PATH, fee)
                .andExpect(status().isOk());

        performGet(PATH_WITH_ID, fee.getId())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(4));
    }

    @Test
    void testAddInvalidCity() throws Exception {
        RegionalBaseFee fee = createRegionalBaseFee();
        fee.setCityId(10L);

        performPost(PATH, fee)
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAddInvalidVehicle() throws Exception {
        RegionalBaseFee fee = createRegionalBaseFee();
        fee.setVehicleTypeId(10L);

        performPost(PATH, fee)
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAddInvalidAmount() throws Exception {
        RegionalBaseFee fee = createRegionalBaseFee();
        fee.setAmount(-1);

        performPost(PATH, fee)
                .andExpect(status().isBadRequest());
    }

    @Test
    void testAddDuplicateFee() throws Exception {
        RegionalBaseFee fee = createRegionalBaseFee();

        performPost(PATH, fee)
                .andExpect(status().isBadRequest());
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
    void testUpdateInvalidAmount() throws Exception {
        RegionalBaseFee fee = createRegionalBaseFee();
        fee.setAmount(-1);

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

    private Long createTestCity() {
        return cityService.create(new City(null, "HAAPSALU"));
    }

    private Long createTestVehicleType() {
        return vehicleTypeService.create(new VehicleType(null, "TRUCK"));
    }
}
