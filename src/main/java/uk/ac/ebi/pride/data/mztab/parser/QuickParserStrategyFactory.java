package uk.ac.ebi.pride.data.mztab.parser;


import lombok.extern.slf4j.Slf4j;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab.parser
 * Timestamp: 2016-06-09 14:44
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 *
 * This Factory puts together a collection of ParserState objects for the different sections of mzTab files, that as a
 * team, implement the "quick file parsing strategy", which is about collecting just the information we need from the
 * mzTab file for the px-submission tool
 */

@Slf4j
public class QuickParserStrategyFactory implements StrategyParserStateFactory {
    @Override
    public MetaDataParserState getMetaDataParserState() {
        return new QuickMetaDataParserState();
    }

    @Override
    public ProteinParserState getProteinParserState() {
        return new QuickProteinParserState();
    }

    @Override
    public PeptideParserState getPeptideParserState() {
        return new QuickPeptideParserState();
    }

    @Override
    public PsmParserState getPsmParserState() {
        return new QuickPsmParserState();
    }

    @Override
    public SmallMoleculeParserState getSmallMoleculeParserState() {
        return new QuickSmallMoleculeParserState();
    }
}
