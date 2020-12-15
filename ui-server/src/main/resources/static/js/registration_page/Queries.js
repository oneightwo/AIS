registrationStudent = (student) => {
    sendRequest('POST', '/registration', student).then(response => {
        if (response.ok) {
            showResultModal("Вы зарегистрированы", "SUCCESS", () => document.location.reload(true));
        } else {
            showResultModal("Произошла ошибка", "ERROR");
        }
    });
}