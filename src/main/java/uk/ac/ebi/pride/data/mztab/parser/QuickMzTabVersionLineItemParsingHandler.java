package uk.ac.ebi.pride.data.mztab.parser;

import lombok.extern.slf4j.Slf4j;
import uk.ac.ebi.pride.data.mztab.parser.exceptions.LineItemParsingHandlerException;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab.parser
 * Timestamp: 2016-06-16 11:44
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 *
 * Quick processing strategy for mzTab version
 */
@Slf4j
public class QuickMzTabVersionLineItemParsingHandler extends MzTabVersionLineItemParsingHandler {
    // Check for attribute duplication
    private void checkForDuplicatedVersion(MzTabParser context, String line, long lineNumber, long offset) throws LineItemParsingHandlerException {
        if (context.getMetaDataSection().getVersion() != null) {
            throw new LineItemParsingHandlerException("DUPLICATED " + MZTAB_VERSION_KEYWORD + " found at line " + lineNumber);
        }
    }
    @Override
    protected boolean doProcessMztabVersion(MzTabParser context, String line, long lineNumber, long offset, String versionValue) throws LineItemParsingHandlerException {
        checkForDuplicatedVersion(context, line, lineNumber, offset);
        context.getMetaDataSection().setVersion(versionValue);
        return true;
    }
}
