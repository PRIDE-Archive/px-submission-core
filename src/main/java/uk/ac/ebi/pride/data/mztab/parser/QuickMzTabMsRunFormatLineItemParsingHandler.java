package uk.ac.ebi.pride.data.mztab.parser;

import lombok.extern.slf4j.Slf4j;
import uk.ac.ebi.pride.data.mztab.model.MsRunFormat;
import uk.ac.ebi.pride.data.mztab.parser.exceptions.LineItemParsingHandlerException;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab.parser
 * Timestamp: 2016-06-21 11:12
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 * <p>
 * Quick processing strategy for ms-run format entries
 */

@Slf4j
public class QuickMzTabMsRunFormatLineItemParsingHandler extends MzTabMsRunFormatLineItemParsingHandler {
    // Check for duplicated entry
    private void checkForDuplicatedEntry(MzTabParser context, long lineNumber) throws LineItemParsingHandlerException {
        log.debug("Checking for duplicated entry for line " + lineNumber);
        if (getMsRunFromContext(context, getIndex()).getMsRunFormat() != null) {
            String msg = "DUPLICATED MS-Run format entry FOUND AT LINE " + lineNumber;
            log.debug(msg);
            throw new LineItemParsingHandlerException(msg);
        }
        log.debug("No duplicates found!");
    }

    @Override
    protected boolean doProcessEntry(MzTabParser context, long lineNumber, long offset) throws LineItemParsingHandlerException {
        checkForDuplicatedEntry(context, lineNumber);
        // Process the entry
        log.debug("Processing ms_run format entry for line " + lineNumber);
        getMsRunFromContext(context, getIndex()).setMsRunFormat(new MsRunFormat(CvParameterParser.fromString(getPropertyValue())));
        return true;
    }
}
