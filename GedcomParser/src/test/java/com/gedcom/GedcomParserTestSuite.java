package com.gedcom;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.gedcom.core.DataNodeTest;
import com.gedcom.core.DataStoreTest;
import com.gedcom.core.GedcomParserTest;
import com.gedcom.readers.TextDataReaderTest;
import com.gedcom.writers.XMLDataWriterTest;

@RunWith(Suite.class)
@SuiteClasses({ DataStoreTest.class, TextDataReaderTest.class, XMLDataWriterTest.class, DataNodeTest.class,
        GedcomParserTest.class })
public class GedcomParserTestSuite {

}
