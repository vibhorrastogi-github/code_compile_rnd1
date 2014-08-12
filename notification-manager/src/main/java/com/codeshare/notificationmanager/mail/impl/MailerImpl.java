/**
 * 
 */
package com.codeshare.notificationmanager.mail.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.codeshare.notificationmanager.commons.Const;
import com.codeshare.notificationmanager.commons.ContentProp;
import com.codeshare.notificationmanager.mail.Mailer;
import com.codeshare.notificationmanager.model.Attachment;
import com.codeshare.notificationmanager.model.Mail;
import com.codeshare.notificationmanager.model.MailRequest;
import com.codeshare.notificationmanager.model.NotificationManagerException;

/**
 * @author vibhor.rastogi
 * 
 */
public final class MailerImpl implements Mailer {

	private static final Logger logger = LoggerFactory
			.getLogger(MailerImpl.class);

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private VelocityEngine velocityEngine;

	@Autowired
	private ContentProp contentProp;

	@Value("${common.logo.loc}")
	private String commonLogoLoc;

	public Mail prepare(final MailRequest mailRequest,
			final List<MultipartFile> multipartFileList)
			throws NotificationManagerException, IOException,
			MessagingException {
		final Mail mail = new Mail();
		mail.setMdn(mailRequest.getMdn());
		mail.setName(StringUtils.hasText(mailRequest.getName()) ? mailRequest
				.getName() : mailRequest.getMdn());
		mail.setDetails(mailRequest.getDetails());
		mail.setTo(mailRequest.getTo());
		mail.setSubject(contentProp.get(mailRequest.getType() + Const.dot
				+ Const.subject)
				+ mailRequest.getSubject());

		mail.setContent(contentProp.get(mailRequest.getType() + Const.dot
				+ Const.content));
		// if (StringUtils.hasText(contentProp.get(mailRequest.getType()
		// + Const.dot + Const.logos))) {
		// mail.setLogos(contentProp.get(
		// mailRequest.getType() + Const.dot + Const.logos).split(","));
		// }
		// String link = contentProp.get(Const.unsubscribe + Const.dot
		// + Const.link);
		// final String key = "\"mdn\"";
		// final String value = "\"" + mailRequest.getMdn() + "\"";
		// try {
		// link = link + URLEncoder.encode(key, Const.UTF8) + ":"
		// + URLEncoder.encode(value, Const.UTF8) + "}";
		// } catch (UnsupportedEncodingException e) {
		// throw new NotificationManagerException(e);
		// }
		// mail.setUnsubscribeLink(link);
		if (multipartFileList != null && multipartFileList.size() > 0) {
			mail.setAttachmentAvailable(true);
			for (final MultipartFile multipartFile : multipartFileList) {
				final byte[] bytes = getAttachmentBytes(mailRequest,
						multipartFile);
				final String fileName = multipartFile.getOriginalFilename();
				final String contentType = multipartFile.getContentType();
				final Attachment attachment = new Attachment(bytes, fileName,
						contentType);
				mail.getAttachments().add(attachment);
			}
		}
		return mail;
	}

	/**
	 * @param mailRequest
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 */
	private byte[] getAttachmentBytes(final MailRequest mailRequest,
			final MultipartFile multipartFile) throws IOException,
			MessagingException {
		final InputStream is = multipartFile.getInputStream();
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final byte[] buffer = new byte[2048];
		int i = 0;
		while ((i = is.read(buffer)) > 0) {
			baos.write(buffer, 0, i);
		}
		return baos.toByteArray();
	}

	public boolean validate(final Mail mail) {
		return StringUtils.hasText(mail.getTo())
				&& StringUtils.hasText(mail.getSubject())
				&& StringUtils.hasText(mail.getContent());
	}

	public void send(final Mail mail) throws NotificationManagerException {
		logger.info("Sending mail: " + mail);
		final MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(final MimeMessage mimeMessage) throws Exception {
				final MimeMessageHelper message = new MimeMessageHelper(
						mimeMessage, true);
				if (mail.isAttachmentAvailable()) {
					for (final Attachment attachment : mail.getAttachments()) {
						logger.info("Adding attachment: " + attachment);
						message.addAttachment(attachment.getFileName(),
								new ByteArrayDataSource(attachment.getBytes(),
										attachment.getContentType()));
					}
				}
				message.setTo(mail.getTo());
				final JavaMailSenderImpl javaMailSenderImpl = (JavaMailSenderImpl) mailSender;
				message.setFrom(javaMailSenderImpl.getUsername());
				message.setSubject(mail.getSubject());
				final Map<String, Object> map = new HashMap<String, Object>();
				map.put("mail", mail);
				logger.debug("VelocityEngine: " + velocityEngine);
				final String text = VelocityEngineUtils
						.mergeTemplateIntoString(velocityEngine,
								mail.getContent(), "UTF-8", map);
				logger.debug("text: {}", text);
				message.setText(text, true);
				String cid = "";
				String image = "";
				// final String logoLoc = contentProp.get(Const.common +
				// Const.dot
				// + Const.logo + Const.dot + Const.loc);
				final String commonLogo = contentProp.get(Const.common
						+ Const.dot + Const.logo);
				logger.debug("commonLogo: " + commonLogo);
				if (StringUtils.hasText(commonLogo)) {
					cid = commonLogo.substring(0, commonLogo.indexOf(':'));
					logger.debug("cid: " + cid);
					image = commonLogoLoc
							+ commonLogo.substring(
									(commonLogo.indexOf(':') + 1),
									commonLogo.length());
					logger.debug("image: {}", image);
					message.addInline(cid, new FileSystemResource(new File(
							image)));
				}
				// if (mail.getLogos() != null && mail.getLogos().length > 0) {
				// for (final String logo : mail.getLogos()) {
				// cid = logo.substring(0, logo.indexOf(':'));
				// image = loc
				// + logo.substring((logo.indexOf(':') + 1),
				// logo.length());
				// message.addInline(cid, new FileSystemResource(new File(
				// image)));
				// }
				// }
			}
		};
		logger.debug("MimeMessagePreparator: " + preparator);
		logger.debug("JavaMailSender: " + mailSender);
		mailSender.send(preparator);
		logger.info("Sent mail: " + mail);
	}
}
