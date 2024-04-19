document.addEventListener('DOMContentLoaded', function() {
    const searchInput = document.getElementById('searchInput');
    const tableBody = document.querySelector('table tbody');

    // Обработчик ввода текста в поле поиска
    searchInput.addEventListener('input', function() {
        const query = this.value.trim().toLowerCase();
        filterPersons(query);
    });

    // Функция для фильтрации людей по имени или фамилии
    function filterPersons(query) {
        const rows = tableBody.getElementsByTagName('tr');

        for (let i = 0; i < rows.length; i++) {
            const firstName = rows[i].getElementsByTagName('td')[1].innerText.toLowerCase();
            const lastName = rows[i].getElementsByTagName('td')[2].innerText.toLowerCase();

            if (firstName.includes(query) || lastName.includes(query)) {
                rows[i].style.display = '';
            } else {
                rows[i].style.display = 'none';
            }
        }
    }
});