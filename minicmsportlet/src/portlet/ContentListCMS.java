package portlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

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
	private String site;
	

	@Override
	protected void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {

		_log.debug("VIEW");
		
		
		
	     site = this.getSite(request);
		
		
		// Access Content repository
		
		ContentAPI content = ContentFactory.getContent();
		List<Content> list = content.getContent(site);
		
		request.setAttribute("listcontent", list);
		request.setAttribute("site", site);

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
		
		site = this.getSite(request);
		
		// Validation messages
		String validation = "";
		
		String deletecontent = request.getParameter("deletecontent");
		String deletelocale = request.getParameter("deletelocale");
		
		if (deletecontent != null) {
			ContentAPI content = ContentFactory.getContent();
			content.removeContent(deletecontent, deletelocale, site);			
			return;
		}
		
			
		request.getPortletSession().setAttribute("validation", validation);					
	}

	// Methods for getting site
	
	private String getSite (RenderRequest request) {
		for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet())
	      {
	    	  
	    	 if (entry.getKey().equals("site_name"))
	    		 site=entry.getValue()[0];
	      }
		System.out.println("site content render:"+site);
		return site;
	}
	private String getSite (ActionRequest request) {
		for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet())
	      {
	    	  
	    	 if (entry.getKey().equals("site_name"))
	    		 site=entry.getValue()[0];
	      }
		System.out.println("site content action:"+site);
		return site;
	}
}
