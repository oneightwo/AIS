<#macro user label user role>
    <div class="card">
        <h5 class="card-header">${label}</h5>
        <div class="card-body">
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon1">Фамилия</span>
                </div>
                <input type="text" class="form-control" aria-label="Фамилия"
                       aria-describedby="basic-addon1" value="${(user.surname)!}" readonly>
            </div>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon1">Имя</span>
                </div>
                <input type="text" class="form-control" aria-label="Имя"
                       aria-describedby="basic-addon1" value="${(user.name)!}" readonly>
            </div>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon1">Отчество</span>
                </div>
                <input type="text" class="form-control" aria-label="Отчество"
                       aria-describedby="basic-addon1" value="${(user.patronymic)!}" readonly>
            </div>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon1">Пол</span>
                </div>
                <input type="text" class="form-control" aria-label="Пол"
                       aria-describedby="basic-addon1" value="${(user.gender)!}" readonly>
            </div>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon1">Дата рождения</span>
                </div>
                <input type="text" class="form-control" aria-label="Дата рождения"
                       aria-describedby="basic-addon1" value="${(user.dateOfBirth)!}" readonly>
            </div>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon1">Email</span>
                </div>
                <input type="text" class="form-control" aria-label="Email"
                       aria-describedby="basic-addon1" value="${(user.email)!}" readonly>
            </div>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon1">Должность</span>
                </div>
                <input type="text" class="form-control" aria-label="Должность"
                       aria-describedby="basic-addon1" value="${(user.positionName)!}" readonly>
            </div>
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon1">Начальник</span>
                </div>
                <input type="text" class="form-control" aria-label="Начальник"
                       aria-describedby="basic-addon1" value="${(user.isLeader)!}" readonly>
            </div>
            <#if role="admin">
                <div class="input-group mt-3 mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="basic-addon1">Номер телефона</span>
                    </div>
                    <input type="text" class="form-control" aria-label="Номер телефона"
                           aria-describedby="basic-addon1" value="${(user.phone)!}" readonly>
                </div>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="basic-addon1">Дата трудоустройства</span>
                    </div>
                    <input type="text" class="form-control" aria-label="Дата трудоустройства"
                           aria-describedby="basic-addon1" value="${(user.employmentDate)!}" readonly>
                </div>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="basic-addon1">Заработная плата</span>
                    </div>
                    <input type="text" class="form-control" aria-label="Заработная плата"
                           aria-describedby="basic-addon1" value="${(user.salary)!}" readonly>
                </div>
                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="basic-addon1">Департамент</span>
                    </div>
                    <input type="text" class="form-control" aria-label="Департамент"
                           aria-describedby="basic-addon1" value="${(user.departmentName)!}" readonly>
                </div>
            </#if>
        </div>
    </div>
</#macro>

<#macro department department>
    <div class="card">
        <h5 class="card-header">Информация о департаменте</h5>
        <div class="card-body">
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon1">Название</span>
                </div>
                <input type="text" class="form-control" aria-label="Название"
                       aria-describedby="basic-addon1" value="${department.name}" readonly>
            </div>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon1">Дата создания</span>
                </div>
                <input type="text" class="form-control" aria-label="Дата создания"
                       aria-describedby="basic-addon1" value="${department.creationDate}" readonly>
            </div>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon1">Количество сотрудников</span>
                </div>
                <input type="text" class="form-control" aria-label="Количество сотрудников"
                       aria-describedby="basic-addon1" value="${department.numberEmployees}" readonly>
            </div>
        </div>
    </div>
</#macro>