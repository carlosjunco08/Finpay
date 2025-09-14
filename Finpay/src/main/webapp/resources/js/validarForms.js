 document.addEventListener('DOMContentLoaded', function () {
        const form = document.getElementById('Formulario');
        const btn = form.querySelector('input[type="submit"]');

        btn.addEventListener('click', function (e) {
            const usuario = form.querySelector('#Formulario\\:iTextUsuario').value.trim();
            const clave = form.querySelector('#Formulario\\:iTextPassword').value.trim();

            if (usuario === '' || clave === '') {
                e.preventDefault(); // evita que se envíe el formulario
                Swal.fire({
                    icon: 'warning',
                    title: 'Campos vacíos',
                    text: 'Por favor, complete todos los campos antes de continuar.',
                    confirmButtonColor: '#efb810'
                });
            }
        });
    });
    
    
   