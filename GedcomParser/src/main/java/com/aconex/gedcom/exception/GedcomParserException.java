package com.aconex.gedcom.exception;

/**
 * Class to handle all the exceptions that can come during parsing
 * 
 * @author ashas
 *
 */
public class GedcomParserException extends Exception {

    private static final long serialVersionUID = 1L;

    private GedcomParserErrors parserError;

    public GedcomParserException(GedcomParserErrors parserError) {
        this.parserError = parserError;
    }

    /**
     * API to get the error message associated with the exception
     * 
     * @return
     */
    public String getErrorMessage() {
        return parserError.getErrorMessage();
    }
}
