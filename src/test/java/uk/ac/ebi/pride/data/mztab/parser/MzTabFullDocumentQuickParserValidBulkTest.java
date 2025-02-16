package uk.ac.ebi.pride.data.mztab.parser;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.pride.data.mztab.model.MzTabDocument;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

/**
 * Project: px-submission-core
 * Package: uk.ac.ebi.pride.data.mztab.parser
 * Timestamp: 2016-07-28 11:51
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 *
 * Bulk testing of valid files for the full document quick parsing strategy
 */
@RunWith(Parameterized.class)
@Slf4j
public class MzTabFullDocumentQuickParserValidBulkTest {

    private String fileName = null;

    private String getTestFilePath(String fileName) throws URISyntaxException {
        return Paths.get(Paths.get(this.getClass().getClassLoader().getResource("sample_data").toURI()).toAbsolutePath().toString()
                + "/"
                + fileName).toString();
    }

    public MzTabFullDocumentQuickParserValidBulkTest(String fileName) {
        this.fileName = fileName;
    }

    @Test
    public void parseFileWithNoError() throws URISyntaxException {
        MzTabParser parser = new MzTabFullDocumentQuickParser(new File(getTestFilePath(fileName)));
        parser.parse();
        MzTabDocument doc = parser.getMzTabDocument();
        log.debug("mzTab title: " + doc.getMetaData().getTitle());
    }

    @Parameterized.Parameters
    public static Collection<Object[]> validFilesToTest() {
        return Arrays.asList(new Object[][]{
                {"valid2.mzTab"},
                {"valid1.mzTab.gz"},
                {"valid3_with_null_location.mzTab"}
        });
    }
}