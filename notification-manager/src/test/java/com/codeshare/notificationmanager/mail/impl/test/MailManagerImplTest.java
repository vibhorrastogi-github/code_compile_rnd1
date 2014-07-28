/**
 * Jul 28, 2014
 * MailManagerImplTest.java
 * 6:55:58 PM
 * vibhor
 */
package com.codeshare.notificationmanager.mail.impl.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.codeshare.notificationmanager.mail.MailManager;
import com.codeshare.notificationmanager.model.MailRequest;
import com.codeshare.notificationmanager.model.NotificationManagerException;

/**
 * @author vibhor
 * 
 */
public class MailManagerImplTest {

	public static void main(String[] args) throws NotificationManagerException {

		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
				"notification-manager-test-ctx.xml");

		final MailManager mailManager = ctx.getBean(MailManager.class);

		final MailRequest request = new MailRequest();

		request.setDetails("test mail");
		request.setMdn("");
		request.setName("vibhor");
		request.setTo("vibhorrastogi@live.com");
		request.setType("credentialsRecovery");

		System.out.println(mailManager.sendMail(request));

		ctx.close();
	}
}
