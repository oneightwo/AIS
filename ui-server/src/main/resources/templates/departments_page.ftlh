<#import "containers/common.ftlh" as c>
<#import "components/navbar.ftlh" as nav>
<#import "components/forms.ftlh" as forms>
<#import "components/modals.ftlh" as modals>

<@c.page>
    <@nav.navbar user=user role=role/>
    <@modals.modalDepartment/>
    <@modals.infoModal/>

    <#function getStr department>
        <#return department.name>
    </#function>

    <div class="card mx-2" style='margin-top: 74px'>
        <h5 class="card-header">
            Текущий департамент
        </h5>
    </div>
    <div class="accordion mx-2 mb-2" id="accordionExample">
        <div class="card">
            <div class="card-header" id="headingOne">
                <h2 class="mb-0">
                    <button class="btn btn-link btn-block text-left collapsed" type="button" data-toggle="collapse"
                            data-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                        ${currentDepartment.name}
                    </button>
                </h2>
            </div>
            <div id="collapseOne" class="collapse" aria-labelledby="headingOne" data-parent="#accordionExample">
                <div class="card-body">
                    <div class="d-flex">
                        <div class="col-6">
                            <@forms.department department=currentDepartment/>
                            <div class="card mt-3">
                                <h5 class="card-header">
                                    Операции
                                </h5>
                                <div class="card-body">
                                    <button class="btn btn-primary" onclick="onClickEditDepartment(${currentDepartment.id})">Изменить</button>
                                    <button class="btn btn-danger" onclick="onClickDeleteDepartment(${currentDepartment.id})">Удалить</button>
                                </div>
                            </div>
                        </div>
                        <div class="col-6">
                            <@forms.user label="Руководитель" user=currentDepartment.leader role=role/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="card mx-2 mt-4">
        <h5 class="card-header">
            Подчиненные департаменты
        </h5>
    </div>
    <div class="accordion mx-2" id="accordionExampleChildren">
        <div class="card">
            <#list childrenDepartments as department>
                <div class="card-header" id="${department.name}_h">
                    <h2 class="mb-0">
                        <button class="btn btn-link btn-block text-left collapsed" type="button" data-toggle="collapse"
                                data-target="#${department.name}_collapse" aria-expanded="true"
                                aria-controls="${department.name}_collapse">
                            ${department.name}
                        </button>
                    </h2>
                </div>
                <div id="${department.name}_collapse" class="collapse" aria-labelledby="${department.name}_h"
                     data-parent="#accordionExampleChildren">
                    <div class="card-body">
                        <div class="d-flex">
                            <div class="col-6">
                                <@forms.department department=department/>
                                <div class="card mt-3">
                                    <h5 class="card-header">
                                        Операции
                                    </h5>
                                    <div class="card-body">
                                        <button class="btn btn-primary" onclick="onClickEditDepartment(${department.id})">Изменить</button>
                                        <button class="btn btn-danger" onclick="onClickDeleteDepartment(${department.id})">Удалить</button>
                                    </div>
                                </div>
                            </div>
                            <div class="col-6">
                                <@forms.user label="Руководитель" user=department.leader role=role/>
                            </div>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </div>
</@c.page>