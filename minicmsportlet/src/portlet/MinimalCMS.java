package portlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.log4j.Logger;

import content.ContentAPI;
import content.ContentFactory;

public class MinimalCMS extends GenericPortlet {

	protected final static Logger _log = Logger.getLogger(MinimalCMS.class);

	final String REPO = "repo:";
	final String INTERNAL_URL = "/minicmsportlet/image?key=";
	
	@Override
	protected void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		
		_log.debug("VIEW");

		// Accessing to Portlet preferences
		
		PortletPreferences prefs = request.getPreferences();
		String edit = prefs.getValue("edit_user", "none"); 
		String key = prefs.getValue("content_key", "default");
		String locale = request.getLocale().getLanguage();

		request.setAttribute("content_key", key);
		
		_log.debug("edit: " + edit + " key: " + key + " locale: " + locale);
				
		// Accessing to Content repository
		
		ContentAPI content = ContentFactory.getContent();
		String content_view = content.getContent(key, locale);
		
		if (content_view == null) content_view = "";		
		request.setAttribute("content_view", content_view);		
		
		// Controlling the view
		
		// Default view: simple read user
		
		String view = "/jsp/view.jsp";

		// Checking admin user: view with edit link
		
		if (request.getUserPrincipal() != null &&
			request.getUserPrincipal().getName() != null &&
			request.getUserPrincipal().getName().equals(edit)) {
			view = "/jsp/viewadmin.jsp";
		}

		// Forward to edit view
		
		String editurl = (String)request.getParameter("editurl");
		if (editurl != null &&
			editurl.equals("edit")) {
			view = "/jsp/edit.jsp";
		}
		
		// Return to view admin view
		
		if (editurl != null &&
			editurl.equals("viewadmin")) {
			view = "/jsp/viewadmin.jsp";
		}		
		
			
		PortletRequestDispatcher prd = getPortletContext()
				.getRequestDispatcher(view);				
		prd.include(request, response);

	}

	@Override
	public void processAction(ActionRequest request, ActionResponse response)
			throws PortletException, IOException {

		_log.debug("ACTION");
		
		// Accesing form params
		
		String edit_view = request.getParameter("edit_view");		
		if (edit_view == null) edit_view = "";
				
		edit_view = new String(edit_view.getBytes("8859_1"),"UTF8");
		
		_log.debug("edit_view: " + edit_view);
		
		// Replacing repo: shorcuts for internal images
		edit_view = edit_view.replaceAll(REPO, INTERNAL_URL);
		
		// Accessing to Portlet preferences
		
		PortletPreferences prefs = request.getPreferences();
		String key = prefs.getValue("content_key", "default");
		String locale = request.getLocale().getLanguage();		
				
		// Accessing to Content repository
		
		ContentAPI content = ContentFactory.getContent();
		content.setContent(key, locale, edit_view);		
				
	}

}