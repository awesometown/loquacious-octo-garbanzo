<#import "common/admin_master.ftl" as layout />

<@layout.adminTemplate title="My test page">
<h1 class="page-header">Create an Incident</h1>
<form id="new-incident-form">
    <ul>
        <li>Title:</li>
        <li><input class="text-input" type="text" name="title" id="incident-title"/></li>
        <li>Update Text:</li>
        <li><input class="text-area" type="text" name="description" id="incident-update-description"/></li>
        <li>State:</li>
        <li>
            <select class="dropdown" id="incident-state" name="state">
                <#list states as state>
				<option value="${state}">${state}</option>
				</#list>
            </select>
        </li>
        <li>Affected Services:</li>
        <li>
			<ul>
				<#list services as service>
					<li>${service.id}</li>
				</#list>
			</ul>
		</li>
        <li>New Service Status:</li>
        <li>
            <select class="dropdown" id="new-service-status" name="serviceStatusId">
                <#list statuses as status>
				<option value="${status.id}">${status.name}</option>
				</#list>
            </select>
        </li>
        <li><button value="Create" name="Create" id="submit" class="btn btn-success"/></li>
    </ul>
</form>

<script type="application/javascript">
    $(document).ready(function() {
        $("#submit").click(function() {
            var data = {};
            $("#new-incident-form").serializeArray().map(function(x){data[x.name] = x.value;});
            $.ajax({
                url: "http://localhost:8080/api/incidents",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify(data),
				error: function(jqXHR, text, errorThrown) {
					alert("hmm");
				},
				success: function(data) {
					alert("yay!");
				}
            });
        })
    });
</script>
</@layout.adminTemplate>