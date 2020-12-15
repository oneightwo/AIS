<#macro modalEmployee>
    <div id="modalEmployee" class="modal fade" tabindex="-1" data-keyboard="false" data-backdrop="static">
        <div class="modal-dialog modal-lg modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 id="modalEmployeeLabel" class="modal-title"></h5>
                </div>
                <div class="modal-body">
                    <form id="modalEmployeeForm" name="modalEmployeeForm" class="needs-validation mb-0"
                          novalidate>
                        <input id="employee_id" type="text" class="form-control" aria-label="id" hidden>
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="basic-addon1">Фамилия</span>
                            </div>
                            <input id="surname" type="text" class="form-control" aria-label="Фамилия"
                                   aria-describedby="basic-addon1" readonly>
                        </div>
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="basic-addon1">Имя</span>
                            </div>
                            <input id="name" type="text" class="form-control" aria-label="Имя"
                                   aria-describedby="basic-addon1" readonly>
                        </div>
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="basic-addon1">Отчество</span>
                            </div>
                            <input id="patronymic" type="text" class="form-control" aria-label="Отчество"
                                   aria-describedby="basic-addon1" readonly>
                        </div>
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="basic-addon1">Пол</span>
                            </div>
                            <select id="gender" class="custom-select" aria-label="Пол" required disabled></select>
                        </div>
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="basic-addon1">Дата рождения</span>
                            </div>
                            <input id="date_of_birth" type="text" class="form-control" aria-label="Дата рождения"
                                   aria-describedby="basic-addon1" readonly>
                        </div>
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="basic-addon1">Номер телефона</span>
                            </div>
                            <input id="phone" type="text" class="form-control" aria-label="Номер телефона"
                                   aria-describedby="basic-addon1" readonly>
                        </div>
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="basic-addon1">Email</span>
                            </div>
                            <input id="email" type="text" class="form-control" aria-label="Email"
                                   aria-describedby="basic-addon1" readonly>
                        </div>
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="basic-addon1">Дата трудоустройства</span>
                            </div>
                            <input id="employment_date" type="text" class="form-control"
                                   aria-label="Дата трудоустройства"
                                   aria-describedby="basic-addon1" readonly>
                        </div>
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="basic-addon1">Заработная плата</span>
                            </div>
                            <input id="salary" type="text" class="form-control" aria-label="Заработная плата"
                                   aria-describedby="basic-addon1" readonly>
                        </div>
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="basic-addon1">Департамент</span>
                            </div>
                            <select id="department_id" class="custom-select" aria-label="Департамент" required
                                    disabled></select>
                        </div>
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="basic-addon1">Должность</span>
                            </div>
                            <select id="position_id" class="custom-select" aria-label="Должность" required
                                    disabled></select>
                        </div>
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="basic-addon1">Начальник</span>
                            </div>
                            <select id="is_leader" class="custom-select" aria-label="Должность" required
                                    disabled></select>
                        </div>
                        <div id="date_of_dismissal_div" class="input-group mt-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="basic-addon1">Дата увольнения</span>
                            </div>
                            <input id="date_of_dismissal" type="text" class="form-control" aria-label="Дата увольнения"
                                   aria-describedby="basic-addon1">
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button id="saveButtonEmployeeModal" type="button" class="btn btn-success">
                        Сохранить
                    </button>
                    <button id="closeButtonEmployeeModal" type="button" class="btn btn-primary" data-dismiss="modal">
                        Закрыть
                    </button>
                </div>
            </div>
        </div>
    </div>
</#macro>

<#macro modalDepartment>
    <div id="modalDepartment" class="modal fade" tabindex="-1" data-keyboard="false" data-backdrop="static">
        <div class="modal-dialog modal-lg modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 id="modalDepartmentLabel" class="modal-title"></h5>
                </div>
                <div class="modal-body">
                    <form id="modalDepartmentForm" name="modalDepartmentForm" class="needs-validation mb-0"
                          novalidate>
                        <input id="department_id" type="text" class="form-control" aria-label="id" hidden>
                        <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="basic-addon1">Название</span>
                            </div>
                            <input id="name" type="text" class="form-control" aria-label="Название"
                                   aria-describedby="basic-addon1">
                        </div>
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <span class="input-group-text" id="basic-addon1">Родительский департамент</span>
                            </div>
                            <select id="parent_id" class="custom-select" aria-label="Родительский департамент" required></select>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button id="saveButtonDepartmentModal" type="button" class="btn btn-success">
                        Сохранить
                    </button>
                    <button id="closeButtonDepartmentModal" type="button" class="btn btn-primary" data-dismiss="modal">
                        Закрыть
                    </button>
                </div>
            </div>
        </div>
    </div>
</#macro>

<#macro infoModal>
    <div id="infoModal" class="modal fade" tabindex="-1" data-keyboard="false" data-backdrop="static">
        <div class="modal-dialog modal modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 id="resultChangeModalText" class="modal-title">Modal title</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    </button>
                </div>
                <div class="modal-footer">
                    <button id="resultChangeModalButton" type="button" class="btn btn-success"
                            data-dismiss="modal">Хорошо
                    </button>
                </div>
            </div>
        </div>
    </div>
</#macro>