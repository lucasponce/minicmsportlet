<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<portlet:defineObjects/>

<c:set var="n"><portlet:namespace/></c:set>

<div class="portlet-font">
<link rel="stylesheet" type="text/css" href="<%=renderResponse.encodeURL(renderRequest.getContextPath() + "/jquery.cleditor.css") %>" />
<script type="text/javascript" src="<%=renderResponse.encodeURL(renderRequest.getContextPath() + "/jquery.min.js") %>"></script>
<script type="text/javascript" src="<%=renderResponse.encodeURL(renderRequest.getContextPath() + "/jquery.cleditor.min.js") %>"></script>
<link rel="stylesheet" type="text/css" href="<%=renderResponse.encodeURL(renderRequest.getContextPath() + "/minicmsportlet.css") %>" />
<script type="text/javascript">
  $(document).ready(function() {
    $("#${n}inputcontent").cleditor({width:"99%", height:"350px"});
  });
</script>
<portlet:actionURL var="saveContentURL"/>
<form action="<%= saveContentURL %>" method="POST">

    <div class="centerContent">
		<b>Content key:</b>
		<%= renderRequest.getAttribute("content_key") %>&nbsp;&nbsp;&nbsp;
		<b>Locale:</b>
	    <%= renderRequest.getLocale().getLanguage() %>  
    </div>
	<center>
    		<textarea id="${n}inputcontent" class="centerEditor" name="edit_view" cols="50" rows="50"><%= renderRequest.getAttribute("content_view") %></textarea>
	</center>
       
</div>
<div class="rightLink">
	<input type="Submit" value="Save"/>
	</form>
<portlet:renderURL var="editURL">
    <portlet:param name="editurl" value='viewadmin'/>
</portlet:renderURL>
<a href="<%= editURL %>" onclick="return confirm('Confirm cancel content edition');" >Cancel</a>&nbsp;&nbsp;
</div>