package com.epam.mjc.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;


public class FileReader {
    //I would rather create enum right here, but we haven't studied that yet.
    private static final String NAME_FIELD = "Name";
    private static final String AGE_FIELD = "Age";
    private static final String EMAIL_FIELD = "Email";
    private static final String PHONE_FIELD = "Phone";
    private static final String fieldNamesPattern = String.join("|", List.of(NAME_FIELD, AGE_FIELD, EMAIL_FIELD, PHONE_FIELD));
    //Nevertheless, neither regular expressions nor String.split method were studied, so I've chosen the first option.
    private static final Pattern dataPattern = Pattern.compile(String.format("(%s): (.+)", fieldNamesPattern));

    private void updateProfileFromLine(Profile profile, String line) {
        var matcher = dataPattern.matcher(line);
        if (!matcher.find()) {
            throw ProfileParseException.ofInvalidLine(line, null);
        }

        try {
            updateProfile(profile, matcher.group(1), matcher.group(2));
        } catch (NumberFormatException exception) {
            throw ProfileParseException.ofInvalidLine(line, exception);
        }
    }

    private void updateProfile(Profile profile, String fieldName, String value) {
        switch (fieldName) {
            case NAME_FIELD: {
                profile.setName(value);
                break;
            }
            case AGE_FIELD: {
                profile.setAge(Integer.parseInt(value));
                break;
            }

            case EMAIL_FIELD: {
                profile.setEmail(value);
                break;
            }
            case PHONE_FIELD: {
                profile.setPhone(Long.parseLong(value));
                break;
            }
        }
    }

    public Profile getDataFromFile(File file) {
        var profile = new Profile();
        try (var reader = new BufferedReader(new java.io.FileReader(file))) {
            String line;
            while (Objects.nonNull(line = reader.readLine())) {
                updateProfileFromLine(profile, line);
            }
        } catch (IOException | ProfileParseException exception) {
            System.err.println("There was an error during file reading:");
            System.err.println(exception.getMessage());
        }
        return profile;
    }
}
