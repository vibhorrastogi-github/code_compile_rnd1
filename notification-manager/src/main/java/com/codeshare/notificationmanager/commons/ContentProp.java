/**
 * Jul 16, 2014
 * contentProp.java
 * 7:57:16 PM
 * vibhor
 */
package com.codeshare.notificationmanager.commons;

import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;

/**
 * @author vibhor
 * 
 */
public class ContentProp implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ContentProp.class);

	@Autowired
	@Qualifier("contentProp")
	private Map<String, Object> contentProp;

	/**
	 * @param <T>
	 * 
	 */
	public String get(final String key) {
		final Object value = contentProp.get(key);
		if (value == null) {
			throw new IllegalStateException("key: " + key
					+ ", not found in contentProp");
		}
		return (String) value;
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(contentProp, "contentProp map is null");
		contentProp = Collections
				.unmodifiableMap(((Map<String, Object>) contentProp.values()
						.iterator().next()));
		LOGGER.info("contentProp map: {}", contentProp);
	}
}
