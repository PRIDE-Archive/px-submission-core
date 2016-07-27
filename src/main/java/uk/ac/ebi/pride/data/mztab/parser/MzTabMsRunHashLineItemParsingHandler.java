package uk.ac.ebi.pride.data.mztab.parser;

import uk.ac.ebi.pride.data.mztab.parser.exceptions.LineItemParsingHandlerException;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab.parser
 * Timestamp: 2016-07-07 22:25
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 */
public abstract class MzTabMsRunHashLineItemParsingHandler extends MzTabMsRunLineItemParsingHandler {
    protected static final String MZTAB_MS_RUN_HASH_PROPERTY_KEY = "hash";

    @Override
    protected boolean processEntry(MzTabParser context, long lineNumber, long offset) throws LineItemParsingHandlerException {
        if (getPropertyKey().equals(MZTAB_MS_RUN_HASH_PROPERTY_KEY)) {
            return doProcessEntry(context, lineNumber, offset);
        }
        return false;
    }

    // Delegate processing
    protected abstract boolean doProcessEntry(MzTabParser context, long lineNumber, long offset) throws LineItemParsingHandlerException;
}
