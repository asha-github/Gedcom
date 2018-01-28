package com.aconex.gedcom.readers;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.internal.WhiteboxImpl;

import com.aconex.gedcom.core.DataNode;
import com.aconex.gedcom.core.DataStore;
import com.aconex.gedcom.exception.GedcomParserErrors;
import com.aconex.gedcom.exception.GedcomParserException;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DataStore.class)
public class TextDataReaderTest {
    private TextDataReader reader = new TextDataReader();

    @Test
    public void test_InputFileMissing() throws Exception {
        Throwable exception = null;
        try {
            reader.parseDataFile("NonExistentFile.txt");
        } catch (Throwable ex) {
            exception = ex;
        }
        Assert.assertTrue(exception instanceof GedcomParserException);
        Assert.assertEquals(((GedcomParserException) exception).getErrorMessage(),
                GedcomParserErrors.INPUT_FILE_READING_FAILED.getErrorMessage());
    }

    @Test
    public void test_ParsingValidInputFile() throws Exception {
        String filePath = "src/test/resources/Input_ValidData.txt";
        PowerMockito.mockStatic(DataStore.class);
        DataStore mockDataStore = Mockito.mock(DataStore.class);
        Mockito.when(DataStore.getDataStore()).thenReturn(mockDataStore);

        Throwable exception = null;
        try {
            reader.parseDataFile(filePath);
        } catch (Throwable ex) {
            exception = ex;
        }
        // Verification starts
        long totalNumOfEntriesExpected = 0;
        try (Stream<String> lines = Files.lines(Paths.get(filePath), Charset.defaultCharset())) {
            totalNumOfEntriesExpected = lines.count();
        }
        Assert.assertFalse(exception instanceof GedcomParserException);
        Mockito.verify(mockDataStore, Mockito.times((int) totalNumOfEntriesExpected))
                .addNode(Mockito.any(DataNode.class));
    }

    @Test
    public void test_ParsingValidInputFileWithBlankLines() throws Exception {
        String filePath = "src/test/resources/Input_ValidDataWithBlankLines.txt";
        PowerMockito.mockStatic(DataStore.class);
        DataStore mockDataStore = Mockito.mock(DataStore.class);
        Mockito.when(DataStore.getDataStore()).thenReturn(mockDataStore);

        Throwable exception = null;
        try {
            reader.parseDataFile(filePath);
        } catch (Throwable ex) {
            exception = ex;
        }

        // Verification starts
        long totalNumOfEntriesExpected = 0;
        try (Stream<String> lines = Files.lines(Paths.get(filePath), Charset.defaultCharset())) {
            totalNumOfEntriesExpected = lines.filter(input -> input != null && !input.isEmpty()).count();
        }
        Assert.assertFalse(exception instanceof GedcomParserException);
        Mockito.verify(mockDataStore, Mockito.times((int) totalNumOfEntriesExpected))
                .addNode(Mockito.any(DataNode.class));
    }

    @Test
    public void test_ParsingIdEntry() throws Exception {
        String inputData = "0 @I1@ INDI";
        DataNode node = WhiteboxImpl.invokeMethod(reader, "getDataNode", new String(inputData));

        Assert.assertEquals(node.getId(), "@I1@");
        Assert.assertEquals(node.getLevel(), 0);
        Assert.assertEquals(node.getTag(), "INDI");
    }

    @Test
    public void test_ParsingTagEntry() throws Exception {
        String inputData = "1 NAME Jamis Gordon /Buck/";
        DataNode node = WhiteboxImpl.invokeMethod(reader, "getDataNode", new String(inputData));

        Assert.assertEquals(node.getId(), null);
        Assert.assertEquals(node.getLevel(), 1);
        Assert.assertEquals(node.getTag(), "NAME");
        Assert.assertEquals(node.getData(), "Jamis Gordon /Buck/");
    }

    @Test
    public void test_ParsingTagEntryWithExtraSpaces() throws Exception {
        String inputData = "2     SURN    Buck";
        DataNode node = WhiteboxImpl.invokeMethod(reader, "getDataNode", new String(inputData));

        Assert.assertEquals(node.getId(), null);
        Assert.assertEquals(node.getLevel(), 2);
        Assert.assertEquals(node.getTag(), "SURN");
        Assert.assertEquals(node.getData(), "Buck");
    }

    @Test
    public void test_TagAndIDMissing() throws Exception {
        String inputData = "0 TAG_OR_ID_NOT_PRESENT";
        Throwable exception = null;
        try {
            WhiteboxImpl.invokeMethod(reader, "getDataNode", new String(inputData));
        } catch (Throwable ex) {
            exception = ex;
        }
        Assert.assertTrue(exception instanceof GedcomParserException);
        Assert.assertEquals(((GedcomParserException) exception).getErrorMessage(),
                GedcomParserErrors.TAG_OR_ID_NOT_PRESENT.getErrorMessage());
    }

    @Test
    public void test_LevelInfoMissing() throws Exception {
        Throwable exception = null;
        String inputData = "NAME Jamis Gordon /Buck/";
        try {
            WhiteboxImpl.invokeMethod(reader, "getDataNode", new String(inputData));
        } catch (Throwable ex) {
            exception = ex;
        }
        Assert.assertTrue(exception instanceof GedcomParserException);
        Assert.assertEquals(((GedcomParserException) exception).getErrorMessage(),
                GedcomParserErrors.LEVEL_INFO_NOT_FOUND.getErrorMessage());
    }

    @Test
    public void test_TagInLowercase() throws Exception {
        String inputData = "1 name Jamis Gordon /Buck/";
        Throwable exception = null;
        try {
            WhiteboxImpl.invokeMethod(reader, "getDataNode", new String(inputData));
        } catch (Throwable ex) {
            exception = ex;
        }
        Assert.assertTrue(exception instanceof GedcomParserException);
        Assert.assertEquals(((GedcomParserException) exception).getErrorMessage(),
                GedcomParserErrors.TAG_OR_ID_NOT_PRESENT.getErrorMessage());
    }

    /**
     * Test to very regular expression matching of tag entry. If @ symbol is not
     * present at the end, it wont be treated as tag
     * 
     * @throws Exception
     */
    @Test
    public void test_TagWithoutEndSymbol() throws Exception {
        String inputData = "1 @123 Tag without @ at end";
        Throwable exception = null;
        try {
            WhiteboxImpl.invokeMethod(reader, "getDataNode", new String(inputData));
        } catch (Throwable ex) {
            exception = ex;
        }
        Assert.assertTrue(exception instanceof GedcomParserException);
        Assert.assertEquals(((GedcomParserException) exception).getErrorMessage(),
                GedcomParserErrors.TAG_OR_ID_NOT_PRESENT.getErrorMessage());
    }

    /**
     * Test to very regular expression matching of tag entry.
     * 
     * @throws Exception
     */
    @Test
    public void test_IDWithoutSymbol() throws Exception {
        String inputData = "0 1234";
        Throwable exception = null;
        try {
            WhiteboxImpl.invokeMethod(reader, "getDataNode", new String(inputData));
        } catch (Throwable ex) {
            exception = ex;
        }
        Assert.assertTrue(exception instanceof GedcomParserException);
        Assert.assertEquals(((GedcomParserException) exception).getErrorMessage(),
                GedcomParserErrors.TAG_OR_ID_NOT_PRESENT.getErrorMessage());
    }

}
