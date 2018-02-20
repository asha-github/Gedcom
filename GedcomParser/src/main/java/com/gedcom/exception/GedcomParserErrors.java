package com.gedcom.exception;

/**
 * Enum representing the error constants
 * 
 * @author ashas
 *
 */
public enum GedcomParserErrors {
    INVALID_DATA_FORMAT("Input data is not in valid format."), INPUT_FILE_READING_FAILED(
            "Input file couldn't be read."), LEVEL_INFO_NOT_FOUND(
                    "Level information not available. It is mandatory for every entry."), TAG_OR_ID_NOT_PRESENT(
                            "Tag or Id information is not available. It is mandatory for every entry."), OUTPUT_FILE_WRITING_FAILED(
                                    "Output file writing failed."), DATA_CONVERSION_FAILED(
                                            "Data Conversion failed"), DATA_READER_NOT_FOUND(
                                                    "Data reader for expected format not available"), DATA_WRITER_NOT_FOUND(
                                                            "Data writer for expected format not available"), OUTPUT_FILE_CREATION_FAILED(
                                                                    "Output file creation failed");

    private final String errorMessage;

    GedcomParserErrors(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
