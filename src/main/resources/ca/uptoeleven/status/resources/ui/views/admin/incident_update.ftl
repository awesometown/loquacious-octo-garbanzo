<#import "../common/admin_master.ftl" as layout />

<@layout.adminTemplate title="My test page">
<script type="application/javascript">
	var incident = ${getIncidentJson()}
</script>

<h1 class="page-header">${incident.title}</h1>

<form id="update-incident-form" role="form">
	<p>State: ${incident.state}</p>

	<p>Service Status: ${incident.serviceStatusId}</p>

	<p>Affected Services:</p>
	<ul>
		<#list incident.affectedServiceIds as serviceId>
			<li>${nameForServiceId(serviceId)}</li>
		</#list>
	</ul>
	<p>Post an Update</p>

	<div class="form-group">
		<label for="description">Update text</label>
		<textarea class="form-control" name="description" id="description"></textarea>
	</div>
	<div class="form-group">
		<label for="state">State</label>
		<select class="form-control" id="state" name="state">
			<#list states as state>
				<option value="${state}" id="option_${state}">${state}</option>
			</#list>
		</select>
	</div>
	<div class="form-group">
		<label for="status">New Services Status</label>
		<select class="form-control" id="status" name="serviceStatusId">
			<#list statuses as status>
				<option value="${status.id}">${status.name}</option>
			</#list>
		</select>
	</div>
	<button value="Update" name="Create" id="submit" class="btn btn-defaulkt">Update</button>
</form>
<ul>
	<#list incident.incidentUpdates as update>
		<li>${update.description}</li>
	</#list>
	<ul>

		<script type="application/javascript">
			$(document).ready(function () {
				$("#state").val(incident.state);

				$("#submit").click(function () {
					var data = {};
					$("#update-incident-form").serializeArray().map(function (x) {
						data[x.name] = x.value;
					});
					$.ajax({
						url: "http://localhost:8080/api/incidents/${incident.id}",
						type: "POST",
						contentType: "application/json",
						data: JSON.stringify(data),
						error: function (jqXHR, text, errorThrown) {
							alert("hmm");
						},
						success: function (data) {
							location.reload();
						}
					});
				})
			});
		</script>
</@layout.adminTemplate>