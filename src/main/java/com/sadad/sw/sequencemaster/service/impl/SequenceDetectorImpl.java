package com.sadad.sw.sequencemaster.service.impl;

import com.sadad.sw.sequencemaster.service.SequenceDetector;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ramin pakzad (rpakzadmanesh@gmail.com) on 4/12/2019.
 */
@Service
public class SequenceDetectorImpl implements SequenceDetector {
    @Override
    public boolean isItContainsSequence(String text) {
        String pattern = "\\bsequence\\b";

        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(text);
        if (m.find( )) {
            String group = m.group(0);
            System.out.println(group);
            return true;
        }else {
            return false;
        }
    }
}
