package com.travix.medusa.busyflights.exception.exceptions;

import com.travix.medusa.busyflights.enums.Language;
import com.travix.medusa.busyflights.exception.enums.IMessageCode;
import com.travix.medusa.busyflights.exception.utils.MessageUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class BusyFlightsNotFoundException extends RuntimeException {
    private final Language language;
    private final IMessageCode messageCode;

    public BusyFlightsNotFoundException(Language language, IMessageCode messageCode, String developerMessage) {
        super(developerMessage);
        this.language = language;
        this.messageCode = messageCode;
        log.error("[BusyFlightsNotFoundException] -> message: {} developerMessage: {}", MessageUtils.getMessage(language, messageCode), developerMessage);
    }
}
