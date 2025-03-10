package biz.utilsdev.subnetcalculator.controller;

import biz.utilsdev.subnetcalculator.dto.SubnetRequest;
import biz.utilsdev.subnetcalculator.dto.SubnetResponse;
import biz.utilsdev.subnetcalculator.service.SubnetCalculatorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/subnet")
public class SubnetController {

    @Autowired
    private SubnetCalculatorService subnetCalculatorService;

    @PostMapping("/calculate")
    public ResponseEntity<?> calculateSubnet(@Valid @RequestBody SubnetRequest request, BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            result.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        SubnetResponse response = subnetCalculatorService.calculateSubnets(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
