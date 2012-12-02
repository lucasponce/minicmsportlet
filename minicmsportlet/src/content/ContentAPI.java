package content;

import java.util.List;

public interface ContentAPI {

	String getContent(String key, String locale);
	void setContent(String key, String locale, String content);
	List<Content> getContent();
	void removeContent(String key, String locale);
	
	//Modificación del 
	String getContent(String key, String locale,String site);
	void setContent(String key, String locale, String content, String site);
	List<Content> getContent(String site);
	void removeContent(String key, String locale, String site);
	
}
