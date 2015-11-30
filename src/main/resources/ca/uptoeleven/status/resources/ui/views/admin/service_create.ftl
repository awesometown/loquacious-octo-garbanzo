<#import "../common/admin_master.ftl" as layout />

<@layout.adminTemplate title="Create new service">
<h1 class="page-header">Create a new Service</h1>
<form id="new-service-form" role="form">
	<div class="form-group">
		<label for="name">Name</label>
		<input type="text" class="form-control" name="name" id="name"/>
	</div>
	<div class="form-group">
		<label for="description">Description</label>
		<textarea class="form-control" name="description" id="description"></textarea>
	</div>
	<button type="submit" value="Create" name="Create" id="submit" class="btn btn-default">Create</button>
</form>

<script type="application/javascript">
	$(document).ready(function () {
		$("#submit").click(function () {
			var data = {};
			$("#new-service-form").serializeArray().map(function (x) {
				data[x.name] = x.value;
			});
			$.ajax({
				url: "http://localhost:9000/api/services",
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