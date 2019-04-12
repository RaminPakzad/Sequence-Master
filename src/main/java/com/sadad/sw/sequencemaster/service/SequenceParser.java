package com.sadad.sw.sequencemaster.service;

import com.sadad.sw.sequencemaster.model.Sequence;

/**
 * @author ramin pakzad (rpakzadmanesh@gmail.com) on 4/12/2019.
 */
public interface SequenceParser {
    Sequence parseSequence(String sequence);
    String toString(Sequence sequence);
}
