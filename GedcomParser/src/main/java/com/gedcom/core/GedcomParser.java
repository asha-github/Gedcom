package com.gedcom.core;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.gedcom.exception.GedcomParserErrors;
import com.gedcom.exception.GedcomParserException;
import com.gedcom.readers.GedcomDataReaderFactory;
import com.gedcom.readers.IGedcomDataReader;
import com.gedcom.writers.GedcomDataWriterFactory;
import com.gedcom.writers.IGedcomDataWriter;

public class GedcomParser {

    private static final Logger logger = Logger.getLogger(GedcomParser.class.getSimpleName());
    private static GedcomParser parser = null;

    private GedcomParser() {
        super();
    }

    public static GedcomParser getParser() {
        if (parser == null) {
            parser = new GedcomParser();
        }
        return parser;
    }

    /**
     * Method handling the parsing of input data and conversion to required
     * output format
     */
    public void parse(String inputFilePath, String outputFilePath) {
        logger.log(Level.INFO, "Gedcom data parsing started");
        DataNode rootNode = null;
        try {
            rootNode = getReader().parseDataFile(inputFilePath);
            if (rootNode != null) {
                getWriter().convert(outputFilePath, rootNode);
            }
        } catch (GedcomParserException e) {
            logger.log(Level.SEVERE, "Conversion didn't happen due to error.");
            logger.log(Level.SEVERE, "\n" + e.getErrorMessage() + "\n");
        }
        logger.log(Level.INFO, "Gedcom Data parsing completed.");
    }

    /**
     * Method to get the reader
     * 
     * @return
     * @throws GedcomParserException
     */
    private IGedcomDataReader getReader() throws GedcomParserException {
        IGedcomDataReader dataReader = GedcomDataReaderFactory
                .getDataReader(GedcomDataReaderFactory.INPUT_FORMAT.TEXT_FORMAT);
        if (dataReader == null) {
            throw new GedcomParserException(GedcomParserErrors.DATA_READER_NOT_FOUND);
        }
        return dataReader;
    }

    /**
     * Method to get the writer
     * 
     * @return
     * @throws GedcomParserException
     */
    private IGedcomDataWriter getWriter() throws GedcomParserException {
        IGedcomDataWriter dataWriter = GedcomDataWriterFactory
                .getDataWriter(GedcomDataWriterFactory.OUTPUT_FORMAT.XML_FORMAT);
        if (dataWriter == null) {
            throw new GedcomParserException(GedcomParserErrors.DATA_WRITER_NOT_FOUND);
        }
        return dataWriter;
    }
}
