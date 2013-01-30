package portlet;

import images.ImagesAPI;
import images.ImagesFactory;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.portlet.PortletFileUpload;
import org.apache.log4j.Logger;

public class ImagesCMS extends GenericPortlet {

	protected final static Logger _log = Logger.getLogger(ImagesCMS.class);

	@Override
	protected void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {

		_log.debug("VIEW");
		
		// Access Images repository
		
		ImagesAPI images = ImagesFactory.getImages();
		Set<String> list = images.getImages();
		
		request.setAttribute("listimages", list);

		// Accessing to Portlet session
		
		request.setAttribute("validation", request.getPortletSession().getAttribute("validation"));		
		
		// Controlling the view
		
		String view = "/jsp/upload.jsp";

		PortletRequestDispatcher prd = getPortletContext()
				.getRequestDispatcher(view);
		prd.include(request, response);

	}
	
	
	@Override
	public void processAction(ActionRequest request, ActionResponse response)
			throws PortletException, IOException {

		_log.debug("ACTION");	
		
		// Validation messages
		String validation = "";
		
		String deleteimage = request.getParameter("deleteimage");
		
		if (deleteimage != null) {
			ImagesAPI images = ImagesFactory.getImages();
			images.removeImage(deleteimage);						
			return;
		}
		
		// Save the images in memory	
		// 750k limit for images
				
		int maxsize = 750000;
		String s_maxsize = System.getProperty("minicms.image.maxsize");		
		if (s_maxsize != null) {
			try {
				maxsize = new Integer(s_maxsize);
			} catch (Exception e) { }
		}
		
		FileItemFactory factory = new DiskFileItemFactory(maxsize, null);		
		
		PortletFileUpload upload = new PortletFileUpload(factory);
		try {
			@SuppressWarnings("unchecked")
			List<FileItem> items = upload.parseRequest(request);		
			
			String key = null;
			FileItem image = null;
			
			for (FileItem f : items) {
				
				if (f.isFormField()) {
				    String name = f.getFieldName();
				    String value = f.getString();			
				    
				    key = value;
				    
				    _log.debug("name: " + name + " value: " + value);
				    
				} else {
				    String fieldName = f.getFieldName();
				    String fileName = f.getName();
				    String contentType = f.getContentType();
				    boolean isInMemory = f.isInMemory();
				    long sizeInBytes = f.getSize();
				    
				    _log.debug("fieldName: " + fieldName + " fileName: " + fileName + " contentType: " + contentType + " isInMemory: " + isInMemory + " sizeInBytes: " + sizeInBytes);
				   
				    image = f;				    
				}
				
			}
			
			// Validation if key has no name
			if (key.equalsIgnoreCase(""))
				key = image.getName();
						
			// Validation if exists
			boolean exists = false;
			ImagesAPI images = ImagesFactory.getImages();
			if (images.getImage(key) != null) {
				exists = true;
				validation = "Image key: " + key + " has already uploaded. Please, delete it prior re-upload it.";
			}
			
			// Access Images repository
						
			if (key != null &&
				image != null && 
				!exists) {
				images = ImagesFactory.getImages();
				images.setImage(key, image);
			}
			
			request.getPortletSession().setAttribute("validation", validation);
			
 		} catch (FileUploadException e) {
 			e.printStackTrace();
			_log.debug("Error uploading file: " + e.toString() );
		} catch (Exception e) {
			e.printStackTrace();
			_log.debug("Error uploading file: " + e.toString() );
		}
		
	}

}
