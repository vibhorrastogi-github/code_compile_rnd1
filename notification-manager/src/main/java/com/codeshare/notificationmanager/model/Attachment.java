/**
 * Jan 24, 2013
 * harkara
 * Attachment.java
 * 
 */
package com.codeshare.notificationmanager.model;

/**
 * @author vibhor.rastogi
 * 
 */
public class Attachment {
	private final byte[] bytes;
	private final String fileName;
	private final String contentType;

	/**
	 * 
	 */
	public Attachment(final byte[] bytes, final String fileName, final String contentType) {
		this.bytes = bytes;
		this.fileName = fileName;
		this.contentType = contentType;
	}

	/**
	 * @return the bytes
	 */
	public byte[] getBytes() {
		return bytes;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Attachment{fileName='" + fileName + "', contentType='" + contentType + "', size='" + bytes.length + "}";
	}
}
