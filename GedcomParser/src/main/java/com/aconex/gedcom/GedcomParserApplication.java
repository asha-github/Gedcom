package com.aconex.gedcom;

import com.aconex.gedcom.core.GedcomParser;

public class GedcomParserApplication {

    public static String DEFAULT_INPUT_FILE_PATH = "/Users/ashas/Projects/Gedcom/GedcomParser/src/main/resources/sampleinput.txt";

    public static String DEFAULT_OUTPUT_FILE_PATH = "/Users/ashas/Projects/Gedcom/GedcomParser/src/main/resources/output.xml";

    public static void main(String[] args) {
        String inputFilePath = DEFAULT_INPUT_FILE_PATH;
        String outputFilePath = DEFAULT_OUTPUT_FILE_PATH;

        if (args.length == 1) {
            inputFilePath = args[0];
        } else if (args.length == 2) {
            inputFilePath = args[0];
            outputFilePath = args[1];
        }
        GedcomParser.getParser().parse(inputFilePath, outputFilePath);
    }

}
