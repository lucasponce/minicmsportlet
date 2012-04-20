<%@ taglib prefix="portlet" uri="http://java.sun.com/portlet" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<portlet:defineObjects/>
<link rel="stylesheet" type="text/css" href="<%=renderResponse.encodeURL(renderRequest.getContextPath() + "/minicmsportlet.css") %>" />

<c:set var="n"><portlet:namespace/></c:set>

<div>

	<div class="leftContent">
		<b>&nbsp;&nbsp;MiniCMS images</b>
	</div>
	
	<table class="centerTable">
			<tr>
				<th>Image key</th>
				<th></th>
				<th></th>
			</tr>
				
		<c:forEach var="image" items="${listimages}">
			<tr>
				<td class="tdImage"><b>${image}</b></td>
				<td class="tdImage">
				<a href='<portlet:actionURL>
					<portlet:param name="deleteimage" value='${image}'/>
				</portlet:actionURL>'
				onclick="return confirm('Confirm delete ${image} image');"
				>Delete</a></td>
				<td class="tdImage"><a href="/minicmsportlet/image?key=${image}" target="_blank">View&nbsp;&nbsp;</a></td>
			</tr>
		</c:forEach>
	</table>	

	<br />
		
	<c:if test='${validation != "" }'>
		<div class="validation">${validation}</div>
		
		<br />			
	</c:if>
				
	<form method="POST" 
		  action="<portlet:actionURL/>" 
		  enctype="multipart/form-data">
		
		<div class="centerContent2">
		<b>New Image key:</b>
		<input type="text" name="image_key" />
		<input type="file" name="uploadfile">
		</div>
		
		<br />
		
	<div class="rightLink">		
		<input class="portlet-form-button" type="submit" value="Upload"/>&nbsp;&nbsp;	
	</form>
	</div>
	
</div>