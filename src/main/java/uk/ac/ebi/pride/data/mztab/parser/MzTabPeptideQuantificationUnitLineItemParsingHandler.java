package uk.ac.ebi.pride.data.mztab.parser;

import uk.ac.ebi.pride.data.mztab.parser.exceptions.LineItemParsingHandlerException;
import uk.ac.ebi.pride.data.mztab.parser.exceptions.MetadataLineItemParserStrategyException;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab.parser
 * Timestamp: 2016-07-07 11:51
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 */
public abstract class MzTabPeptideQuantificationUnitLineItemParsingHandler extends MetaDataLineItemParsingHandler implements MetaDataLineItemParsingHandler.LineItemBean {
    protected static final String MZTAB_PEPTIDE_QUANTIFICATION_UNIT = "peptide-quantification_unit";

    // TODO - Refactor in the future to create external beans that handle this?
    // Bean defaults
    protected static final String DEFAULT_LINE_ITEM_KEY = "";
    protected static final String DEFAULT_PROPERTY_VALUE = "";
    // Bean attributes
    private String lineItemKey = DEFAULT_LINE_ITEM_KEY;
    private String propertyValue = DEFAULT_PROPERTY_VALUE;

    @Override
    public String getLineItemKey() {
        return lineItemKey;
    }

    @Override
    public void setLineItemKey(String lineItemKey) {
        this.lineItemKey = lineItemKey;
    }

    @Override
    public String getPropertyValue() {
        return propertyValue;
    }

    @Override
    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    private void cleanBean() {
        lineItemKey = DEFAULT_LINE_ITEM_KEY;
        propertyValue = DEFAULT_PROPERTY_VALUE;
    }

    // TODO - Refactor out
    @Override
    protected boolean doParseLineItem(MzTabParser context, String line, long lineNumber, long offset) throws LineItemParsingHandlerException {
        cleanBean();
        try {
            if (MetadataLineItemParserStrategy.parseLine(this, line)) {
                if (getLineItemKey().equals(MZTAB_PEPTIDE_QUANTIFICATION_UNIT)) {
                    // The line item key is ok, go ahead
                    return processEntry(context, lineNumber, offset);
                }
            }
        } catch (MetadataLineItemParserStrategyException e) {
            throw new LineItemParsingHandlerException(e.getMessage());
        }
        return false;
    }

    // Delegate processing
    protected abstract boolean processEntry(MzTabParser context, long lineNumber, long offset) throws LineItemParsingHandlerException;
}
