document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('registroForm');
    const btn = form.querySelector('button, input[type="submit"]');
    btn.addEventListener('click', function (e) {
        const ids = ['documento', 'nombre', 'apellidos', 'email', 'telefono', 'direccion', 'password', 'fechaNacimiento'];
        const empty = ids.some(id => {
            const el = form.querySelector(`#registroForm\\:${id}`);
            return !el || el.value.trim() === '';
        });
        if (empty) {
            e.preventDefault();
            Swal.fire({
                icon: 'warning',
                title: 'Campos vacíos',
                text: 'Por favor complete todos los campos obligatorios.',
                confirmButtonColor: '#efb810'
            });
        }
    });
});


