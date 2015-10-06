<#import "common/admin_master.ftl" as layout />

<@layout.adminTemplate title="My test page">
<h1 class="page-header">Create an Incident</h1>
<form id="new_incident" method="post">
    <ul>
        <li>Title:</li>
        <li><input class="text-input" type="text" id="incident-title"/></li>
        <li>Update Text:</li>
        <li><input class="text-area" type="text" id="incident-update-description"/></li>
        <li>State:</li>
        <li>
            <select class="dropdown" id="incident-state">
                <option>Investigating</option>
                <option>Identified</option>
                <option>Monitoring</option>
                <option>Resolved</option>
            </select>
        </li>
        <li>Affected Services:</li>
        <li>TODO</li>
        <li>New Service Status:</li>
        <li>
            <select class="dropdown" id="new-service-status">
                <option>Degraded Performance</option>
                <option>Partial Outage</option>
                <option>Major Outage</option>
            </select>
        </li>
        <li><input class="button" type="submit"/></li>
    </ul>
</form>
</@layout.adminTemplate>