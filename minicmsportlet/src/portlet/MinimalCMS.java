package portlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

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

import org.exoplatform.services.organization.OrganizationService;
import org.exoplatform.container.PortalContainer;
import org.exoplatform.services.organization.Group;

public class MinimalCMS extends GenericPortlet {

	protected final static Logger _log = Logger.getLogger(MinimalCMS.class);

	final String REPO = "repo:";
	final String INTERNAL_URL = "/minicmsportlet/image?key=";
	private String site;
	
	
	@Override
	protected void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		
		_log.debug("VIEW");
		
		site = this.getSite(request);
		
		// Accessing to Portlet preferences
		
		PortletPreferences prefs = request.getPreferences();
		String edit = prefs.getValue("edit_role", "none"); 
		String key = prefs.getValue("content_key", "default");
		String locale = request.getLocale().getLanguage();

		request.setAttribute("content_key", key);
		
		_log.debug("edit: " + edit + " key: " + key + " locale: " + locale);
				
		// Accessing to Content repository
		
		ContentAPI content = ContentFactory.getContent();
		String content_view = content.getContent(key, locale,site);
		
		if (content_view == null) content_view = "";		
		request.setAttribute("content_view", content_view);		
		
		// Controlling the view
		
		// Default view: simple read user
		
		String view = "/jsp/view.jsp";

		// Checking admin user: view with edit link
		
		OrganizationService os = (OrganizationService)PortalContainer
					.getInstance()
					.getComponentInstanceOfType(OrganizationService.class);	

		if (request.getUserPrincipal() != null &&
		    request.getUserPrincipal().getName() != null) {
		
		    try {
			    for (Object o : os.getGroupHandler().findGroupsOfUser(request.getRemoteUser())){						
				Group g = (Group)o;						
				if (g.getGroupName().equals(edit)) {
					view = "/jsp/viewadmin.jsp";
					break;		        
				}
			    }	
		    } catch (Exception e) {
			_log.error("Error accesing to Organization API");			
		    }		
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
		
		site = this.getSite(request);
		
		// Accesing form params
		
		String edit_view = request.getParameter("edit_view");		
		if (edit_view == null) edit_view = "";
				
		// Encoding parameters

		String IN_ENCODING = "UTF8";
		String OUT_ENCODING = "UTF8";

		if (System.getProperty("minicms.in.encoding") != null)
			IN_ENCODING = System.getProperty("minicms.in.encoding");

		if (System.getProperty("minicms.out.encoding") != null)
			OUT_ENCODING = System.getProperty("minicms.out.encoding");

		edit_view = new String(edit_view.getBytes(IN_ENCODING),OUT_ENCODING);		

		_log.debug("edit_view: " + edit_view);
		
		// Replacing repo: shorcuts for internal images
		edit_view = edit_view.replaceAll(REPO, INTERNAL_URL);
		
		// Accessing to Portlet preferences
		
		PortletPreferences prefs = request.getPreferences();
		String key = prefs.getValue("content_key", "default");
		String locale = request.getLocale().getLanguage();		
				
		// Accessing to Content repository
		
		ContentAPI content = ContentFactory.getContent();
		content.setContent(key, locale, edit_view,site);		
				
	}

	// Methods for getting site
	
		private String getSite (RenderRequest request) {
			for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet())
		      {
		    	  
		    	 if (entry.getKey().equals("site_name"))
		    		 site=entry.getValue()[0];
		      }
			System.out.println("site minimalcms render:"+site);
			return site;
		}
		private String getSite (ActionRequest request) {
			for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet())
		      {
		    	  
		    	 if (entry.getKey().equals("site_name"))
		    		 site=entry.getValue()[0];
		      }
			System.out.println("site minimalcms action:"+site);
			return site;
		}
	
}
