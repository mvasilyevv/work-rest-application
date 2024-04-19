document.addEventListener('DOMContentLoaded', function() {
    const paginationLinks = document.querySelectorAll('.page-link');

    // Обработчик клика на ссылки пагинации
    paginationLinks.forEach(function(link) {
        link.addEventListener('click', function(event) {
            event.preventDefault();
            var page = this.getAttribute('data-page');
            loadPersons(page);
        });
    });

    // Функция для загрузки списка людей с сервера
    function loadPersons(page) {
        const url = '/admin?page=' + page;
        fetch(url)
            .then(response => response.text())
            .then(data => {
                var contentContainer = document.getElementById('contentContainer');
                contentContainer.innerHTML = data;
            });
    }
});