package ca.uqo.pret;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class JsonLogFormatter extends Formatter {

    private String escape(String value) {
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }

    @Override
    public String format(LogRecord record) {
        return "{\"timestamp\":\"" + record.getInstant()
                + "\",\"level\":\"" + record.getLevel()
                + "\",\"logger\":\"" + escape(record.getLoggerName())
                + "\",\"message\":\"" + escape(formatMessage(record))
                + "\"}\n";
    }
}
