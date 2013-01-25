package images;

import java.io.Serializable;

public class Image implements Serializable {

	private static final long serialVersionUID = -7000725040481769342L;
	
	private String contentType;
	private long size;
	private String name;

	byte[] content;

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}
	
}
