package biz.utilsdev.subnetcalculator.dto;


import jakarta.validation.constraints.*;
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

    @NotNull(message = "La IP base no puede ser nula.")
    @Pattern(regexp = "^(\\d{1,3}\\.){3}\\d{1,3}$", message = "Formato de IP inválido. Debe tener cuatro octetos (ejemplo: 192.168.1.1).")
    private String ipBase;

    @Min(value = 8, message = "El CIDR debe ser al menos /8.")
    @Max(value = 30, message = "El CIDR no puede ser mayor a /30.")
    private int cidr;

    @Min(value = 1, message = "Debe haber al menos una subred.")
    private int numSubnets;

    @NotEmpty(message = "Debe especificar la cantidad de hosts por cada subred.")
    private List<@Min(value = 1, message = "Cada subred debe tener al menos 1 host.") Integer> hostRequirements;

}
