package biz.utilsdev.subnetcalculator.dto;


import lombok.Data;

import java.util.List;

@Data
public class SubnetRequest {

    private String ipBase;
    private int cidr;
    private int numSubnets;
    private List<Integer> hostRequirements;

}
