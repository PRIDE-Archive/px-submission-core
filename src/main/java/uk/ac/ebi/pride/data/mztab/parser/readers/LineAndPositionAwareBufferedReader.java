package uk.ac.ebi.pride.data.mztab.parser.readers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.pride.data.util.FileUtil;

import java.io.*;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab.parser.readers
 * Timestamp: 2016-06-13 11:16
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 *
 * This class implements an Extension of a BufferedReader via Delegation that keeps track of the line number, and the position of the
 * Reader that corresponds to the line currently read
 */

public class LineAndPositionAwareBufferedReader {
    private static final Logger logger = LoggerFactory.getLogger(LineAndPositionAwareBufferedReader.class);

    // Offset
    private long offset = 0;
    // Reader
    private LineNumberReader reader;
    // File Name
    private String fileName = "";
    // How many characters to use for new line
    private int ncrlf = 1;  // Default = 1

    public class PositionAwareLine {
        private long lineNo = 0;
        private long offset = 0;
        private String line = "";

        public PositionAwareLine(long lineNo, long offset, String line) {
            this.lineNo = lineNo;
            this.offset = offset;
            this.line = line;
        }

        public long getLineNo() {
            return lineNo;
        }

        public long getOffset() {
            return offset;
        }

        public String getLine() {
            return line;
        }
    }

    public static int howManyCrlfChars(String fileName) throws IOException {
        logger.debug("Detecting line break type in the file '" + fileName + "'");
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(fileName));
        return howManyCrlfChars(in);
    }

    public static int howManyCrlfChars(InputStream in) throws IOException {
        try {
            int b = 0;
            while ((b = in.read()) != -1) {
                char c = (char) b;
                //logger.debug("Character: '" + String.valueOf(c) + "'");
                if (c == '\r') {
                    b = in.read();
                    if (b != -1) {
                        c = (char) b;
                        if (c == '\n') {
                            logger.debug("CRLF Found!");
                            return 2;
                        }
                    }
                    logger.debug("CR Found!");
                    return 1;
                } else if (c == '\n') {
                    logger.debug("LF Found!");
                    return 1;
                }
            }
            logger.error("Line break type could not be identified !!");
        } finally {
            in.close();
        }
        return -1;
    }

    public LineAndPositionAwareBufferedReader(String fileName) throws IOException {
        ncrlf = howManyCrlfChars(fileName);
        logger.debug("Creating reader for file '" + fileName + "'");
        //reader = new LineNumberReader(new FileReader(fileName));
        // TODO - To my future self: if, at any time in the future, you need to refactor the parser out of the
        // TODO - submission tool, keep in mind this coupling point, where we use FileUtil getFileInputStream method
        // TODO - for opening an InputStream
        reader = new LineNumberReader(new InputStreamReader(FileUtil.getFileInputStream(new File(fileName))));
    }

    public LineAndPositionAwareBufferedReader(File file) throws IOException {
        ncrlf = howManyCrlfChars(FileUtil.getFileInputStream(file));
        logger.debug("Creating reader for file '" + file.getName() + "'");
        reader = new LineNumberReader(new InputStreamReader(FileUtil.getFileInputStream(file)));
    }

    /**
     * Read a line with offset and line number information
     * @return line number, offset and the content of the line if any, null if it reached the end of the input stream
     * @throws IOException
     */
    public PositionAwareLine readLine() throws IOException {
        String line = reader.readLine();
        //logger.debug("Line read '" + line + "', position '" + offset + "', line number '" + reader.getLineNumber() + "'");
        PositionAwareLine readLine = null;
        if (line != null) {
            readLine = new PositionAwareLine(reader.getLineNumber(), offset, line);
            offset += line.getBytes().length + ncrlf;
        }
        return readLine;
    }
}
