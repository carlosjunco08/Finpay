/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


window.addEventListener('DOMContentLoaded', function () {
    // Datos por país
    const jsonDataPais = document.getElementById('formPais:usuariosPorPaisData').value;
    const dataParsedPais = JSON.parse(jsonDataPais);

    const labelsPais = dataParsedPais.map(item => item.pais);
    const valuesPais = dataParsedPais.map(item => item.cantidad);

    const backgroundColorsPais = ['#33cc99', '#ff6384', '#ffcd56', '#36a2eb', '#9966ff', '#ff9f40'];

    const ctxPais = document.getElementById('usuariosPorPaisChart').getContext('2d');
    new Chart(ctxPais, {
        type: 'pie',
        data: {
            labels: labelsPais,
            datasets: [{
                label: 'Cantidad de Usuarios',
                
                data: valuesPais,
                backgroundColor: backgroundColorsPais.slice(0, valuesPais.length),
                borderColor: '#248a66',
                borderWidth: 1
            }]
        },
        options: {
        responsive: true,
        plugins: {
            legend: {
                labels: {
                    color: '#36a2eb', // Cambia aquí el color del texto "Cantidad de Usuarios" en la leyenda
                    font: {
                        weight: 'bold',
                        size: 14
                    }
                }
            },
            tooltip: {
                titleColor: '#36a2eb', // Color del título del tooltip
                bodyColor: '#36a2eb'   // Color del cuerpo del tooltip
            }
        },
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
    });

    // Datos por rol
    const jsonData = document.getElementById('form:usuariosPorRolData').value;
    const dataParsed = JSON.parse(jsonData);

    const labels = dataParsed.map(item => item.rol);
    const values = dataParsed.map(item => item.cantidad);

    const backgroundColorsRol = ['#36a2eb', '#ffcd56', '#9966ff', '#ff9f40'];

    const ctx = document.getElementById('usuariosPorRolChart').getContext('2d');
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Cantidad de Usuarios',
                data: values,
                backgroundColor: backgroundColorsRol.slice(0, values.length),
                borderColor: '#1c6cc2',
                borderWidth: 1
            }]
        },
       options: {
        responsive: true,
        plugins: {
            legend: {
                labels: {
                    color: '#36a2eb', // Cambia aquí el color del texto "Cantidad de Usuarios" en la leyenda
                    font: {
                        weight: 'bold',
                        size: 14
                    }
                }
            },
            tooltip: {
                titleColor: '#36a2eb', // Color del título del tooltip
                bodyColor: '#36a2eb'   // Color del cuerpo del tooltip
            }
        },
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
    });
});
