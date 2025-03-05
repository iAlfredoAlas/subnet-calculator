package biz.utilsdev.subnetcalculator.controller;

import biz.utilsdev.subnetcalculator.dto.SubnetRequest;
import biz.utilsdev.subnetcalculator.dto.SubnetResponse;
import biz.utilsdev.subnetcalculator.service.SubnetCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class SubnetController {

    @Autowired
    private SubnetCalculatorService subnetCalculatorService;

    @PostMapping("/calculate")
    public SubnetResponse calculateSubnet(@RequestBody SubnetRequest request) {
        return subnetCalculatorService.calculateSubnets(request);
    }

}
