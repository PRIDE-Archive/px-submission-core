package uk.ac.ebi.pride.data.mztab.parser;

import uk.ac.ebi.pride.data.mztab.parser.exceptions.LineItemParsingHandlerException;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab.parser
 * Timestamp: 2016-06-29 12:24
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 */
public class QuickProteinDataEntryLineItemParsingHandler extends ProteinDataEntryLineItemParsingHandler {
    @Override
    protected boolean doProcessDataEntryLine(MzTabParser context, String[] parsedColumnEntries, long lineNumber, long offset) throws LineItemParsingHandlerException {
        return parsedColumnEntries.length == context.getProteinDataSection().getNumberOfColumns();
    }
}
