package com.sadad.sw.sequencemaster.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ramin Pakzad
 */
public interface RegularExpressionUtil {
    static String getTextByPattern(String content, String regex) {
        Pattern compile = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = compile.matcher(content);

        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }
}
