package com.sadad.sw.sequencemaster.service.impl;

import com.sadad.sw.sequencemaster.service.SequenceCatcher;
import com.sadad.sw.sequencemaster.util.RegularExpressionUtil;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ramin Pakzad
 */
@Service
public class DefaultSequenceCatcher implements SequenceCatcher {
    private String regex = "([\\ssequence\\s][\\s}\\s,\\s])";

    @Override
    public String getSequence(String content) {
        return RegularExpressionUtil.getTextByPattern(content, regex);
    }


}
