package com.travix.medusa.busyflights.exception.utils;

import com.travix.medusa.busyflights.enums.Language;
import com.travix.medusa.busyflights.exception.enums.IMessageCode;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Slf4j
@UtilityClass
public class MessageUtils {
    private static final String RESOURCE_BUNDLE_NAME = "Message";
    private static final String SPECIAL_CHARACTER = "__";

    /**
     * This method takes Language, IMessageCode and returns String message.
     * This method provides multilingual support for error messages.
     *
     * @param language Language, messageCode IMessageCode
     * @return String
     */
    public static String getMessage(Language language, IMessageCode messageCode) {
        String messageKey = null;
        try {
            Locale locale = new Locale(language.name());
            ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, locale);
            messageKey = messageCode.getClass().getSimpleName() + SPECIAL_CHARACTER + messageCode;
            return resourceBundle.getString(messageKey);
        } catch (MissingResourceException missingResourceException) {
            log.error("Message not found for key: {}", messageKey);
            return null;
        }
    }
}
