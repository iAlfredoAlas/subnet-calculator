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
        response.setSubnetMask(getSubnetMask(request.getCidr()));
        response.setWildcardMask(getWildcardMask(request.getCidr()));

        List<SubnetResponse.SubnetInfo> subnets = new ArrayList<>();

        // Convertir la IP base a entero
        String[] ipParts = request.getIpBase().split("\\.");
        int baseIp = (Integer.parseInt(ipParts[0]) << 24) |
                (Integer.parseInt(ipParts[1]) << 16) |
                (Integer.parseInt(ipParts[2]) << 8) |
                Integer.parseInt(ipParts[3]);

        // Calcular el tamaño del bloque basado en el CIDR
        int blockSize = (int) Math.pow(2, (32 - request.getCidr()));

        // Validar que haya espacio suficiente
        if (blockSize < request.getNumSubnets()) {
            throw new IllegalArgumentException("No hay suficiente espacio en la subred base para acomodar todas las subredes.");
        }

        int currentIp = baseIp;
        for (int i = 0; i < request.getNumSubnets(); i++) {
            int broadcastAddress = currentIp + blockSize - 1;

            SubnetResponse.SubnetInfo subnetInfo = new SubnetResponse.SubnetInfo();
            subnetInfo.setName("Red " + (i + 1));
            subnetInfo.setNetworkAddress(intToIp(currentIp));
            subnetInfo.setSubnetMask(getSubnetMask(request.getCidr()));
            subnetInfo.setGateway(intToIp(currentIp + 1));
            subnetInfo.setFirstUsableIp(intToIp(currentIp + 2));
            subnetInfo.setLastUsableIp(intToIp(broadcastAddress - 1));
            subnetInfo.setBroadcast(intToIp(broadcastAddress));

            subnets.add(subnetInfo);

            currentIp += blockSize; // Avanzar a la siguiente subred
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
