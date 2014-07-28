/**
 * 
 */
package com.codeshare.notificationmanager.mail;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.web.multipart.MultipartFile;

import com.codeshare.notificationmanager.model.Mail;
import com.codeshare.notificationmanager.model.MailRequest;
import com.codeshare.notificationmanager.model.NotificationManagerException;

/**
 * @author vibhor.rastogi
 * 
 */
public interface Mailer {

	Mail prepare(final MailRequest request,
			final List<MultipartFile> multipartFileList)
			throws NotificationManagerException, IOException,
			MessagingException;

	void send(final Mail mail) throws Exception;

	boolean validate(final Mail mail);
}
