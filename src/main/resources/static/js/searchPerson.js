function loadUserData() {
    var userId = document.getElementById("userSelect").value;
    if (userId) {
        fetch(`/work-hours/person/${userId}`)
            .then(response => response.json())
            .then(data => {
                console.log('Received data:', data); // Добавлено для отладки
                buildWorkHoursTable(data);
            })
            .catch(error => console.error('Error fetching data:', error));
    }
}

function buildWorkHoursTable(data) {
    var tableContainer = document.getElementById("userWorkHoursTable");
    tableContainer.innerHTML = '';

    var table = document.createElement('table');
    table.className = 'table table-striped';

    var thead = table.createTHead();
    var headerRow = thead.insertRow();
    headerRow.insertCell().textContent = 'Дата';
    for (var i = 0; i < 24; i++) {
        headerRow.insertCell().textContent = i + ':00';
    }

    var tbody = table.createTBody();
    data.workDays.forEach(workDay => {
        var row = tbody.insertRow();
        var dateCell = row.insertCell();
        dateCell.textContent = workDay.currentDay; // Формат даты может потребовать изменений

        var hoursCell = row.insertCell();
        hoursCell.colSpan = "24";
        var hoursContainer = document.createElement('div');
        hoursContainer.className = 'hours-selector';
        for (var hour = 0; hour < 24; hour++) {
            var hourButton = document.createElement('button');
            hourButton.textContent = hour + ':00';
            hourButton.className = workDay.workHours.includes(hour) ? 'btn-hour-selected' : 'btn-hour';
            hourButton.disabled = true; // делаем кнопку некликабельной
            hoursContainer.appendChild(hourButton);
        }
        hoursCell.appendChild(hoursContainer);
    });

    tableContainer.appendChild(table);
}
