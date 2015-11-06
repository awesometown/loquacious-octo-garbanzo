<#import "../common/admin_master.ftl" as layout />

<@layout.adminTemplate title="My test page">
<h1 class="page-header">Incidents</h1>
<div class="list-group container">
	<#list incidents as incident>
        <div class="row">
            <a href="/admin/incidents/${incident.id}">
                <div class="col-md-offset-1 col-md-4">
                    <h3 class="list-group-item-heading">${incident.title}</h3>
                    <h5>${incident.serviceStatusId}</h5>

                    <p><strong>${incident.state}</strong> - Last Message Goes Here</p>
                </div>
                <div class="col-md-4">
                    <p>${incident.updatedAt}</p>

                    <p>Affected Services:</p>
                <ul>
                        <li>todo</li>
                    </ul>
                </div>
            </a>
        </div>
	</#list>
</div>

</@layout.adminTemplate>