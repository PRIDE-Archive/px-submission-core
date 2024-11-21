package uk.ac.ebi.pride.data.mztab.parser;

import lombok.extern.slf4j.Slf4j;
import uk.ac.ebi.pride.data.mztab.parser.exceptions.LineItemParsingHandlerException;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab.parser
 * Timestamp: 2016-06-16 12:46
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 *
 * Quick processing strategy for mzTab title
 */
@Slf4j
public class QuickMzTabTitleLineItemParsingHandler extends MzTabTitleLineItemParsingHandler {
    private void checkForDuplicatedTitle(MzTabParser context, long lineNumber) throws LineItemParsingHandlerException {
        if (context.getMetaDataSection().getTitle() != null) {
            throw new LineItemParsingHandlerException("DUPLICATED " + MZTAB_TITLE_KEYWORD + " found at line " + lineNumber);
        }
    }

    @Override
    protected boolean doProcessTitle(MzTabParser context, String line, long lineNumber, long offset, String title) throws LineItemParsingHandlerException {
        checkForDuplicatedTitle(context, lineNumber);
        context.getMetaDataSection().setTitle(title);
        return true;
    }
}
