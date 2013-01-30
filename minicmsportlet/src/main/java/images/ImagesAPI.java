package images;

import java.util.Set;

import org.apache.commons.fileupload.FileItem;

public interface ImagesAPI {
	
	Image getImage(String key);
	void setImage(String key, FileItem file);
	void removeImage(String key);
	public Set<String> getImages();
	
}
