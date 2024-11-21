package uk.ac.ebi.pride.data.mztab.parser;

import lombok.extern.slf4j.Slf4j;
import uk.ac.ebi.pride.data.mztab.model.InvalidMzTabSectionException;
import uk.ac.ebi.pride.data.mztab.parser.exceptions.LineItemParsingHandlerException;
import uk.ac.ebi.pride.data.mztab.parser.exceptions.ParserStateException;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab.parser
 * Timestamp: 2016-06-30 12:10
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 *
 * This class handles the behaviour of parsing mzTab protein data
 */
@Slf4j
public abstract class ProteinParserState extends ParserState {
    private static final String STATE_ID_NAME = "ProteinData_parser_state";

    @Override
    protected String getStateIdName() {
        return STATE_ID_NAME;
    }

    @Override
    protected boolean doValidateSubProduct(MzTabParser context) throws ParserStateException {
        // Delegate validation to the subproduct itself, for the given context
        try {
            return context.getProteinDataSection().validate(context.getMzTabDocument(), context.getMzTabSectionValidator());
        } catch (InvalidMzTabSectionException e) {
            throw new ParserStateException("Invalid validation of Protein Data section due to ---> " + e.getMessage());
        }
    }

    @Override
    public void parseLine(MzTabParser context, String line, long lineNumber, long offset) throws ParserStateException {
        if (!line.trim().isEmpty()) {
            if (line.matches("^(PRH|PRT|COM)\\s?.*$")) {
                // Header, data entry or comment
                try {
                    if (!getLineItemParsingHandler().parseLine(context, line, lineNumber, offset)) {
                        log.warn("IGNORED Line '" + lineNumber + "', offset '" + offset + "', content '" + line + "'");
                    }
                } catch (LineItemParsingHandlerException e) {
                    throw new ParserStateException("Error parsing line '" + lineNumber + "' ---> " + e.getMessage());
                }
            } else if (line.startsWith("PEH")) {
                // Change state to Peptide processing mode
                PeptideParserState peptideParserState = context.getParserStateFactory().getPeptideParserState();
                changeState(context, peptideParserState);
                peptideParserState.parseLine(context, line, lineNumber, offset);
            } else if (line.startsWith("PSH")) {
                // Change state to PSM processing mode
                PsmParserState psmParserState = context.getParserStateFactory().getPsmParserState();
                changeState(context, psmParserState);
                psmParserState.parseLine(context, line, lineNumber, offset);
            } else if (line.startsWith("SMH")) {
                // Change state to Small Molecule processing mode
                SmallMoleculeParserState smallMoleculeParserState = context.getParserStateFactory().getSmallMoleculeParserState();
                changeState(context, smallMoleculeParserState);
                smallMoleculeParserState.parseLine(context, line, lineNumber, offset);
            } else {
                // UNEXPECTED Line content ERROR
                throw new ParserStateException("UNEXPECTED LINE '" + line + "' at line number '" + lineNumber + "', offset '" + offset + "'");
            }
        }
    }
}
