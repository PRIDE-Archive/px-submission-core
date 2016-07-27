package uk.ac.ebi.pride.data.mztab.parser.readers;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab.parser.readers
 * Timestamp: 2016-07-26 14:28
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 */
@RunWith(Parameterized.class)
public class GenericFilePositionAndLineTest {
    private long expectedPosition;
    private long expectedLineNumber;
    private String expectedLineStart;
    private int readNLines;
    private String fileName;
    @Rule
    public ExpectedException exception = ExpectedException.none();

    public GenericFilePositionAndLineTest(long expectedPosition, long expectedLineNumber, String expectedLineStart,
                                          int readNLines, String fileName) {
        this.expectedPosition = expectedPosition;
        this.expectedLineNumber = expectedLineNumber;
        this.expectedLineStart = expectedLineStart;
        this.readNLines = readNLines;
        this.fileName = fileName;
    }

    private String getTestFilePath(String fileName) throws URISyntaxException {
        return Paths.get(Paths.get(this.getClass().getClassLoader().getResource("sample_data").toURI()).toAbsolutePath().toString()
                + "/"
                + fileName).toString();
    }

    private void doRunDetectionTest(LineAndPositionAwareBufferedReader reader) throws IOException, URISyntaxException {
        LineAndPositionAwareBufferedReader.PositionAwareLine positionAwareLine = null;
        int linesToRead = readNLines;
        while (linesToRead > 0) {
            positionAwareLine = reader.readLine();
            linesToRead--;
        }
        assertEquals("Line number for file '" + fileName + "'", readNLines, positionAwareLine.getLineNo());
        assertEquals("Position for file '" + fileName + "'", expectedPosition, positionAwareLine.getOffset());
        assertThat(positionAwareLine.getLine(), CoreMatchers.startsWith(expectedLineStart));
    }


    @Test
    public void detectLineAndPositionUsingFileObject() throws IOException, URISyntaxException {
        LineAndPositionAwareBufferedReader reader = new LineAndPositionAwareBufferedReader(new File(getTestFilePath(fileName)));
        doRunDetectionTest(reader);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {0, 1, "", 1, "unix_position_test"},
                {1, 2, "FIRST", 2, "unix_position_test"},
                {7, 3, "SECOND", 3, "unix_position_test"},
                {15, 3, "LAST", 5, "unix_position_test"},
                {0, 1, "", 1, "windows_position_test"},
                {2, 2, "FIRST", 2, "windows_position_test"},
                {9, 3, "SECOND", 3, "windows_position_test"},
                {19, 3, "LAST", 5, "windows_position_test"},
                {0, 1, "", 1, "unix_position_test.gz"},
                {1, 2, "FIRST", 2, "unix_position_test.gz"},
                {7, 3, "SECOND", 3, "unix_position_test.gz"},
                {15, 3, "LAST", 5, "unix_position_test.gz"},
                {0, 1, "", 1, "windows_position_test.gz"},
                {2, 2, "FIRST", 2, "windows_position_test.gz"},
                {9, 3, "SECOND", 3, "windows_position_test.gz"},
                {19, 3, "LAST", 5, "windows_position_test.gz"},
                {0, 1, "", 1, "unix_position_test.zip"},
                {1, 2, "FIRST", 2, "unix_position_test.zip"},
                {7, 3, "SECOND", 3, "unix_position_test.zip"},
                {15, 3, "LAST", 5, "unix_position_test.zip"},
                {0, 1, "", 1, "windows_position_test.zip"},
                {2, 2, "FIRST", 2, "windows_position_test.zip"},
                {9, 3, "SECOND", 3, "windows_position_test.zip"},
                {19, 3, "LAST", 5, "windows_position_test.zip"}
        });
    }
}
