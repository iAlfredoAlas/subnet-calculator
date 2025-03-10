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
public class SubnetResponse {

    private String ipBase;
    private String subnetMask;
    private String wildcardMask;
    private List<SubnetInfo> subnets;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubnetInfo {
        private String name;
        private String networkAddress;
        private String subnetMask;
        private String gateway;
        private String firstUsableIp;
        private String lastUsableIp;
        private String broadcast;
    }
}
