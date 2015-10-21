<#import "../common/admin_master.ftl" as layout />

<@layout.adminTemplate title="My test page">
<h1 class="page-header">Incident</h1>
<ul>
    <li>${incident.id}</li>
    <li>${incident.title}</li>
    <li>${incident.state}</li>
    <li>${incident.serviceStatusId}</li>
    <li>${incident.allServices?c}</li>
    <li>${incident.createdAt}</li>
    <li>${incident.updatedAt}</li>
    <!--incidentUpdates; -->
</ul>
</@layout.adminTemplate>
