<#import "containers/common.ftlh" as c>
<#import "components/navbar.ftlh" as nav>
<#import "components/forms.ftlh" as forms>
<#import "components/modals.ftlh" as modals>

<@c.page>
    <@nav.navbar user=user role=role/>
    <@modals.modalEmployee/>
    <@modals.infoModal/>

    <#if employees?size != 0>
        <div class="accordion mx-2" id="accordionEmployee">
            <div class="card">
                <#list employees as employee>
                    <div class="card-header" id="${employee.name}${employee.id}_h">
                        <h2 class="mb-0">
                            <button class="btn btn-link btn-block text-left collapsed" type="button"
                                    data-toggle="collapse"
                                    data-target="#${employee.name}${employee.id}_collapse" aria-expanded="true"
                                    aria-controls="${employee.name}${employee.id}_collapse">
                                ${employee.surname} ${employee.name} ${employee.patronymic}
                            </button>
                        </h2>
                    </div>
                    <div id="${employee.name}${employee.id}_collapse" class="collapse"
                         aria-labelledby="${employee.name}${employee.id}_h"
                         data-parent="#accordionEmployee">
                        <div class="card-body">
                            <div class="d-flex">
                                <div class="col-8">
                                    <@forms.user label="Информация" user=employee role=role/>
                                </div>
                                <#if role="admin">
                                    <div class="col-4">
                                        <div class="card">
                                            <h5 class="card-header">
                                                Операции
                                            </h5>
                                            <div class="card-body">
                                                <button class="btn btn-primary"
                                                        onclick="onClickEditEmployee(${employee.id})">
                                                    Изменить
                                                </button>
                                                <button class="btn btn-danger"
                                                        onclick="onClickDismissalEmployee(${employee.id})">Уволить
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </#if>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </#if>
</@c.page>