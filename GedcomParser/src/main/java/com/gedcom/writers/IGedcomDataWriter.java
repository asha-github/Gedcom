package com.gedcom.writers;

import com.gedcom.core.DataNode;
import com.gedcom.exception.GedcomParserException;

/**
 * Interface definition for gedcom output file writer
 * 
 * @author ashas
 *
 */
public interface IGedcomDataWriter {

    /**
     * API to convert gedcom file to required format
     * 
     * @param rootNode
     *            - root node of the data tree created out of gedcom input file
     * @throws GedcomParserException
     */
    public void convert(String outputFilePath, DataNode rootNode) throws GedcomParserException;

}
