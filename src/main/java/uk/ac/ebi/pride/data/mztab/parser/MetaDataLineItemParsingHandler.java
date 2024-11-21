package uk.ac.ebi.pride.data.mztab.parser;

import lombok.extern.slf4j.Slf4j;
import uk.ac.ebi.pride.data.mztab.parser.exceptions.LineItemParsingHandlerException;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab.parser
 * Timestamp: 2016-06-14 16:03
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 *
 * This branch of the hierarchy processes information at section level for all those items in the metadata section of an
 * mzTab document.
 */

@Slf4j
public abstract class MetaDataLineItemParsingHandler extends LineItemParsingHandler {
    
    protected interface LineItemBean {
        String getLineItemKey();
        void setLineItemKey(String lineItemKey);
        void setPropertyValue(String pv);
        String getPropertyValue();
    }

    protected interface IndexedLineItemBean extends LineItemBean {
        int getIndex();
        void setIndex(int index);
    }

    protected interface IndexedLineItemWithPropertyBean extends IndexedLineItemBean {
        String getPropertyKey();
        void setPropertyKey(String pk);
    }

    protected interface IndexedLineItemWithIndexedPropertyDataEntry extends IndexedLineItemWithPropertyBean {
        int getPropertyEntryIndex();
        void setPropertyEntryIndex(int index);
    }

    @Override
    protected boolean doParseLine(MzTabParser context, String line, long lineNumber, long offset) throws LineItemParsingHandlerException {
        return doParseLineItem(context, line, lineNumber, offset);
    }

    // Delegate processing of a particular item to subclass
    protected abstract boolean doParseLineItem(MzTabParser context, String line, long lineNumber, long offset) throws LineItemParsingHandlerException;
}
