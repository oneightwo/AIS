getEmployeeUpdateData = (id) => {
    return sendRequest('GET', '/api/employees/' + id)
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                //TODO изменить на новое модальное окно
                showResultModal("ERROR");
            }
        });
}

getEmployeeCreateData = () => {
    return sendRequest('GET', '/api/employees/modals')
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                //TODO изменить на новое модальное окно
                showResultModal("ERROR");
            }
        });
}

getDepartmentCreateData = () => {
    return sendRequest('GET', '/api/departments/modals')
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                //TODO изменить на новое модальное окно
                showResultModal("ERROR");
            }
        });
}

getDepartmentUpdateData = (id) => {
    return sendRequest('GET', '/api/departments/' + id)
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                //TODO изменить на новое модальное окно
                showResultModal("ERROR");
            }
        });
}

getDepartmentDeleteData = (id) => {
    return sendRequest('DELETE', '/api/departments/' + id)
        .then(response => {
            if (response.ok) {
                showResultModal("Удалено", "SUCCESS", () => document.location.reload(true));
                // return response.json();
            } else {
                //TODO изменить на новое модальное окно
                showResultModal("Произошла ошибка", "ERROR");
            }
        });
}

sendUpdatableEmployee = (employee) => {
    sendRequest('PUT', '/api/employees/' + employee.employee_id, employee)
        .then(response => {
            if (response.ok) {
                console.log("response ok: ", response);
                showResultModal("Сохранено", "SUCCESS", () => document.location.reload(true));
            } else {
                console.log("response no ok: ", response);
                showResultModal("Произошла ошибка", "ERROR");
            }
        });
}

sendDismissalEmployee = (employee) => {
    sendRequest('DELETE', '/api/employees/' + employee.employee_id, employee)
        .then(response => {
            if (response.ok) {
                console.log("response ok: ", response);
                showResultModal("Сохранено", "SUCCESS", () => document.location.reload(true));
            } else {
                console.log("response no ok: ", response);
                showResultModal("Произошла ошибка", "ERROR");
            }
        });
}

sendCreatedEmployee = (employee) => {
    sendRequest('POST', '/api/employees', employee)
        .then(response => {
            if (response.ok) {
                console.log("response ok: ", response);
                showResultModal("Сохранено", "SUCCESS", () => document.location.reload(true));
            } else {
                console.log("response no ok: ", response);
                showResultModal("Произошла ошибка", "ERROR");
            }
        });
}

sendCreatedDepartment = (department) => {
    console.log("to send: ", department);
    sendRequest('POST', '/api/departments', department)
        .then(response => {
            if (response.ok) {
                console.log("response ok: ", response);
                showResultModal("Сохранено", "SUCCESS", () => document.location.reload(true));
            } else {
                console.log("response no ok: ", response);
                showResultModal("Произошла ошибка", "ERROR");
            }
        });
}

sendUpdatableDepartment = (department) => {
    console.log("to send: ", department);
    sendRequest('PUT', '/api/departments/' + department.department_id, department)
        .then(response => {
            if (response.ok) {
                console.log("response ok: ", response);
                showResultModal("Сохранено", "SUCCESS", () => document.location.reload(true));
            } else {
                console.log("response no ok: ", response);
                showResultModal("Произошла ошибка", "ERROR");
            }
        });
}


sendRequest = (method, url, body) => {
    const headers = {
        'Content-Type': 'application/json'
    }
    console.log("body: ", body);
    if (body !== null) {
        return fetch(url, {
            method: method,
            body: JSON.stringify(body),
            headers: headers
        });
    } else {
        return fetch(url, {
            method: method,
            headers: headers
        });
    }
}