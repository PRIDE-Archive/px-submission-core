package uk.ac.ebi.pride.data.mztab.parser;

import uk.ac.ebi.pride.data.mztab.parser.exceptions.LineItemParsingHandlerException;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab.parser
 * Timestamp: 2016-06-22 16:55
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 */
public abstract class MzTabSampleCustomLineItemParsingHandler extends MzTabSampleLineItemParsingHandler {
    protected static final String MZTAB_SAMPLE_CUSTOM_PROPERTY_KEY = "custom";

    @Override
    protected boolean processEntry(MzTabParser context, long lineNumber, long offset) throws LineItemParsingHandlerException {
        if (getPropertyKey().equals(MZTAB_SAMPLE_CUSTOM_PROPERTY_KEY)) {
            if (getPropertyEntryIndex() == DEFAULT_PROPERTY_ENTRY_INDEX) {
                throw new LineItemParsingHandlerException("Missing index for property key '" + MZTAB_SAMPLE_CUSTOM_PROPERTY_KEY + "'");
            }
            return doProcessEntry(context, lineNumber, offset);
        }
        return false;
    }

    // Delegate processing strategy
    protected abstract boolean doProcessEntry(MzTabParser context, long lineNumber, long offset) throws LineItemParsingHandlerException;
}
