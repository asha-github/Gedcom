package com.gedcom.core;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class GedcomParserTest {
    /**
     * Test to verify the invocation of data reader and data writer.
     */
    @Test
    public void test_parse() {
        String inputFilePath = "src/test/resources/Input_ValidData.txt";
        String outputFilePath = "src/test/resources/Output_ValidData.xml";
        GedcomParser parser = GedcomParser.getParser();

        parser.parse(inputFilePath, outputFilePath);

        // verification
        File file = new File(outputFilePath);
        Assert.assertTrue(file.exists());
        // clean up
        file.delete();
    }
}
