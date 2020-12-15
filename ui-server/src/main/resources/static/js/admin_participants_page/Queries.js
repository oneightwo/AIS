getStudentById = (id) => {
    return sendRequest('GET', '/admins/students/' + id)
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                //TODO изменить на новое модальное окно
                showResultModal("ERROR");
            }
        });
}

updateDataStudent = (student) => {
    sendRequest('PUT', '/admins/students/' + student.id, student).then(response => {
        if (response.ok) {
            hideAdminStudentModal();
            showResultModal("Изменения были сохранены", "SUCCESS", () => document.location.reload(true));
        } else {
            showResultModal("Произошла ошибка", "ERROR");
        }
    });
}

deleteStudent = (id) => {
    sendRequest('DELETE', '/admins/students/' + id).then(response => {
        if (response.ok) {
            hideDeleteStudentModal();
            showResultModal("Студент был удален", "SUCCESS", () => document.location.reload(true));
        } else {
            hideDeleteStudentModal();
            showResultModal("Произошла ошибка", "ERROR");
        }
    });
}