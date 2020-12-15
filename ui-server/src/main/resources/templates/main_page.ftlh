<#import "containers/common.ftlh" as c>
<#import "components/navbar.ftlh" as nav>
<#import "components/forms.ftlh" as forms>
<@c.page>
    <@nav.navbar user=user role=role/>
    <div class="container-fluid">
        <div class="d-flex justify-content-center">
            <div class='col-6 mt-3'>
                <@forms.user label="Информация"user=user role="admin"/>
            </div>
        </div>
    </div>

</@c.page>