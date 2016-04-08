package com.emergya.utils;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * Class to format text
 * @author Alejandro Gomez <agommor@gmail.com>
 */
public class StringFormatter {

    /**
     * Escape strings with ' for xpath expression
     * @param originalString
     * @return escaped string
     */
    public static String escapeSimpleQuotesForXpath(String originalString) {
        return escapeQuotesForXpath(originalString, "'");
    }

    /**
     * Escape strings with " for xpath expression
     * @param originalString
     * @return escaped string
     */
    public static String escapeDoubleQuotesForXpath(String originalString) {
        return escapeQuotesForXpath(originalString, "\"");
    }

    /**
     * Escape strings with " or ' for xpath expression
     * @param originalString
     * @return escaped string
     */
    private static String escapeQuotesForXpath(String originalString,
            String quote) {
        String formattedString = "";
        if (StringUtils.isNotBlank(originalString)
                && StringUtils.isNotBlank(quote)) {
            String xpathDelimitter = "'";
            String xpathDelimiterForQuote;
            if (quote.equals("\"")) {
                xpathDelimiterForQuote = "'";
            } else {
                xpathDelimiterForQuote = "\"";
            }

            if (originalString.startsWith(quote)) {
                formattedString += xpathDelimiterForQuote + quote
                        + xpathDelimiterForQuote;
                formattedString += ",";
            }
            String[] parts = originalString.split(quote);
            if (parts.length > 1) {
                for (int i = 0; i < parts.length; i++) {
                    formattedString += xpathDelimitter + parts[i]
                            + xpathDelimitter;
                    if (i < parts.length - 1) {
                        formattedString += ",";
                        formattedString += xpathDelimiterForQuote + quote
                                + xpathDelimiterForQuote;
                        formattedString += ",";
                    }
                }
                if (originalString.endsWith(quote)) {
                    formattedString += ",";
                    formattedString += xpathDelimiterForQuote + quote
                            + xpathDelimiterForQuote;
                }
                formattedString = "concat(" + formattedString + ")";
            } else {
                formattedString = xpathDelimitter + originalString
                        + xpathDelimitter;
            }
        }
        return formattedString;
    }

    /**
     * Method to get a random string and different from other.
     * @param stringToBeDifferent string we want to have another different.
     * @return a different random string.
     */
    public static String getARandomAndDifferentStringOf(
            String stringToBeDifferent) {
        StringBuffer buffer = new StringBuffer();
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        if (StringUtils.isNotBlank(stringToBeDifferent)) {
            do {
                for (int i = 0; i < stringToBeDifferent.length(); i++) {
                    double index = Math.random() * characters.length();
                    buffer.append(characters.charAt((int) index));
                }
            } while (buffer.toString().equalsIgnoreCase(stringToBeDifferent));
        }
        return buffer.toString();
    }

    /**
     * Method to get a random string and different from others.
     * @param stringToBeDifferent string we want to have another different.
     * @return a different random string.
     */
    public static String getARandomAndDifferentStringOf(
            List<String> stringsToBeDifferent) {

        StringBuffer buffer = new StringBuffer();
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        if (!stringsToBeDifferent.isEmpty()) {
            do {
                for (int i = 0; i < stringsToBeDifferent.get(0).length(); i++) {
                    double index = Math.random() * characters.length();
                    buffer.append(characters.charAt((int) index));
                }
            } while (stringsToBeDifferent.contains(buffer.toString()));
        }
        return buffer.toString();
    }

    /**
     * Method to get a random string with a lenght.
     * @param length string length
     * @return a random string.
     */
    public static String getARandomWithALenght(int length) {
        StringBuffer buffer = new StringBuffer();
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

        for (int i = 0; i < length; i++) {
            double index = Math.random() * characters.length();
            buffer.append(characters.charAt((int) index));
        }

        return buffer.toString();
    }
}
