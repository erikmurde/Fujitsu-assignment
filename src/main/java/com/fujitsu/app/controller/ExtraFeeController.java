package com.fujitsu.app.controller;

import com.fujitsu.app.controller.base.BaseController;
import com.fujitsu.app.domain.ExtraFee;
import com.fujitsu.app.service.ExtraFeeService;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing extra fees related to weather conditions.
 */
@RestController
@RequestMapping(BaseController.API_PATH + "extra-fee")
public class ExtraFeeController extends BaseController<ExtraFee, ExtraFeeService> {

    public ExtraFeeController(ExtraFeeService service) {
        super(service);
    }
}
