package content;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;

import org.apache.log4j.Logger;
import org.jboss.cache.Cache;
import org.jboss.cache.Fqn;
import org.jboss.cache.jmx.CacheJmxWrapperMBean;
import org.jboss.mx.util.MBeanServerLocator;

public class CacheContentImpl implements ContentAPI {

	@SuppressWarnings("rawtypes")
	public Cache contentCache = null;
	public final String CACHE_NAME="jboss.cache:service=MiniCMSCache";
	
	private static final Logger log = Logger.getLogger(CacheContentImpl.class);
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getContent(String key, String locale) {
		checkCache();
		if (contentCache != null) {
			String fqnstr = "/content/" + key + "/" + locale;
			Fqn fqn = Fqn.fromString(fqnstr);
			String content = (String)contentCache.get(fqn, key);
			return content;
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setContent(String key, String locale, String content) {
		checkCache();
		if (contentCache != null) {
			String fqnstr = "/content/" + key + "/" + locale;
			Fqn fqn = Fqn.fromString(fqnstr);
			contentCache.put(fqn, key, content);			
		}
	}
	
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Content> getContent() {
		checkCache();
		if (contentCache != null) {
			
			ArrayList<Content> aContent = new ArrayList<Content>();
			
			String fqnstr = "/content";
			Fqn fqn = Fqn.fromString(fqnstr);
			for (String key : (Set<String>)contentCache.getChildrenNames(fqn)) {
				String fqnchildstr = "/content/" + key;
				Fqn fqnchild = Fqn.fromString(fqnchildstr);
				for (String locale : (Set<String>)contentCache.getChildrenNames(fqnchild)) {
					Content content = new Content();
					aContent.add(content);
					
					content.setKey(key);
					content.setLocale(locale);
					String fqncontentstr = "/content/" + key + "/" + locale;
					Fqn fqncontent = Fqn.fromString(fqncontentstr);
					content.setContent((String)contentCache.get(fqncontent, key));
				}
			}
			
			return aContent;
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void removeContent(String key, String locale) {
		checkCache();
		if (contentCache != null) {
			String fqnstr = "/content/" + key + "/" + locale;
			Fqn fqn = Fqn.fromString(fqnstr);
			contentCache.remove(fqn, key);
			contentCache.removeNode(fqn);
		}		
	}

	@SuppressWarnings("rawtypes")
	private void checkCache()  {
		if (contentCache == null) {
			try {
				log.debug("Creating API to Cache: " + CACHE_NAME);
				MBeanServer server = MBeanServerLocator.locateJBoss();
				ObjectName on = new ObjectName(CACHE_NAME);
				CacheJmxWrapperMBean cacheWrapper = 
						(CacheJmxWrapperMBean) MBeanServerInvocationHandler
						.newProxyInstance(server, on, CacheJmxWrapperMBean.class, false);
				contentCache = cacheWrapper.getCache();
			} catch(Exception e) {
				log.error("Error creating Cache " + e.toString() );
				e.printStackTrace();
			}
		}
	}


}