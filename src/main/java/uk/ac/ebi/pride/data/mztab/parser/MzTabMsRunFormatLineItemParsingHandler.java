package uk.ac.ebi.pride.data.mztab.parser;

import lombok.extern.slf4j.Slf4j;
import uk.ac.ebi.pride.data.mztab.parser.exceptions.LineItemParsingHandlerException;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab.parser
 * Timestamp: 2016-06-21 11:10
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 *
 * It checks the ms-run entry to be of type format
 *
 * It delegates further processing of its data
 */

@Slf4j
public abstract class MzTabMsRunFormatLineItemParsingHandler extends MzTabMsRunLineItemParsingHandler {
    protected static final String MZTAB_MSRUN_FORMAT_PROPERTY_KEY = "format";

    @Override
    protected boolean processEntry(MzTabParser context, long lineNumber, long offset)  throws LineItemParsingHandlerException {
        if (getPropertyKey().equals(MZTAB_MSRUN_FORMAT_PROPERTY_KEY)) {
            return doProcessEntry(context, lineNumber, offset);
        }
        log.debug("Found property key '" + getPropertyKey() + "' but this parser is expecting '" + MZTAB_MSRUN_FORMAT_PROPERTY_KEY + "'");
        return false;
    }

    // Delegate to strategy subclasses what to do
    protected abstract boolean doProcessEntry(MzTabParser context, long lineNumber, long offset) throws LineItemParsingHandlerException ;
}
