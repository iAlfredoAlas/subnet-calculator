package biz.utilsdev.subnetcalculator.service;

import biz.utilsdev.subnetcalculator.dto.SubnetRequest;
import biz.utilsdev.subnetcalculator.dto.SubnetResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubnetCalculatorService {

    public SubnetResponse calculateSubnets(SubnetRequest request) {
        SubnetResponse response = new SubnetResponse();
        response.setIpBase(request.getIpBase());
        response.setSubnetMask(getSubnetMask(request.getCidr())); // Máscara base
        response.setWildcardMask(getWildcardMask(request.getCidr()));

        List<SubnetResponse.SubnetInfo> subnets = new ArrayList<>();

        // Convertir la IP base a entero
        String[] ipParts = request.getIpBase().split("\\.");
        int baseIp = (Integer.parseInt(ipParts[0]) << 24) |
                (Integer.parseInt(ipParts[1]) << 16) |
                (Integer.parseInt(ipParts[2]) << 8) |
                Integer.parseInt(ipParts[3]);

        int currentIp = baseIp;

        for (int i = 0; i < request.getNumSubnets(); i++) {
            int requiredHosts = request.getHostRequirements().get(i);

            // Determinar el CIDR adecuado (mínimo que cubra los hosts requeridos)
            int subnetBits = 32 - Integer.numberOfLeadingZeros(requiredHosts + 2); // +2 para red y broadcast
            int cidr = 32 - subnetBits; // CIDR óptimo
            int subnetSize = (int) Math.pow(2, subnetBits);

            int broadcastAddress = currentIp + subnetSize - 1;

            SubnetResponse.SubnetInfo subnetInfo = new SubnetResponse.SubnetInfo();
            subnetInfo.setName("Red " + (i + 1));
            subnetInfo.setNetworkAddress(intToIp(currentIp));
            subnetInfo.setSubnetMask(getSubnetMask(cidr)); // Asigna el CIDR óptimo
            subnetInfo.setGateway(intToIp(currentIp + 1));
            subnetInfo.setFirstUsableIp(intToIp(currentIp + 2));
            subnetInfo.setLastUsableIp(intToIp(broadcastAddress - 1));
            subnetInfo.setBroadcast(intToIp(broadcastAddress));

            subnets.add(subnetInfo);

            currentIp += subnetSize; // Avanzar a la siguiente subred
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

    private String intToIp(int ip) {
        return String.format("%d.%d.%d.%d",
                (ip >> 24) & 0xff,
                (ip >> 16) & 0xff,
                (ip >> 8) & 0xff,
                ip & 0xff);
    }
}
