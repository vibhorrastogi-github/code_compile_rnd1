/**
 * 
 */
package com.codeshare.notificationmanager.mail;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.codeshare.notificationmanager.model.MailRequest;
import com.codeshare.notificationmanager.model.NotificationManagerException;

/**
 * @author vibhor.rastogi
 * 
 */
public interface MailManager {

	boolean sendMail(final MailRequest mailRequest,
			final List<MultipartFile> multipartFileList)
			throws NotificationManagerException;

	boolean sendMail(final MailRequest mailRequest)
			throws NotificationManagerException;

}
