package biz.utilsdev.subnetcalculator.service;

import biz.utilsdev.subnetcalculator.dto.SubnetRequest;
import biz.utilsdev.subnetcalculator.dto.SubnetResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SubnetCalculatorService {

    public SubnetResponse calculateSubnets(SubnetRequest request) {
        SubnetResponse response = new SubnetResponse();
        response.setIpBase(request.getIpBase());
        response.setSubnetMask(getSubnetMask(request.getCidr()));
        response.setWildcardMask(getWildcardMask(request.getCidr()));

        List<Integer> sortedHosts = new ArrayList<>(request.getHostRequirements());
        sortedHosts.sort(Collections.reverseOrder());  // Ordenar de mayor a menor

        List<SubnetResponse.SubnetInfo> subnets = new ArrayList<>();

        String[] ipParts = request.getIpBase().split("\\.");
        int baseIp = (Integer.parseInt(ipParts[0]) << 24) |
                (Integer.parseInt(ipParts[1]) << 16) |
                (Integer.parseInt(ipParts[2]) << 8) |
                Integer.parseInt(ipParts[3]);

        int currentIp = baseIp;  // Dirección base actual

        for (int requiredHosts : sortedHosts) {
            int subnetSize = getNextPowerOfTwo(requiredHosts + 2); // +2 por red y broadcast
            int cidr = 32 - Integer.numberOfTrailingZeros(subnetSize);

            int broadcastAddress = currentIp + subnetSize - 1;

            SubnetResponse.SubnetInfo subnetInfo = new SubnetResponse.SubnetInfo();
            subnetInfo.setNetworkAddress(intToIp(currentIp));
            subnetInfo.setSubnetMask(getSubnetMask(cidr));
            subnetInfo.setGateway(intToIp(currentIp + 1));
            subnetInfo.setFirstUsableIp(intToIp(currentIp + 2));
            subnetInfo.setLastUsableIp(intToIp(broadcastAddress - 1));
            subnetInfo.setBroadcast(intToIp(broadcastAddress));

            subnets.add(subnetInfo);

            currentIp = broadcastAddress + 1; // La siguiente subred empieza justo después
        }

        response.setSubnets(subnets);
        return response;
    }

    private String getSubnetMask(int cidr) {
        int mask = 0xffffffff << (32 - cidr);
        return String.format("%d.%d.%d.%d",
                (mask >> 24) & 0xff,
                (mask >> 16) & 0xff,
                (mask >> 8) & 0xff,
                mask & 0xff);
    }

    private String getWildcardMask(int cidr) {
        int wildcard = (1 << (32 - cidr)) - 1;
        return String.format("%d.%d.%d.%d",
                (wildcard >> 24) & 0xff,
                (wildcard >> 16) & 0xff,
                (wildcard >> 8) & 0xff,
                wildcard & 0xff);
    }

    private int getNextPowerOfTwo(int n) {
        int power = 1;
        while (power < n) {
            power *= 2;
        }
        return power;
    }

    private String intToIp(int ip) {
        return String.format("%d.%d.%d.%d",
                (ip >> 24) & 0xff,
                (ip >> 16) & 0xff,
                (ip >> 8) & 0xff,
                ip & 0xff);
    }

}
