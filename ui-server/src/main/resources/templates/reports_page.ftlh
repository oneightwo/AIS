<#import "containers/common.ftlh" as c>
<#import "components/navbar.ftlh" as nav>
<@c.page>
    <@nav.navbar user=user role=role/>
    <div class="col-4" style='margin-top: 74px'>
        <div class="card">
            <h5 class="card-header">
                Операции
            </h5>
            <div class="card-body">
                <a class="btn btn-primary mb-3" href="/api/admin/departments/reports">
                    Получить отчет по департаментам
                </a>
                <a class="btn btn-primary" href="/api/admin/employees/reports">
                    Получить отчет по работникам
                </a>
            </div>
        </div>
    </div>
</@c.page>