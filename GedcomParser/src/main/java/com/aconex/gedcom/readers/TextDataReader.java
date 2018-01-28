package com.aconex.gedcom.readers;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.aconex.gedcom.core.DataNode;
import com.aconex.gedcom.core.DataStore;
import com.aconex.gedcom.exception.GedcomParserErrors;
import com.aconex.gedcom.exception.GedcomParserException;

/**
 * Reader implementation for the input data in text format
 * 
 * @author ashas
 *
 */
public class TextDataReader implements IGedcomDataReader {
    private static final Logger logger = Logger.getLogger(TextDataReader.class.getSimpleName());

    /**
     * API to parse the gedcom fle
     */
    @Override
    public DataNode parseDataFile(String file) throws GedcomParserException {
        logger.log(Level.INFO, "Reading input text data file");
        DataStore dataStore = DataStore.getDataStore();
        try (Stream<String> inputStream = Files.lines(Paths.get(file), Charset.defaultCharset())) {
            List<String> inputLines = inputStream
                    .filter(inputData -> (inputData != null && !inputData.trim().isEmpty()))
                    .collect(Collectors.toCollection(LinkedList::new));
            for (String inputData : inputLines) {
                dataStore.addNode(getDataNode(inputData));
            }
        } catch (IOException e) {
            throw new GedcomParserException(GedcomParserErrors.INPUT_FILE_READING_FAILED);
        }
        logger.log(Level.INFO, "Reading input text data file completed.");
        return dataStore.getRoot();
    }

    /**
     * Method to create the data node for each extracted line from input file
     * text. Format expected is LEVEL TAG-OR-ID [DATA]. Level and TAG-OR-ID is
     * considered mandatory. If not present, exception is thrown.
     * 
     * @param inputData
     * @return
     * @throws GedcomParserException
     */
    private DataNode getDataNode(String inputData) throws GedcomParserException {
        logger.log(Level.INFO, "Creating data node for input > " + inputData);
        String[] inputStringArray = inputData.trim().split("\\s+", 3);
        String id = null;
        String tag = null;
        String data = null;
        int level = 0;
        try {
            level = Integer.parseInt(inputStringArray[0]);
        } catch (NumberFormatException ex) {
            throw new GedcomParserException(GedcomParserErrors.LEVEL_INFO_NOT_FOUND);
        }
        if (inputStringArray[1].matches(ID_PATTERN)) {
            id = inputStringArray[1];
            if (inputStringArray.length == 3) {
                tag = inputStringArray[2];
            }
        } else if (inputStringArray[1].matches(TAG_PATTERN)) {
            tag = inputStringArray[1];
        } else {
            throw new GedcomParserException(GedcomParserErrors.TAG_OR_ID_NOT_PRESENT);
        }

        if (inputStringArray.length == 3) {
            data = inputStringArray[2];
        }

        DataNode node = new DataNode(level, id, tag, data);
        return node;
    }

}
