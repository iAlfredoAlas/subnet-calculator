package biz.utilsdev.subnetcalculator.dto;

import lombok.Data;

import java.util.List;

@Data
public class SubnetResponse {

    private String ipBase;
    private String subnetMask;
    private String wildcardMask;
    private List<SubnetInfo> subnets;

    @Data
    public static class SubnetInfo {
        private String networkAddress;
        private String subnetMask;
        private String gateway;
        private String firstUsableIp;
        private String lastUsableIp;
        private String broadcast;
    }

}
