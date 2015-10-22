<#import "../common/admin_master.ftl" as layout />

<@layout.adminTemplate title="My test page">
<h1 class="page-header">${incident.title}</h1>
<form id="update-incident-form">
    <ul>
        <li>State: ${incident.state}</li>
        <li>Service Status: ${incident.serviceStatusId}</li>
        <li>Affected Services: TODO</li>
        <li>Post an Update</li>
        <li><input type="text"></input></li>
        <li>New State:</li>
        <li>
            <select class="dropdown" id="incident-state" name="state">
                <#list states as state>
				<option value="${state}">${state}</option>
				</#list>
            </select>
        </li>
        <li>New Service Status:</li>
        <li>
            <select class="dropdown" id="new-service-status" name="serviceStatusId">
                <#list statuses as status>
				<option value="${status.id}">${status.name}</option>
				</#list>
            </select>
        </li>
        <li><button value="Update" name="Create" id="submit" class="btn btn-success"/></li>
    </ul>
</form>
<ul>
<#list incident.incidentUpdates as update>
	<li>${update.description}</li>
</#list>
<ul>

<script type="application/javascript">
//    $(document).ready(function() {
//        $("#submit").click(function() {
//            var data = {};
//            $("#new-incident-form").serializeArray().map(function(x){data[x.name] = x.value;});
//            $.ajax({
//                url: "http://localhost:8080/api/incidents",
//                type: "POST",
//                contentType: "application/json",
//                data: JSON.stringify(data),
//				error: function(jqXHR, text, errorThrown) {
//					alert("hmm");
//				},
//				success: function(data) {
//					alert("yay!");
//				}
//            });
//        })
//    });
</script>
</@layout.adminTemplate>