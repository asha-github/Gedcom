package com.aconex.gedcom;

import java.io.File;

import com.aconex.gedcom.core.GedcomParser;

/**
 * Main class of Gedcom Parser Application
 * 
 * @author ashas
 *
 */
public class GedcomParserApplication {

    public static String DEFAULT_INPUT_FILE_PATH = "src/main/resources/sampleinput.txt";

    public static String DEFAULT_OUTPUT_FILE_PATH = "output/output.xml";

    public static void main(String[] args) {

        String inputFilePath = null;
        String outputFilePath = null;

        if (args.length == 1) {
            inputFilePath = args[0];
        } else if (args.length == 2) {
            inputFilePath = args[0];
            outputFilePath = args[1];
        }
        if (inputFilePath == null) {
            inputFilePath = getPath(DEFAULT_INPUT_FILE_PATH);
        }
        if (outputFilePath == null) {
            outputFilePath = getPath(DEFAULT_OUTPUT_FILE_PATH);
        }
        GedcomParser.getParser().parse(inputFilePath, outputFilePath);
    }

    private static String getPath(String relativeFilePath) {
        File file = new File(relativeFilePath);
        return file.getAbsolutePath();
    }

}
