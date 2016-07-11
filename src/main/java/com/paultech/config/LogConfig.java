package com.paultech.config;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.LayoutBase;

import java.util.Date;

/**
 * Created by paulzhang on 11/07/2016.
 */
// Config customised logging info format
public class LogConfig extends LayoutBase<ILoggingEvent> {
    @Override
    public String doLayout(ILoggingEvent iLoggingEvent) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer
                .append("[")
                .append(new Date())
                .append("]").append(iLoggingEvent.getTimeStamp() - iLoggingEvent.getLoggerContextVO().getBirthTime())
                .append(" ")
                .append(iLoggingEvent.getLevel())
                .append(" ")
                .append(iLoggingEvent.getFormattedMessage())
                .append(CoreConstants.LINE_SEPARATOR);

        return stringBuffer.toString();
    }
}
