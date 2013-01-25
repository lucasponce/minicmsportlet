<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<portlet:defineObjects/>
<link rel="stylesheet" type="text/css" href="<%=renderResponse.encodeURL(renderRequest.getContextPath() + "/minicmsportlet.css") %>" />

<c:set var="n"><portlet:namespace/></c:set>

<div>

	<div class="leftContent">
		<b>&nbsp;&nbsp;MiniCMS content</b>
	</div>
	
	<table class="centerTable">
			<tr>
				<th>Content key / locale</th>
				<th></th>
				<th></th>
			</tr>
				
		<c:forEach var="content" items="${listcontent}">
			<tr>
				<td class="tdImage"><b>${content.key}&nbsp;&nbsp;${content.locale}</b></td>
				<td class="tdImage">
				<a href='<portlet:actionURL>
					<portlet:param name="deletecontent" value='${content.key}'/>
					<portlet:param name="deletelocale" value='${content.locale}'/>					
				</portlet:actionURL>'
				onclick="return confirm('Confirm delete ${content.key}_${content.locale} content');"
				>Delete</a></td>
				<td class="tdImage"><a href="/minicmsportlet/content?key=${content.key}_${content.locale}" target="_blank">View&nbsp;&nbsp;</a></td>
			</tr>
		</c:forEach>
	</table>	

	<br />
		
	<c:if test='${validation != "" }'>
		<div class="validation">${validation}</div>
		
		<br />			
	</c:if>				
	
</div>