package uk.ac.ebi.pride.data.mztab.parser;

import lombok.extern.slf4j.Slf4j;
import uk.ac.ebi.pride.data.mztab.model.MzTabSectionValidator;
import uk.ac.ebi.pride.data.mztab.model.OneTimeDefaultValidatorMzTabSectionValidator;

import java.io.File;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab.parser
 * Timestamp: 2016-06-08 23:52
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 *
 * This is a quick parser for mzTab files, it will get
 */
@Slf4j
public class MzTabFullDocumentQuickParser extends MzTabParser {
    
    public MzTabFullDocumentQuickParser(String fileName) {
        super(fileName);
    }

    public MzTabFullDocumentQuickParser(File file) {
        super(file);
    }

    @Override
    protected StrategyParserStateFactory getParserStateFactory() {
        return new QuickParserStrategyFactory();
    }

    @Override
    protected MzTabSectionValidator getMzTabSectionValidator() {
        return new OneTimeDefaultValidatorMzTabSectionValidator();
    }
}
