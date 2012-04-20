package servlet;

import images.Image;
import images.ImagesAPI;
import images.ImagesFactory;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ImageCMS
 */
public class ImageCMS extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImageCMS() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Get key parameter of the object
		String key = request.getParameter("key");
		
		// Access to Images repository
		ImagesAPI images = ImagesFactory.getImages();
		
		Image image = images.getImage(key);
		
		if (image != null) {
			
			response.setHeader("Content-Type", image.getContentType() );
			response.setHeader("Content-Length", String.valueOf(image.getSize()));
			response.setHeader("Content-Disposition", "inline; filename=\"" + image.getName() + "\"");
			
			response.setContentType( image.getContentType() );
			response.setStatus(HttpServletResponse.SC_OK);
			

			ByteArrayInputStream input = null;
			BufferedOutputStream output = null;
			
			// input = new BufferedInputStream(file.getInputStream());
			input = new ByteArrayInputStream(image.getContent());
			output = new BufferedOutputStream(response.getOutputStream());
			
			byte[] buffer = new byte[16384];
		    for (int length = 0; (length = input.read(buffer)) > 0;) {
		        output.write(buffer, 0, length);
		    }
		    
		    input.close();
		    output.flush();
		    output.close();			
			
		}
		
	}

}
