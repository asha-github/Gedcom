package com.gedcom.writers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.gedcom.core.DataNode;
import com.gedcom.exception.GedcomParserErrors;
import com.gedcom.exception.GedcomParserException;

/**
 * Class responsible for generating XML for the Gedcom data provided in tree
 * form.
 * 
 * @author ashas
 *
 */
public class XMLDataWriter implements IGedcomDataWriter {
    private static final Logger logger = Logger.getLogger(XMLDataWriter.class.getSimpleName());
    private final static String XML_ID_ENTRY = "id";
    private final static String XML_VALUE_ENTRY = "value";

    /**
     * API to convert Gedcom file to XML format
     */
    @Override
    public void convert(String outputFilePath, DataNode rootNode) throws GedcomParserException {
        logger.log(Level.INFO, "Conversion to XML process started.");
        XMLStreamWriter writer = null;
        try {
            File outputFile = createFile(outputFilePath);
            writer = XMLOutputFactory.newInstance()
                    .createXMLStreamWriter(new BufferedOutputStream(new FileOutputStream(outputFile)), "UTF-8");
            writer.writeStartDocument();
            convertToXML(writer, rootNode);
            writer.writeEndDocument();
            writer.flush();
            logger.log(Level.INFO, "XML conversion process completed.");
        } catch (IOException e) {
            throw new GedcomParserException(GedcomParserErrors.OUTPUT_FILE_WRITING_FAILED);
        } catch (XMLStreamException e) {
            throw new GedcomParserException(GedcomParserErrors.DATA_CONVERSION_FAILED);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (XMLStreamException e) {
                throw new GedcomParserException(GedcomParserErrors.DATA_CONVERSION_FAILED);
            }
        }
    }

    /**
     * Method handling the creation of file
     * 
     * @param filePath
     * @return
     * @throws GedcomParserException
     */
    private File createFile(String filePath) throws GedcomParserException {
        logger.log(Level.INFO, "Creating output XML file.");
        File outputFile = new File(filePath);
        if (outputFile.exists()) {
            outputFile.delete();
        }
        try {
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }
            outputFile.createNewFile();
        } catch (IOException e) {
            throw new GedcomParserException(GedcomParserErrors.OUTPUT_FILE_CREATION_FAILED);
        }
        return outputFile;
    }

    /**
     * Method responsibilities :- 1. Traverse the data tree starting from root
     * node covering all nodes in order. 2. Write data of each node to output
     * XML.
     * 
     * @param streamWriter
     * @param node
     *            - root node of the data tree
     */
    private void convertToXML(XMLStreamWriter streamWriter, DataNode node) throws GedcomParserException {
        try {
            formatFields(streamWriter, node);
            List<DataNode> children = node.getChildren();
            if (children != null) {
                for (DataNode child : children) {
                    convertToXML(streamWriter, child);
                }
            }
            streamWriter.writeEndElement();
        } catch (XMLStreamException ex) {
            throw new GedcomParserException(GedcomParserErrors.DATA_CONVERSION_FAILED);
        }
    }

    /**
     * Method responsible for formatting the data in XML format. Formatting
     * logic is as follows. Step 1: If both ID and tag is not present, a new XML
     * tag is started with data valie. This is expected for root element GEDCOM.
     * Otherwise, a new XML tag is started with the tag element value. Step 2:
     * If id is present, then write the id as an attribute entry. Step 3: If
     * leaf node, then write the data value as characters. Step 4: If not leaf
     * node and if data is present, then write the data value as a value
     * attribute.
     * 
     * @param writer
     * @param node
     * @throws XMLStreamException
     */
    private void formatFields(XMLStreamWriter streamWriter, DataNode node) throws XMLStreamException {
        logger.log(Level.FINE, "Converting data -> "
                + (node.getLevel() + " - " + node.getId() + " - " + node.getTag() + " - " + node.getData()));
        if (node.getId() == null && node.getTag() == null && node.isDataAvailable()) {
            streamWriter.writeStartElement(node.getData());
        } else if (node.getTag() != null) {
            streamWriter.writeStartElement(node.getTag());
        }
        if (node.getId() != null) {
            streamWriter.writeAttribute(XML_ID_ENTRY, node.getId());
        } else if (node.isLeafNode() && node.isDataAvailable()) {
            streamWriter.writeCharacters(node.getData());
        } else if (!node.isLeafNode() && node.isDataAvailable()) {
            streamWriter.writeAttribute(XML_VALUE_ENTRY, node.getData());
        }
    }

}
