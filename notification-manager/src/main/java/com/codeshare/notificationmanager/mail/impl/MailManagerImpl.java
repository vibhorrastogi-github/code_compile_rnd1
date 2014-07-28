/**
 * 
 */
package com.codeshare.notificationmanager.mail.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.codeshare.notificationmanager.mail.MailManager;
import com.codeshare.notificationmanager.mail.Mailer;
import com.codeshare.notificationmanager.model.Mail;
import com.codeshare.notificationmanager.model.MailRequest;
import com.codeshare.notificationmanager.model.NotificationManagerException;

/**
 * @author vibhor.rastogi
 * 
 */
public final class MailManagerImpl implements MailManager {

	private static Logger logger = LoggerFactory
			.getLogger(MailManagerImpl.class);

	@Autowired
	private Mailer mailer;

	public boolean sendMail(final MailRequest mailRequest,
			final List<MultipartFile> multipartFileList)
			throws NotificationManagerException {
		try {
			final Mail mail = mailer.prepare(mailRequest, multipartFileList);
			final boolean sendFlag = mailer.validate(mail);
			if (sendFlag) {
				mailer.send(mail);
			} else {
				logger.error("Mail Validation Failed, One of the Mandatory paramater is missing: "
						+ mail);
			}
			return sendFlag;
		} catch (Exception e) {
			logger.error("Exception while sending mail for: " + mailRequest);
			throw new NotificationManagerException(e);
		}
	}

	public boolean sendMail(final MailRequest mailRequest)
			throws NotificationManagerException {
		try {
			final Mail mail = mailer.prepare(mailRequest, null);
			final boolean sendFlag = mailer.validate(mail);
			if (sendFlag) {
				mailer.send(mail);
			} else {
				logger.error("Mail Validation Failed, One of the Mandatory paramater is missing: "
						+ mail);
			}
			return sendFlag;
		} catch (Exception e) {
			logger.error("Exception while sending mail for: " + mailRequest);
			throw new NotificationManagerException(e);
		}
	}

}
