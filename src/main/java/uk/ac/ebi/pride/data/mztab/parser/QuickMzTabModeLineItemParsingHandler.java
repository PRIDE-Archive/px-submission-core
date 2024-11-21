package uk.ac.ebi.pride.data.mztab.parser;

import lombok.extern.slf4j.Slf4j;
import uk.ac.ebi.pride.data.mztab.model.MetaData;
import uk.ac.ebi.pride.data.mztab.parser.exceptions.LineItemParsingHandlerException;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab.parser
 * Timestamp: 2016-06-14 16:27
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 *
 * This strategy processes the mzTab-mode by incorporating it in its corresponding subproduct
 */

@Slf4j
public class QuickMzTabModeLineItemParsingHandler extends MzTabModeLineItemParsingHandler {
    private void checkForModeDuplication(MzTabParser context, String line, long lineNumber, long offset) throws LineItemParsingHandlerException {
        if (context.getMetaDataSection().getMode() != null) {
            throw new LineItemParsingHandlerException("DUPLICATED '" + MZTAB_MODE_KEYWORD + "' entry found!, line number '" + lineNumber + "'");
        }
    }

    @Override
    protected boolean doProcessMode(MzTabParser context, String line, long lineNumber, long offset, MetaData.MzTabMode mode) throws LineItemParsingHandlerException {
        checkForModeDuplication(context, line, lineNumber, offset);
        context.getMetaDataSection().setMode(mode);
        return true;
    }
}
