<#import "../common/admin_master.ftl" as layout />

<@layout.adminTemplate title="My test page">
<h1 class="page-header">Create an Incident</h1>
<form id="new-incident-form" role="form">
	<div class="form-group">
		<label for="title">Title</label>
		<input type="text" class="form-control" name="title" id="title"/>
	</div>
	<div class="form-group">
		<label for="description">Text for initial update</label>
		<textarea class="form-control" name="description" id="description"></textarea>
	</div>
	<div class="form-group">
		<label for="state">State</label>
		<select class="form-control" id="state" name="state">
			<#list states as state>
				<option value="${state}">${state}</option>
			</#list>
		</select>
	</div>
	<div class="form-group">
		<label for="services">Affected Services</label>

		<div class="panel panel-default service-checkbox-list">
			<#list services as service>
				<div class="checkbox-inline">
					<label><input type="checkbox" class="service-checkbox" id="service_${service.id}"
								  value="${service.id}">${service.name}</label>
				</div>
			</#list>
		</div>
	</div>
	<div class="form-group">
		<label for="status">New Services Status</label>
		<select class="form-control" id="status" name="serviceStatusId">
			<#list statuses as status>
				<option value="${status.id}">${status.name}</option>
			</#list>
		</select>
	</div>
	<button type="submit" value="Create" name="Create" id="submit" class="btn btn-default">Create</button>
</form>

<script type="application/javascript">
	$(document).ready(function () {
		$("#submit").click(function () {
			var serviceIds = $("input.service-checkbox:checked").map(function () {
				return this.value
			}).get();
			var data = {};
			$("#new-incident-form").serializeArray().map(function (x) {
				data[x.name] = x.value;
			});
			data["affectedServiceIds"] = serviceIds;
			$.ajax({
				url: "http://localhost:8080/api/incidents",
				type: "POST",
				contentType: "application/json",
				data: JSON.stringify(data),
				error: function (jqXHR, text, errorThrown) {
					alert("hmm");
				},
				success: function (data) {
					alert("yay!");
				}
			});
		})
	});
</script>
</@layout.adminTemplate>