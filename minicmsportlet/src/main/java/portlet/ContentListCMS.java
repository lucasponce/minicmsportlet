package portlet;

import java.io.IOException;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;

import content.Content;
import content.ContentAPI;
import content.ContentFactory;

public class ContentListCMS extends GenericPortlet {

	protected final static Logger _log = Logger.getLogger(ContentListCMS.class);

	@Override
	protected void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {

		_log.debug("VIEW");
		
		// Access Content repository
		
		ContentAPI content = ContentFactory.getContent();
		List<Content> list = content.getContent();
		
		request.setAttribute("listcontent", list);

		// Accessing to Portlet session
		
		request.setAttribute("validation", request.getPortletSession().getAttribute("validation"));		
		
		// Controlling the view
		
		String view = "/jsp/content.jsp";

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
		
		String deletecontent = request.getParameter("deletecontent");
		String deletelocale = request.getParameter("deletelocale");
		
		if (deletecontent != null) {
			ContentAPI content = ContentFactory.getContent();
			content.removeContent(deletecontent, deletelocale);			
			return;
		}
		
			
		request.getPortletSession().setAttribute("validation", validation);					
	}

}
