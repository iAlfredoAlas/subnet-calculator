document.getElementById("subnetForm").addEventListener("submit", function(event) {
    event.preventDefault();

    // Obtener valores del formulario
    const ipBase = document.getElementById("ipBase");
    const cidr = document.getElementById("cidr");
    const numSubnets = document.getElementById("numSubnets");
    const hostRequirements = document.getElementById("hostRequirements");

    let isValid = true;
    const ipPattern = /^(\d{1,3}\.){3}\d{1,3}$/;

    if (!ipPattern.test(ipBase.value)) {
        ipBase.classList.add("is-invalid");
        isValid = false;
    } else {
        ipBase.classList.remove("is-invalid");
    }

    if (!isValid) return;

    axios.post("/api/subnet/calculate", {
        ipBase: ipBase.value,
        cidr: parseInt(cidr.value),
        numSubnets: parseInt(numSubnets.value),
        hostRequirements: hostRequirements.value.split(",").map(h => parseInt(h.trim()))
    })
        .then(response => {
            const tbody = document.querySelector("#resultsTable tbody");
            tbody.innerHTML = "";
            response.data.subnets.forEach(subnet => {
                tbody.innerHTML += `
                <tr>
                    <td data-label="Nombre">${subnet.name}</td>
                    <td data-label="IP de Red">${subnet.networkAddress}</td>
                    <td data-label="Máscara">${subnet.subnetMask}</td>
                    <td data-label="Gateway">${subnet.gateway}</td>
                    <td data-label="Primera IP">${subnet.firstUsableIp}</td>
                    <td data-label="Última IP">${subnet.lastUsableIp}</td>
                    <td data-label="Broadcast">${subnet.broadcast}</td>
                </tr>
            `;
            });
        })
        .catch(() => alert("Error al calcular subredes."));
});
