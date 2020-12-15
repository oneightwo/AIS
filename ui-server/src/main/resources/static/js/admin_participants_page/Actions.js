submitAdminStudentForm = () => {
    let adminForm = document.forms.adminStudentForm;
    if (!adminForm.checkValidity()) {
        adminForm.classList.add('was-validated');
    } else {
        for (let i in adminForm.elements) {
            let field = adminForm.elements[i];
            if (field.type === 'text' || field.type === 'select-one' || field.type === 'textarea') {
                if (typeof student[field.id] === 'number') {
                    student[field.id] = Number(field.value);
                } else {
                    student[field.id] = field.value;
                }
            } else if (field.type === 'file') {
                obj['note'] = field.files[0];
            } else if (field.type === 'checkbox') {
                student[field.id] = field.checked;
            }
        }
        console.log("student", student);
        updateDataStudent(student);
    }
}

showAdminStudentModal = (id, isEditable) => {
    setIsEditable(isEditable);
    getStudentById(id).then(data => {
        console.log(data);
        setDataOnFullInformationModal(data);
    });
    $("#adminStudentModal").modal();
}

setIsEditable = (isEditable) => {
    let label;
    if (isEditable) {
        label = "Редактирование";
        document.getElementById("adminStudentModalDeleteButton").hidden = false;
    } else {
        label = "Просмотр";
        document.getElementById("adminStudentModalDeleteButton").hidden = true;
    }
    setDisableOrEnableEdit(isEditable);
    document.getElementById("adminStudentModalLabel").innerText = label;
}


setDataOnFullInformationModal = (data) => {
    const student = data.student;
    const universities = data.universities;
    const scienceDirections = data.scienceDirections;
    const courses = data.courses;

    let formElements = document.forms.adminStudentForm.elements;

    formElements.id.value = student.id;
    formElements.surname.value = student.surname;
    formElements.name.value = student.name;
    formElements.patronymic.value = student.patronymic;
    setOptionsOnSelect(formElements.university_id, universities);
    formElements.university_id.value = student.university_id;
    setOptionsOnSelectCourse(formElements.course_id, courses);
    formElements.course_id.value = student.course_id;
    formElements.faculty.value = student.faculty;
    formElements.email.value = student.email;
    formElements.phone.value = student.phone;
    setOptionsOnSelect(formElements.science_direction_id, scienceDirections);
    formElements.science_direction_id.value = student.science_direction_id;
    formElements.topic.value = student.topic;
    formElements.c1.value = student.c1;
    formElements.c2.value = student.c2;
    formElements.c3.value = student.c3;
    formElements.c4.value = student.c4;
    formElements.c5.value = student.c5;
    formElements.c6.value = student.c6;
    formElements.c7.value = student.c7;
    formElements.c8.value = student.c8;
    formElements.c9.value = student.c9;
    formElements.c10.value = student.c10;
    formElements.c11.value = student.c11;
    formElements.c12.value = student.c12;
    formElements.c13.value = student.c13;
    formElements.c14.value = student.c14;
    formElements.c15.value = student.c15;
    formElements.is_valid.checked = student.is_valid;
    formElements.data_registration.value = student.data_registration;
}

setDisableOrEnableEdit = (isEditable) => {
    let formElements = document.forms.adminStudentForm.elements;
    for (let i = 0; i < formElements.length; i++) {
        formElements[i].disabled = !isEditable;
    }
    formElements.is_valid.disabled = false;
}

hideAdminStudentModal = () => {
    $("#adminStudentModal").modal('toggle');
}

setOptionsOnSelectCourse = (select, options) => {
    options.forEach(function (element, key) {
        select[key] = new Option(element.value, element.id);
    });
}

setOptionsOnSelect = (select, options) => {
    options.forEach(function (element, key) {
        select[key] = new Option(element.name, element.id);
    });
}

deleteAdminStudentForm = () => {
    showDeleteStudentModal(document.forms.adminStudentForm.elements.id.value);
}

showDeleteStudentModal = (id) => {
    console.log("(id)", id);
    document.getElementById("deleteStudentModalButton").onclick = () => deleteStudent(id);
    $("#deleteStudentModal").modal();
}

hideDeleteStudentModal = () => {
    $("#deleteStudentModal").modal('toggle');
}

