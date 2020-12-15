<#macro navbar user role>
    <nav id="navbar" class="navbar navbar-expand-lg navbar-light bg-light light shadow fixed-top">
        <a class="navbar-brand" href="/main">АИС</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="/employees">Сотрудники</a>
                </li>
                <#if role="admin">
                    <li class="nav-item">
                        <a class="nav-link" href="/admin/departments">Департаменты</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/admin/reports">Отчеты</a>
                    </li>
                </#if>
            </ul>
        </div>
        <form action="/logout">
            <#if role="admin">
                <#if springMacroRequestContext.requestUri?contains("/admin/departments")>
                    <button class="btn btn-outline-success my-2 my-sm-0 inline" type="button"
                            onclick="onClickCreateDepartment()">Создать
                    </button>
                </#if>
                <#if springMacroRequestContext.requestUri?contains("/employees")>
                    <button class="btn btn-outline-success my-2 my-sm-0" type="button"
                            onclick="onClickCreateEmployee()">
                        Создать
                    </button>
                </#if>
            </#if>
            <a target="_blank" rel="noopener noreferrer" href="http://localhost:8777/auth/realms/AIS/account/"
               class="btn btn-outline-primary my-2 my-sm-0 inline">Учетная запись</a>
            <button class="btn btn-outline-danger my-2 my-sm-0 inline" type="submit">Выйти</button>
        </form>
    </nav>
    <div style='margin-top: 58px'></div>
</#macro>