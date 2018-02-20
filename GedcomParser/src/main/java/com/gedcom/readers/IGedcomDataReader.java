package com.gedcom.readers;

import com.gedcom.core.DataNode;
import com.gedcom.exception.GedcomParserException;

/**
 * Interface definition for gedcom file reader
 * 
 * @author ashas
 *
 */
public interface IGedcomDataReader {
    /**
     * Regex pattern to identify ID entry
     */
    public final static String ID_PATTERN = "@.*@";
    /**
     * Regex pattern to identify TAG entry
     */
    public final static String TAG_PATTERN = "[A-Z]{3,4}";

    /**
     * API to parse the gedcom fle
     * 
     * @param file
     *            - input file
     * @return root node of the data tree created out of gedcom input file
     * @throws GedcomParserException
     */
    public DataNode parseDataFile(String file) throws GedcomParserException;

}
