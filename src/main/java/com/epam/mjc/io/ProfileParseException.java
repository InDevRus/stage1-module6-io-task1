package com.epam.mjc.io;

import java.text.MessageFormat;

class ProfileParseException extends IllegalArgumentException {
    ProfileParseException(String message, Throwable cause) {
        super(message, cause);
    }

    static ProfileParseException ofInvalidLine(String line, Throwable cause) {
        return new ProfileParseException(MessageFormat.format("""
                    Line
                    {0}
                    contains invalid information""", line), cause);
    }
}
