<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet" %>
<portlet:defineObjects/>
<%= renderRequest.getAttribute("content_view") %>
<br />
<link rel="stylesheet" type="text/css" href="<%=renderResponse.encodeURL(renderRequest.getContextPath() + "/minicmsportlet.css") %>" />
<div class="rightLink">
<portlet:renderURL var="editURL">
    <portlet:param name="editurl" value='edit'/>
</portlet:renderURL>
<a href="<%= editURL %>">Edit</a>&nbsp;&nbsp;
</div>