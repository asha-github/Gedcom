package com.aconex.gedcom.writers;

import java.io.BufferedOutputStream;
import java.io.File;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.aconex.gedcom.core.DataNode;

@RunWith(PowerMockRunner.class)
@PrepareForTest(XMLOutputFactory.class)
public class XMLDataWriterTest {
    private XMLDataWriter writer = new XMLDataWriter();
    private XMLStreamWriter mockXmlStreamWriter = null;

    @Before
    public void init() throws XMLStreamException {
        PowerMockito.mockStatic(XMLOutputFactory.class);
        XMLOutputFactory xmlFactory = Mockito.mock(XMLOutputFactory.class);
        mockXmlStreamWriter = Mockito.mock(XMLStreamWriter.class);
        Mockito.when(xmlFactory.createXMLStreamWriter((BufferedOutputStream) Mockito.anyObject(), Mockito.anyObject()))
                .thenReturn(mockXmlStreamWriter);
        Mockito.when(XMLOutputFactory.newInstance()).thenReturn(xmlFactory);
    }

    /**
     * Test input content
     * 
     * 0 @I1@ INDI 1 NAME Jamis Gordon 1 SEX M
     * 
     * @throws Exception
     */
    @Test
    public void test_XMLDataCreationWithOnlyOneLevel() throws Exception {
        String filePath = "src/test/resources/testXML.xml";
        DataNode root = new DataNode(0, "@I1@", "INDI", "");
        root.addChild(new DataNode(1, null, "NAME", "Jamis Gordon"));
        root.addChild(new DataNode(1, null, "SEX", "M"));

        writer.convert(filePath, root);

        Mockito.verify(mockXmlStreamWriter, Mockito.times(3)).writeStartElement(Mockito.anyObject());
        Mockito.verify(mockXmlStreamWriter, Mockito.times(1)).writeStartElement("INDI");
        Mockito.verify(mockXmlStreamWriter, Mockito.times(1)).writeAttribute("id", "@I1@");
        Mockito.verify(mockXmlStreamWriter, Mockito.times(1)).writeStartElement("NAME");
        Mockito.verify(mockXmlStreamWriter, Mockito.times(1)).writeCharacters("Jamis Gordon");
        Mockito.verify(mockXmlStreamWriter, Mockito.times(1)).writeStartElement("SEX");
        Mockito.verify(mockXmlStreamWriter, Mockito.times(1)).writeCharacters("M");
        Mockito.verify(mockXmlStreamWriter, Mockito.times(3)).writeEndElement();
        File file = new File(filePath);
        Assert.assertTrue(file.exists());

        // cleanup
        file.delete();

    }

    /**
     * Test to verify xml creation when multiple levels of data is there
     * 
     * @throws Exception
     */
    @Test
    public void test_XMLDataCreationWithOnlyMultipleLevels() throws Exception {
        String filePath = "src/test/resources/testXML.xml";
        DataNode root = new DataNode(0, "@I1@", "INDI", "");
        DataNode rootchild1 = new DataNode(1, null, "NAME", "Jamis Gordon");
        DataNode child1 = new DataNode(2, null, "SURN", "Gordon");
        DataNode child2 = new DataNode(3, null, "GIVN", "Jamis");
        rootchild1.addChild(child1);
        rootchild1.addChild(child2);
        DataNode rootchild2 = new DataNode(1, null, "SEX", "M");
        root.addChild(rootchild1);
        root.addChild(rootchild2);

        writer.convert(filePath, root);

        Mockito.verify(mockXmlStreamWriter, Mockito.times(5)).writeStartElement(Mockito.anyObject());
        Mockito.verify(mockXmlStreamWriter, Mockito.times(1)).writeStartElement("INDI");
        Mockito.verify(mockXmlStreamWriter, Mockito.times(1)).writeAttribute("id", "@I1@");
        Mockito.verify(mockXmlStreamWriter, Mockito.times(1)).writeStartElement("NAME");
        Mockito.verify(mockXmlStreamWriter, Mockito.times(1)).writeAttribute("value", "Jamis Gordon");
        Mockito.verify(mockXmlStreamWriter, Mockito.times(1)).writeStartElement("SURN");
        Mockito.verify(mockXmlStreamWriter, Mockito.times(1)).writeCharacters("Gordon");
        Mockito.verify(mockXmlStreamWriter, Mockito.times(1)).writeStartElement("GIVN");
        Mockito.verify(mockXmlStreamWriter, Mockito.times(1)).writeCharacters("Jamis");
        Mockito.verify(mockXmlStreamWriter, Mockito.times(1)).writeStartElement("SEX");
        Mockito.verify(mockXmlStreamWriter, Mockito.times(1)).writeCharacters("M");
        Mockito.verify(mockXmlStreamWriter, Mockito.times(5)).writeEndElement();
        File file = new File(filePath);
        Assert.assertTrue(file.exists());

        // cleanup
        file.delete();

    }

}
