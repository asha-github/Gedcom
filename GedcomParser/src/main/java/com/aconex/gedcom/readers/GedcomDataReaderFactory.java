package com.aconex.gedcom.readers;

/**
 * Factory class for the creation of data reader objects
 * 
 * @author ashas
 *
 */
public class GedcomDataReaderFactory {
    /**
     * Enum representing different input format types
     */
    public enum INPUT_FORMAT {
        TEXT_FORMAT
    }

    /**
     * Method to get the required data reader.
     * 
     * @param dataFormat
     * @return
     */
    public static IGedcomDataReader getDataReader(INPUT_FORMAT dataFormat) {
        IGedcomDataReader dataReader = null;
        switch (dataFormat) {
        case TEXT_FORMAT:
            dataReader = new TextDataReader();
        default:
            break;
        }
        return dataReader;
    }
}
