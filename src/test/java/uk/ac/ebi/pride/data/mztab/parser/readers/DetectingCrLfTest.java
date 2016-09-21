package uk.ac.ebi.pride.data.mztab.parser.readers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab.parser.readers
 * Timestamp: 2016-06-13 11:54
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 * <p>
 * Bulk test for the detection of CRLF characters used on files
 */
@RunWith(Parameterized.class)
public class DetectingCrLfTest {

    private int expected;
    private String fileName;

    public DetectingCrLfTest(int expected, String fileName) {
        this.expected = expected;
        this.fileName = fileName;
    }

    private String getTestFilePath(String fileName) throws URISyntaxException {
        return Paths.get(Paths.get(this.getClass().getClassLoader().getResource("sample_data").toURI()).toAbsolutePath().toString()
                + "/"
                + fileName).toString();
    }

    @Test
    public void testNewLineCharacterDetectionUsingFileName() throws Exception {
        assertEquals("Number of characters used for new line in file '" + fileName + "'", expected, LineAndPositionAwareBufferedReader.howManyCrlfChars(getTestFilePath(fileName)));
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {1, "no_new_line"},
                {2, "windows_file"},
                {1, "unix_file"}
        });
    }
}