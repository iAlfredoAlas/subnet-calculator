package biz.utilsdev.subnetcalculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SubnetControllerUI {

    @GetMapping("/")
    public String showForm(Model model) {
        return "subnet_form";
    }
}
