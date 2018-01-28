package com.aconex.gedcom.writers;

/**
 * Factory class for the creation of data writer objects
 * 
 * @author ashas
 *
 */
public class GedcomDataWriterFactory {
    /**
     * Enum representing different output format types
     */
    public enum OUTPUT_FORMAT {
        XML_FORMAT
    }

    /**
     * Method to get the required writer for converting data to required format.
     * 
     * @param dataFormat
     * @return
     */
    public static IGedcomDataWriter getDataWriter(OUTPUT_FORMAT dataFormat) {
        IGedcomDataWriter dataWriter = null;
        switch (dataFormat) {
        case XML_FORMAT:
            dataWriter = new XMLDataWriter();
        default:
            break;
        }
        return dataWriter;
    }
}
