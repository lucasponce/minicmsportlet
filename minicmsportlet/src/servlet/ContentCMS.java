package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import content.ContentAPI;
import content.ContentFactory;

/**
 * Servlet implementation class ImageCMS
 */
public class ContentCMS extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ContentCMS() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Get key parameter of the object
		String key = request.getParameter("key");
		String site = request.getParameter("site");
		
		// Access to Content repository
		
		ContentAPI contents = ContentFactory.getContent();

		String keystr = key.split("_")[0];
		String localestr = key.split("_")[1];

		String content = contents.getContent(keystr, localestr,site);
		
		if (content != null) {
			
			System.out.println("Content: " + content);
									
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(header());
			response.getWriter().print(content);
			response.getWriter().flush();
			response.getWriter().print(footer());
			
		}
		
	}

	private String header() {		 
		return "<html><head></head><body><div>";
	}
	
	private String footer() {
		return "</div></body></html>";
	}
	
}
