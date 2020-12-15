onClickEditEmployee = (id) => {
    $("#modalEmployee").modal();
    document.getElementById("saveButtonEmployeeModal").onclick = onSubmitEditEmployee;
    document.getElementById("date_of_dismissal_div").hidden = true;
    document.getElementById("modalEmployeeLabel").innerText = "Изменить";
    configurationEmployeeFormFields(true);
    getEmployeeUpdateData(id).then(data => {
        console.log(data);
        setDataOnEmployeeFrom(data);
    });
}

onClickDismissalEmployee = (id) => {
    $("#modalEmployee").modal();
    document.getElementById("saveButtonEmployeeModal").onclick = onSubmitDismissalEmployee;
    document.getElementById("date_of_dismissal_div").hidden = false;
    document.getElementById("modalEmployeeLabel").innerText = "Уволить";
    configurationEmployeeFormFields(false);
    getEmployeeUpdateData(id).then(data => {
        console.log(data);
        setDataOnEmployeeFrom(data);
    });
}

onClickCreateEmployee = () => {
    $("#modalEmployee").modal();
    document.getElementById("saveButtonEmployeeModal").onclick = onSubmitCreatedEmployee;
    document.getElementById("date_of_dismissal_div").hidden = true;
    document.getElementById("modalEmployeeLabel").innerText = "Создать";
    configurationEmployeeFormFields(true);
    getEmployeeCreateData().then(data => {
        console.log(data);
        setDataOnEmployeeFrom(data);
    });
}

onClickCreateDepartment = () => {
    $("#modalDepartment").modal();
    document.getElementById("saveButtonDepartmentModal").onclick = onSubmitCreatedDepartment;
    document.getElementById("modalDepartmentLabel").innerText = "Создать";
    // configurationEmployeeFormFields(true);
    getDepartmentCreateData().then(data => {
        console.log(data);
        setDataOnDepartmentFrom(data);
    });
}

onClickEditDepartment = (id) => {
    $("#modalDepartment").modal();
    document.getElementById("saveButtonDepartmentModal").onclick = onSubmitEditDepartment;
    document.getElementById("modalDepartmentLabel").innerText = "Создать";
    // configurationDepartmentFormFields(true);
    getDepartmentUpdateData(id).then(data => {
        console.log("data: ", data);
        setDataOnDepartmentFrom(data);
    });
}

onClickDeleteDepartment = (id) => {
    getDepartmentDeleteData(id)
        .then(data => {

        });
    // $("#modalDepartment").modal();
    // document.getElementById("saveButtonDepartmentModal").onclick = onSubmitEditDepartment;
    // document.getElementById("modalDepartmentLabel").innerText = "Создать";
    // // configurationDepartmentFormFields(true);
    // getDepartmentUpdateData(id).then(data => {
    //     console.log("data: ", data);
    //     setDataOnDepartmentFrom(data);
    // });
}

onSubmitCreatedEmployee = () => {
    formToJson("modalEmployeeForm", employee, sendCreatedEmployee);
}

onSubmitEditEmployee = () => {
    formToJson("modalEmployeeForm", employee, sendUpdatableEmployee);
}

onSubmitDismissalEmployee = () => {
    formToJson("modalEmployeeForm", employee, sendDismissalEmployee);
}

onSubmitCreatedDepartment = () => {
    formToJson("modalDepartmentForm", department, sendCreatedDepartment);
}

onSubmitEditDepartment = () => {
    formToJson("modalDepartmentForm", department, sendUpdatableDepartment);
}


showResultModal = (text, type, onclick = null) => {
    console.log(text, type);
    let className = "btn btn-primary";
    if (type === "ERROR") {
        className = "btn btn-danger";
    } else if (type === "SUCCESS") {
        className = "btn btn-success";
    }
    let button = document.getElementById('resultChangeModalButton');
    button.onclick = onclick;
    button.className = className;
    document.getElementById('resultChangeModalText').innerHTML = text;
    $("#infoModal").modal();
}
