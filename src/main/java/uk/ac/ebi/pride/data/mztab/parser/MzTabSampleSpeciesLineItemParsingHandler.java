package uk.ac.ebi.pride.data.mztab.parser;

import uk.ac.ebi.pride.data.mztab.parser.exceptions.LineItemParsingHandlerException;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab.parser
 * Timestamp: 2016-06-22 16:40
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 */
public abstract class MzTabSampleSpeciesLineItemParsingHandler extends MzTabSampleLineItemParsingHandler {
    protected static final String MZTAB_SAMPLE_SPECIES_PROPERTY_KEY = "species";

    @Override
    protected boolean processEntry(MzTabParser context, long lineNumber, long offset) throws LineItemParsingHandlerException {
        if (getPropertyKey().equals(MZTAB_SAMPLE_SPECIES_PROPERTY_KEY)) {
            // Check that we have indexes for species property key
            if (getPropertyEntryIndex() == DEFAULT_PROPERTY_ENTRY_INDEX) {
                throw new LineItemParsingHandlerException("Missing index for property key '" + MZTAB_SAMPLE_SPECIES_PROPERTY_KEY + "'");
            }

            return doProcessEntry(context, lineNumber, offset);
        }
        return false;
    }

    // Delegate processing strategy
    protected abstract boolean doProcessEntry(MzTabParser context, long lineNumber, long offset) throws LineItemParsingHandlerException ;
}
