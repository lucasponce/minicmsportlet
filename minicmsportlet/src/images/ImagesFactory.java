package images;

public class ImagesFactory {

	public static ImagesAPI getImages() {
		// return MemoryImagesImpl.init();
		return new CacheImagesImpl();
	}
	
}
