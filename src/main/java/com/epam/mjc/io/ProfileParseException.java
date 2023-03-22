package com.epam.mjc.io;

import java.text.MessageFormat;

class ProfileParseException extends IllegalArgumentException {
    ProfileParseException(String message, Throwable cause) {
        super(message, cause);
    }

    static ProfileParseException ofInvalidLine(String line, Throwable cause) {
        return new ProfileParseException(MessageFormat.format("This line contains invalid information: {0}", line), cause);
    }
}
