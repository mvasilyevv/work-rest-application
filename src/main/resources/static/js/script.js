function loadContent(page) {
    const contentContainer = document.getElementById('contentContainer');

    if (page === 'crewMembers') {
        // fetch('/fragments/crewMembers')
        //     .then(response => response.text())
        //     .then(data => {
        //         contentContainer.innerHTML = data;
        //     });
    } else if (page === 'workHours') {
        fetch('/fragments/workHours')
            .then(response => response.text())
            .then(data => {
                contentContainer.innerHTML = data;
            });
    }
}





