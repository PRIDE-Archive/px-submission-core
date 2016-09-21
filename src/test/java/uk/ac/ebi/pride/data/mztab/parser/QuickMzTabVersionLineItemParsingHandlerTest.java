package uk.ac.ebi.pride.data.mztab.parser;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import uk.ac.ebi.pride.data.mztab.model.MetaData;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab.parser
 * Timestamp: 2016-06-16 12:06
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 */
public class QuickMzTabVersionLineItemParsingHandlerTest {
    private static MzTabParser context;
    private LineItemParsingHandler subject;

    @BeforeClass
    public static void initDummies() {
        context = new DummyMzTabParser("dummyfile.txt");
    }

    @Before
    public void init() {
        subject = new QuickMzTabVersionLineItemParsingHandler();
    }

    @Test
    public void returnFalseOnEmptyLine() {
        assertFalse("Return false when handling an empty line", subject.parseLine(context, "", 1, 0));
    }

    @Test
    public void returnFalseWhenInvalidKeyword() {
        assertFalse("Return false when invalid keyword", subject.parseLine(context, "MTD\tikjshdfksjhfd\tGarbage information", 1, 0));
    }

    @Test
    public void verifyVersionMatches() {
        MzTabParser context = Mockito.mock(DummyMzTabParser.class);
        MetaData metaData = Mockito.mock(MetaData.class);
        String versionValue = "1.0.0";
        String mzTabVersionLine = "MTD\tmzTab-version\t" + versionValue;
        when(context.getMetaDataSection()).thenReturn(metaData);
        subject.parseLine(context, mzTabVersionLine, 1, 0);
        verify(context, atLeastOnce()).getMetaDataSection();
        verify(metaData, times(1)).getVersion();
        verify(metaData, times(1)).setVersion(versionValue);
    }
}