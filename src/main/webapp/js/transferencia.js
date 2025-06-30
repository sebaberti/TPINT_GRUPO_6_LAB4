document.addEventListener("DOMContentLoaded", function () {
    const inputVisible = document.getElementById("lblMontoVisible");
    const inputOculto = document.getElementById("lblMonto");

    const formatNumber = (value) => {
        // Eliminar todo lo que no sea número o coma
        value = value.replace(/[^0-9,]/g, '');

        // Separar parte entera y decimal
        let parts = value.split(',');

        // Limitar a un máximo de 2 decimales
        let integerPart = parts[0].replace(/^0+/, '').replace(/\B(?=(\d{3})+(?!\d))/g, '.');
        let decimalPart = parts.length > 1 ? ',' + parts[1].slice(0, 2) : '';

        return integerPart + decimalPart;
    };

    inputVisible.addEventListener("input", () => {
        let cursorPosition = inputVisible.selectionStart;
        let raw = inputVisible.value;

        // Guardamos cuántos caracteres antes del cursor había
        const prevLength = inputVisible.value.length;

        // Formateamos el valor
        let formatted = formatNumber(raw);
        inputVisible.value = formatted;

        // Convertimos a valor numérico en formato backend (500000.25)
        const numericValue = formatted.replace(/\./g, '').replace(',', '.');
        inputOculto.value = numericValue;

        // Restaurar cursor
        const newLength = formatted.length;
        inputVisible.setSelectionRange(cursorPosition + (newLength - prevLength), cursorPosition + (newLength - prevLength));
    });

    inputVisible.addEventListener("keydown", function (e) {
        // Permitimos coma solo si aún no fue escrita
        if (e.key === ',' && inputVisible.value.includes(',')) {
            e.preventDefault();
        }
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const cbuInput = document.getElementById("lblCBU");

    // Solo permitir teclas numéricas
    cbuInput.addEventListener("keydown", function (e) {
        const tecla = e.key;

        // Permitir teclas de control como backspace, delete, tab, etc.
        if (
            tecla === "Backspace" || tecla === "Delete" || tecla === "ArrowLeft" ||
            tecla === "ArrowRight" || tecla === "Tab"
        ) {
            return;
        }

        // Bloquear todo lo que no sea número
        if (!/^[0-9]$/.test(tecla)) {
            e.preventDefault();
        }
    });

    // Evitar pegar contenido no numérico
    cbuInput.addEventListener("paste", function (e) {
        const textoPegado = (e.clipboardData || window.clipboardData).getData("text");
        if (!/^\d+$/.test(textoPegado)) {
            e.preventDefault();
        }
    });
});