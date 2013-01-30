package content;

public class ContentFactory {

	static public ContentAPI getContent() {
		// return MemoryContentImpl.init();
		return new CacheContentImpl();
	}
	
}
