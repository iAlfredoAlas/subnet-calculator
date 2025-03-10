package biz.utilsdev.subnetcalculator.test;

import biz.utilsdev.subnetcalculator.dto.SubnetRequest;
import biz.utilsdev.subnetcalculator.dto.SubnetResponse;
import biz.utilsdev.subnetcalculator.service.SubnetCalculatorService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class SubnetCalculatorServiceTests {

    private final SubnetCalculatorService subnetCalculatorService = new SubnetCalculatorService();

    // 🔹 Test 1: Subneteo básico con un solo bloque /16
    @Test
    void testSingleSubnetWithFixedCidr() {
        SubnetRequest request = new SubnetRequest("129.2.0.0", 16, 1, List.of(2));
        SubnetResponse response = subnetCalculatorService.calculateSubnets(request);

        assertEquals("129.2.0.0", response.getIpBase());
        assertEquals("255.255.0.0", response.getSubnetMask());
        assertEquals("0.0.255.255", response.getWildcardMask());
        assertEquals(1, response.getSubnets().size());

        SubnetResponse.SubnetInfo subnet = response.getSubnets().get(0);
        assertEquals("129.2.0.0", subnet.getNetworkAddress());
        assertEquals("255.255.0.0", subnet.getSubnetMask());
        assertEquals("129.2.0.1", subnet.getGateway());
        assertEquals("129.2.0.2", subnet.getFirstUsableIp());
        assertEquals("129.2.255.254", subnet.getLastUsableIp());
        assertEquals("129.2.255.255", subnet.getBroadcast());
    }

    // 🔹 Test 2: Varias subredes dentro de un bloque /16
    @Test
    void testMultipleSubnetsWithFixedCidr() {
        SubnetRequest request = new SubnetRequest("129.2.0.0", 16, 2, Arrays.asList(2, 2));
        SubnetResponse response = subnetCalculatorService.calculateSubnets(request);

        assertEquals(2, response.getSubnets().size());

        SubnetResponse.SubnetInfo subnet1 = response.getSubnets().get(0);
        assertEquals("129.2.0.0", subnet1.getNetworkAddress());
        assertEquals("255.255.0.0", subnet1.getSubnetMask());

        SubnetResponse.SubnetInfo subnet2 = response.getSubnets().get(1);
        assertEquals("129.3.0.0", subnet2.getNetworkAddress());
        assertEquals("255.255.0.0", subnet2.getSubnetMask());
    }

    // 🔹 Test 3: Subneteo en un bloque más pequeño (/24)
    @Test
    void testSubnetInSmallerBlock() {
        SubnetRequest request = new SubnetRequest("192.168.1.0", 24, 2, List.of(2, 2));
        SubnetResponse response = subnetCalculatorService.calculateSubnets(request);

        assertEquals(2, response.getSubnets().size());

        SubnetResponse.SubnetInfo subnet1 = response.getSubnets().get(0);
        assertEquals("192.168.1.0", subnet1.getNetworkAddress());
        assertEquals("255.255.255.0", subnet1.getSubnetMask());

        SubnetResponse.SubnetInfo subnet2 = response.getSubnets().get(1);
        assertEquals("192.168.2.0", subnet2.getNetworkAddress());
        assertEquals("255.255.255.0", subnet2.getSubnetMask());
    }

    // 🔹 Test 4: No hay suficiente espacio para subredes
    @Test
    void testInsufficientSpaceForSubnets() {
        SubnetRequest request = new SubnetRequest("192.168.1.0", 30, 2, List.of(2, 2));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            subnetCalculatorService.calculateSubnets(request);
        });

        assertTrue(exception.getMessage().contains("No hay suficiente espacio en la subred base"));
    }

    // 🔹 Test 5: CIDR inválido
    @Test
    void testInvalidCidr() {
        SubnetRequest request = new SubnetRequest("10.0.0.0", 32, 1, List.of(2));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            subnetCalculatorService.calculateSubnets(request);
        });

        assertTrue(exception.getMessage().contains("No hay suficiente espacio en la subred base"));
    }

    // 🔹 Test 6: Subred con hosts mayores a lo permitido
    @Test
    void testHostsExceedingSubnetSize() {
        SubnetRequest request = new SubnetRequest("192.168.1.0", 28, 1, List.of(20));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            subnetCalculatorService.calculateSubnets(request);
        });

        assertTrue(exception.getMessage().contains("No hay suficiente espacio en la subred base"));
    }
}
