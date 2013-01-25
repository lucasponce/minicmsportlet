package images;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;

import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;
import org.jboss.cache.Cache;
import org.jboss.cache.Fqn;
import org.jboss.cache.jmx.CacheJmxWrapperMBean;
import org.jboss.mx.util.MBeanServerLocator;

public class CacheImagesImpl implements ImagesAPI {

	@SuppressWarnings("rawtypes")
	public Cache imagesCache = null;
	public final String CACHE_NAME="jboss.cache:service=MiniCMSCache";	
	
	private static final Logger log = Logger.getLogger(CacheImagesImpl.class);
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Image getImage(String key) {
		checkCache();
		if (imagesCache != null) {
			String fqnstr = "/images/" + key;
			Fqn fqn = Fqn.fromString(fqnstr);
			Image image = (Image)imagesCache.get(fqn, key);
			return image;
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setImage(String key, FileItem file) {
		checkCache();
		if (imagesCache != null) {
			String fqnstr = "/images/" + key;
			Fqn fqn = Fqn.fromString(fqnstr);
			Image image = convert(file);
			imagesCache.put(fqn, key, image);								
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void removeImage(String key) {
		checkCache();
		if (imagesCache != null) {
			String fqnstr = "/images/" + key;
			Fqn fqn = Fqn.fromString(fqnstr);
			imagesCache.remove(fqn, key);	
			imagesCache.removeNode(fqn);
		}		
	}

	@SuppressWarnings("unchecked")
	public Set<String> getImages() {
		checkCache();
		if (imagesCache != null) {
			return imagesCache.getChildrenNames("/images");
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	private void checkCache()  {
		if (imagesCache == null) {
			try {
				log.debug("Creating API to Cache: " + CACHE_NAME);
				MBeanServer server = MBeanServerLocator.locateJBoss();
				ObjectName on = new ObjectName(CACHE_NAME);
				CacheJmxWrapperMBean cacheWrapper = 
						(CacheJmxWrapperMBean) MBeanServerInvocationHandler
						.newProxyInstance(server, on, CacheJmxWrapperMBean.class, false);
				imagesCache = cacheWrapper.getCache();
			} catch(Exception e) {
				log.error("Error creating Cache " + e.toString() );
				e.printStackTrace();
			}
		}
	}
	
	private Image convert(FileItem f) {
		if (f==null) return null;
		
		Image image = new Image();
		image.setName(f.getName());
		image.setContentType(f.getContentType());
		image.setSize(f.getSize());

		BufferedInputStream input = null;
		ByteArrayOutputStream output = null;
		
		try {
			input = new BufferedInputStream(f.getInputStream());		
			output = new ByteArrayOutputStream((int)f.getSize());
			byte[] buffer = new byte[16384];
		    for (int length = 0; (length = input.read(buffer)) > 0;) {
		        output.write(buffer, 0, length);
		    }
	    
		    input.close();
		    output.flush();
		    output.close();
		    
		    image.setContent(output.toByteArray());
		
		} catch (IOException e) {
			log.error("Error reading file " + f.getName());
			e.printStackTrace();
		}
	    
		return image;
	}

}
