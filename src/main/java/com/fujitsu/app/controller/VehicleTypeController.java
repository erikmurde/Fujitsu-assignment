package com.fujitsu.app.controller;

import com.fujitsu.app.controller.base.BaseController;
import com.fujitsu.app.domain.VehicleType;
import com.fujitsu.app.service.VehicleTypeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing vehicle types.
 */
@RestController
@RequestMapping(BaseController.API_PATH + "vehicle-type")
public class VehicleTypeController extends BaseController<VehicleType, VehicleTypeService> {

    public VehicleTypeController(VehicleTypeService service) {
        super(service);
    }
}
