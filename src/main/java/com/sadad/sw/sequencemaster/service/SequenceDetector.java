package com.sadad.sw.sequencemaster.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * @author ramin pakzad (rpakzadmanesh@gmail.com) on 4/12/2019.
 */
public interface SequenceDetector {
    boolean isItContainsSequence(String text);

    default boolean isItContainsSequence(File file) throws IOException {
        //new String(Files.readAllBytes(Paths.get("duke.java")))
        return isItContainsSequence(new String(Files.readAllBytes(file.toPath())));
    }
}
