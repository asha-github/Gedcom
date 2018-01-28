package com.aconex.gedcom;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.aconex.gedcom.core.DataStoreTest;
import com.aconex.gedcom.readers.TextDataReaderTest;
import com.aconex.gedcom.writers.XMLDataWriterTest;

@RunWith(Suite.class)
@SuiteClasses({ DataStoreTest.class, TextDataReaderTest.class, XMLDataWriterTest.class })
public class GedcomParserTestSuite {

}
