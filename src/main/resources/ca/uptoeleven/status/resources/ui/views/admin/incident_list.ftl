<#import "../common/admin_master.ftl" as layout />

<@layout.adminTemplate title="My test page">
<h1 class="page-header">Incidents</h1>
<ul>
    <#list incidents as incident>
        <li>
        <ul>
            <li><a href="/admin/incidents/${incident.id}">Title: ${incident.title}</a></li>
            <li>State: ${incident.state}</li>
            <li>Service Status: ${incident.serviceStatusId}</li>
            <li>Affected Services: TODO</li>
            <li>Last Message: TODO</li>
            <li>Last Updated: ${incident.updatedAt}</li>
        </ul>
        </li>
    </#list>
</ul>

</@layout.adminTemplate>