package content;

import java.util.List;

public interface ContentAPI {

	String getContent(String key, String locale);
	void setContent(String key, String locale, String content);
	List<Content> getContent();
	void removeContent(String key, String locale);
	
}
