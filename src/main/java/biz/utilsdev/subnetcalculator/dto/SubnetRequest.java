package biz.utilsdev.subnetcalculator.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubnetRequest {

    private String ipBase;
    private int cidr;
    private int numSubnets;
    private List<Integer> hostRequirements;

}
