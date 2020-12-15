submitStudentFormRegistration = () => {
    let studentForm = document.forms.studentForm;
    console.log(studentForm.checkValidity());
    if (!studentForm.checkValidity()) {
        studentForm.classList.add('was-validated');
    } else {
        studentForm.classList.add('was-validated');
        for (let i in studentForm.elements) {
            let field = studentForm.elements[i];
            if (field.type === 'text' || field.type === 'select-one') {
                if (typeof student[field.id] === 'number') {
                    student[field.id] = Number(field.value);
                } else {
                    student[field.id] = field.value;
                }
            } else if (field.type === 'file') {
                obj['note'] = field.files[0];
            }
        }
        registrationStudent(student);
    }
}