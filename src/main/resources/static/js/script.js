document.addEventListener('DOMContentLoaded', function() {
    const WORK_HOURS_NORM = 8; // Константа для норматива рабочего времени
    let isMouseDown = false;
    let isSelecting = true;
    let startHour = null;
    let selectedHours = new Set();

    document.querySelectorAll('.btn-hour, .btn-hour-selected').forEach(button => {
        button.addEventListener('mousedown', function(event) {
            isMouseDown = true;
            startHour = parseInt(this.textContent.trim());
            isSelecting = !selectedHours.has(startHour); // Определяем, будем ли мы выбирать или убирать часы
            toggleHourSelection(startHour); // Переключаем начальный час
            event.preventDefault();
        });

        button.addEventListener('mouseover', function(event) {
            if (isMouseDown) {
                const currentHour = parseInt(this.textContent.trim());
                // Если мы находимся в режиме выбора, выбираем диапазон
                if (isSelecting) {
                    selectRange(startHour, currentHour);
                } else {
                    deselectRange(startHour, currentHour);
                }
            }
        });

        button.addEventListener('mouseup', endSelection);
    });

    document.addEventListener('mouseup', endSelection);

    function selectRange(start, end) {
        for (let i = Math.min(start, end); i <= Math.max(start, end); i++) {
            if (!selectedHours.has(i)) {
                toggleHourSelection(i);
            }
        }
    }

    function deselectRange(start, end) {
        for (let i = Math.min(start, end); i <= Math.max(start, end); i++) {
            if (selectedHours.has(i)) {
                toggleHourSelection(i);
                updateColorIndicator();
            }
        }
    }

    function endSelection(event) {
        if (isMouseDown) {
            isMouseDown = false;
            isSelecting = true; // Сбрасываем на дефолтное значение для следующего выбора
        }
    }

    function updateColorIndicator() {
        document.querySelectorAll('.btn-hour').forEach(button => {
            // Удалить все классы цветов у не выбранных часов
            button.classList.remove('hours-under', 'hours-norm', 'hours-over');
        });

        const hoursCount = selectedHours.size;
        selectedHours.forEach(hour => {
            const button = document.getElementById('hour-' + hour);
            // Удалить все классы цветов перед обновлением, чтобы не было конфликта стилей
            button.classList.remove('hours-under', 'hours-norm', 'hours-over');

            if (hoursCount < WORK_HOURS_NORM) {
                button.classList.add('hours-under');
            } else if (hoursCount === WORK_HOURS_NORM) {
                button.classList.add('hours-norm');
            } else {
                button.classList.add('hours-over');
            }
        });
    }


    function toggleHourSelection(hour) {
        const button = document.getElementById('hour-' + hour);
        if (selectedHours.has(hour)) {
            selectedHours.delete(hour);
            button.classList.remove('btn-hour-selected');
        } else {
            selectedHours.add(hour);
            button.classList.add('btn-hour-selected');
        }
        updateColorIndicator(); // Обновляем цветовую индикацию при каждом изменении выбора
    }


    // Вызываем обновление цвета при первой загрузке страницы
    updateColorIndicator();
});

document.querySelectorAll('.btn-hour').forEach(button => {
    button.addEventListener('click', function(event) {
        const hour = parseInt(this.textContent.trim().split(':')[0]);
        const hiddenInput = document.getElementById('input-hour-' + hour);

        // Проверяем, был ли час уже выбран
        if (selectedHours.has(hour)) {
            selectedHours.delete(hour);
            this.classList.remove('btn-hour-selected');
            hiddenInput.disabled = true; // Отключаем поле, чтобы оно не отправлялось с формой
        } else {
            selectedHours.add(hour);
            this.classList.add('btn-hour-selected');
            hiddenInput.disabled = false; // Включаем поле для отправки с формой
        }
    });
});






