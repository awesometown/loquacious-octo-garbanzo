<#import "../common/dashboard_master.ftl" as layout />
<@layout.dashboardTemplate title="Dashboard">
<div class="container theme-showcase" role="main">

	<!-- Main jumbotron for a primary marketing message or call to action -->
	<div class="jumbotron">
		<h1>EADP Social Service Status</h1>

		<p>How's it going?</p>
	</div>

	<div class="currentIssues">

		<#list incidents as incident>
			<a href="/dashboard/incidents/${incident.id}">
				<div class="alert incident-${incident.serviceStatusId}">
					<p class="issue-state">${incident.state}</p>

					<h2>${incident.title}</h2>

					<p class="issue-updated">last updated ${prettyDate(incident.updatedAt)}</p>
				</div>
			</a>
		</#list>
	</div>

	<div class="services">
		<ul class="list-group">
			<#list services as service>
				<li class="list-group-item">
					<p class="service-status">${service.serviceStatus.name}</p>

					<p class="service-name">${service.name}</p>
				</li>
			</#list>
		</ul>
	</div>
</div>
</@layout.dashboardTemplate>