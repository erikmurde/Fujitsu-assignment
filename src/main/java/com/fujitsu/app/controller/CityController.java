package com.fujitsu.app.controller;

import com.fujitsu.app.controller.base.BaseController;
import com.fujitsu.app.domain.City;
import com.fujitsu.app.service.CityService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing cities.
 */
@RestController
@RequestMapping(BaseController.API_PATH + "city")
public class CityController extends BaseController<City, CityService> {

    public CityController(CityService service) {
        super(service);
    }
}
