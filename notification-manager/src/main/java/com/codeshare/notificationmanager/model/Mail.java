/**
 * 
 */
package com.codeshare.notificationmanager.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vibhor.rastogi
 * 
 */
public final class Mail {
	private String mdn;
	private String name;
	private String subject;
	private String content;
	private String details;
	private String to;
	private String[] logos;
	private String unsubscribeLink;
	private boolean attachmentAvailable;
	private final List<Attachment> attachments = new ArrayList<Attachment>();

	public String getUnsubscribeLink() {
		return unsubscribeLink;
	}

	public void setUnsubscribeLink(String unsubscribeLink) {
		this.unsubscribeLink = unsubscribeLink;
	}

	public String[] getLogos() {
		return logos;
	}

	public void setLogos(String[] logos) {
		this.logos = logos;
	}

	/**
	 * @return the mdn
	 */
	public String getMdn() {
		return mdn;
	}

	/**
	 * @param mdn
	 *            the mdn to set
	 */
	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * @param details
	 *            the details to set
	 */
	public void setDetails(String details) {
		this.details = details;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to
	 *            the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * @return the attachmentAvailable
	 */
	public boolean isAttachmentAvailable() {
		return attachmentAvailable;
	}

	/**
	 * @param attachmentAvailable
	 *            the attachmentAvailable to set
	 */
	public void setAttachmentAvailable(boolean attachmentAvailable) {
		this.attachmentAvailable = attachmentAvailable;
	}

	/**
	 * @return the attachments
	 */
	public List<Attachment> getAttachments() {
		return attachments;
	}

	@Override
	public String toString() {
		return "Mail{mdn='" + mdn + "', name='" + name + "', details='" + details + "', content='" + content
				+ "', subject='" + subject + "', to='" + to + "', unsubscribeLink='" + unsubscribeLink
				+ "', isAttachmentAvailable='" + attachmentAvailable + "', attachments='" + attachments + "'}";
	}
}
