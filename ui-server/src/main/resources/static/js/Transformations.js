setDataOnEmployeeFrom = (data) => {
    const employee = data.employee;
    const departments = data.departments;
    const positions = data.positions;

    let formElements = document.forms.modalEmployeeForm.elements;

    if (employee !== undefined) {
        formElements.employee_id.value = employee.employee_id;
        formElements.surname.value = employee.surname;
        formElements.name.value = employee.name;
        formElements.patronymic.value = employee.patronymic;
        setOptionsOnSelect(formElements.department_id, departments, "department_id");
        formElements.department_id.value = employee.department_id;
        formElements.date_of_birth.value = employee.date_of_birth;
        formElements.phone.value = employee.phone;
        formElements.email.value = employee.email;
        formElements.employment_date.value = employee.employment_date;
        formElements.salary.value = employee.salary;
        setOptionsOnSelect(formElements.position_id, positions, "position_id");
        formElements.position_id.value = employee.position_id;
        setOptionsOnSelect(formElements.gender, [{"gender": "MALE", "name": "Мужчина"},
            {"gender": "FEMALE", "name": "Женщина"}], "gender");
        formElements.gender.value = employee.gender;
        setOptionsOnSelect(formElements.is_leader, [{"is_leader": true, "name": "Да"},
            {"is_leader": false, "name": "Нет"}], "is_leader");
        formElements.is_leader.value = employee.is_leader;
        formElements.date_of_dismissal.value = employee.date_of_dismissal;
    } else {
        setOptionsOnSelect(formElements.department_id, departments, "department_id");
        setOptionsOnSelect(formElements.position_id, positions, "position_id");
        setOptionsOnSelect(formElements.gender, [{"gender": "MALE", "name": "Мужчина"},
            {"gender": "FEMALE", "name": "Женщина"}], "gender");
        setOptionsOnSelect(formElements.is_leader, [{"is_leader": true, "name": "Да"},
            {"is_leader": false, "name": "Нет"}], "is_leader");
    }
}

setDataOnDepartmentFrom = (data) => {
    const department = data.department;
    const departments = data.departments;
    departments.push({department_id: null, name: ""});
    console.log("setDataOnDepartmentFrom:", departments);
    let formElements = document.forms.modalDepartmentForm.elements;

    if (department !== undefined) {
        formElements.department_id.value = department.department_id;
        formElements.name.value = department.name;
        setOptionsOnSelect(formElements.parent_id, departments, "department_id");
        formElements.parent_id.value = department.parent_id;
    } else {
        formElements.department_id.value = null;
        formElements.name.value = "";
        setOptionsOnSelect(formElements.parent_id, departments, "department_id");
    }
}

setOptionsOnSelect = (select, options, field) => {
    options.forEach(function (element, key) {
        select[key] = new Option(element.name, element[field]);
    });
}

formToJson = (formName, object, send) => {
    let form = document.forms[formName];
    if (!form.checkValidity()) {
        form.classList.add('was-validated');
    } else {
        for (let i in form.elements) {
            let field = form.elements[i];
            if (field.type === 'text' || field.type === 'select-one' || field.type === 'textarea') {
                if (typeof object[field.id] === 'number') {
                    object[field.id] = Number(field.value);
                } else {
                    object[field.id] = field.value;
                }
            } else if (field.type === 'file') {
                obj['note'] = field.files[0];
            } else if (field.type === 'checkbox') {
                object[field.id] = field.checked;
            }
        }
        send(object);
    }
}

configurationEmployeeFormFields = (editable) => {
    editable = !editable;
    let formElements = document.forms.modalEmployeeForm.elements;

    formElements.employee_id.readOnly = editable;
    formElements.surname.readOnly = editable;
    formElements.name.readOnly = editable;
    formElements.patronymic.readOnly = editable;
    formElements.department_id.disabled = editable;
    formElements.date_of_birth.readOnly = editable;
    formElements.phone.readOnly = editable;
    formElements.email.readOnly = editable;
    formElements.employment_date.readOnly = editable;
    formElements.salary.readOnly = editable;
    formElements.position_id.disabled = editable;
    formElements.gender.disabled = editable;
    formElements.is_leader.disabled = editable;
}
