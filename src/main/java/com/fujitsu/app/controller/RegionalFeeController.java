package com.fujitsu.app.controller;

import com.fujitsu.app.controller.base.BaseController;
import com.fujitsu.app.domain.RegionalBaseFee;
import com.fujitsu.app.service.RegionalFeeService;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing regional base fees.
 */
@RestController
@RequestMapping(BaseController.API_PATH + "regional-fee")
public class RegionalFeeController extends BaseController<RegionalBaseFee, RegionalFeeService> {

    public RegionalFeeController(RegionalFeeService service) {
        super(service);
    }
}
