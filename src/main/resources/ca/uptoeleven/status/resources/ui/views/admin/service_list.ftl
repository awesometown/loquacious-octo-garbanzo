<#import "../common/admin_master.ftl" as layout />

<@layout.adminTemplate title="Services">
<h1 class="page-header">Services</h1>
<ul>
	<#list services as service>
		<li>${service.name} - ${service.description}</li>
	</#list>
</ul>
<a href="/admin/services/new">Create new service</a>

</@layout.adminTemplate>