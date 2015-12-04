<#import "../common/admin_master.ftl" as layout />

<@layout.adminTemplate title="Admin Dashboard">
<h1 class="page-header">Status Dashboard</h1>

<ul>
	<#list services as service>
		<li>${service.name} - ${service.serviceStatus.name}</li>
	</#list>
	<a href="admin/incidents/new">Create New Incident</a>
</ul>

</@layout.adminTemplate>