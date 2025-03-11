document.getElementById("subnetForm").addEventListener("submit", function(event) {
    event.preventDefault(); // Evita recargar la página al enviar el formulario

    // Obtener valores del formulario
    const ipBase = document.getElementById("ipBase");
    const cidr = document.getElementById("cidr");
    const numSubnets = document.getElementById("numSubnets");
    const hostRequirements = document.getElementById("hostRequirements");

    let isValid = true;
    const ipPattern = /^(\d{1,3}\.){3}\d{1,3}$/;

    // Validación de IP Base
    if (!ipPattern.test(ipBase.value)) {
        ipBase.classList.add("is-invalid");
        isValid = false;
    } else {
        ipBase.classList.remove("is-invalid");
    }

    // Validación de CIDR (Debe estar entre 8 y 30)
    if (cidr.value < 8 || cidr.value > 30 || isNaN(cidr.value)) {
        cidr.classList.add("is-invalid");
        isValid = false;
    } else {
        cidr.classList.remove("is-invalid");
    }

    // Validación de Número de Subredes (Debe ser mayor a 0)
    if (numSubnets.value < 1 || isNaN(numSubnets.value)) {
        numSubnets.classList.add("is-invalid");
        isValid = false;
    } else {
        numSubnets.classList.remove("is-invalid");
    }

    // Validación de Hosts por Subred (Lista de números separados por comas)
    let hostsArray = hostRequirements.value.split(",").map(h => parseInt(h.trim()));
    if (hostsArray.some(h => isNaN(h) || h < 1)) {
        hostRequirements.classList.add("is-invalid");
        isValid = false;
    } else {
        hostRequirements.classList.remove("is-invalid");
    }

    // Si hay errores, no enviar la solicitud
    if (!isValid) {
        return;
    }

    // 🔹 Enviar petición al backend
    axios.post("/api/subnet/calculate", {
        ipBase: ipBase.value,
        cidr: parseInt(cidr.value),
        numSubnets: parseInt(numSubnets.value),
        hostRequirements: hostsArray
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
        .catch(() => alert("Error al calcular subredes. Verifique los valores ingresados."));
});

// Validaciones en tiempo real (elimina .is-invalid cuando el usuario corrige un error)
document.querySelectorAll(".form-control").forEach(input => {
    input.addEventListener("input", () => {
        input.classList.remove("is-invalid");
    });
});
document.addEventListener("DOMContentLoaded", function () {
    // 🔹 Copiar JSON al portapapeles
    document.getElementById("copyJson").addEventListener("click", function () {
        const tableBody = document.querySelector("#resultsTable tbody");

        if (!tableBody || tableBody.children.length === 0) {
            alert("No hay datos para copiar.");
            return;
        }

        const subnets = [];
        tableBody.querySelectorAll("tr").forEach(row => {
            const cells = row.querySelectorAll("td");
            subnets.push({
                name: cells[0].innerText,
                networkAddress: cells[1].innerText,
                subnetMask: cells[2].innerText,
                gateway: cells[3].innerText,
                firstUsableIp: cells[4].innerText,
                lastUsableIp: cells[5].innerText,
                broadcast: cells[6].innerText
            });
        });

        const jsonData = {
            ipBase: document.getElementById("ipBase").value,
            cidr: parseInt(document.getElementById("cidr").value),
            numSubnets: parseInt(document.getElementById("numSubnets").value),
            subnets: subnets
        };

        navigator.clipboard.writeText(JSON.stringify(jsonData, null, 2))
            .then(() => {
                alert("JSON copiado al portapapeles.");
            })
            .catch(err => {
                console.error("Error al copiar JSON:", err);
                alert("Error al copiar JSON.");
            });
    });

    // 🔹 Copiar Tabla como texto tabulado
    document.getElementById("copyTable").addEventListener("click", function () {
        const tableBody = document.querySelector("#resultsTable tbody");

        if (!tableBody || tableBody.children.length === 0) {
            alert("No hay datos para copiar.");
            return;
        }

        let textTable = "Nombre\tIP de Red\tMáscara\tGateway\tPrimera IP\tÚltima IP\tBroadcast\n";
        tableBody.querySelectorAll("tr").forEach(row => {
            const cells = row.querySelectorAll("td");
            textTable += `${cells[0].innerText}\t${cells[1].innerText}\t${cells[2].innerText}\t${cells[3].innerText}\t${cells[4].innerText}\t${cells[5].innerText}\t${cells[6].innerText}\n`;
        });

        navigator.clipboard.writeText(textTable)
            .then(() => {
                alert("Tabla copiada al portapapeles.");
            })
            .catch(err => {
                console.error("Error al copiar la tabla:", err);
                alert("Error al copiar la tabla.");
            });
    });
});