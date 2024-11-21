package uk.ac.ebi.pride.data.mztab.parser;

import lombok.extern.slf4j.Slf4j;
import uk.ac.ebi.pride.data.mztab.parser.exceptions.ParserStateException;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab.parser
 * Timestamp: 2016-06-08 21:41
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 *
 * The hierarchy ParserState implements a State pattern, changing the behaviour of the parser at runtime,
 * depending on the particular section that is currently being processed
 *
 */
@Slf4j
public abstract class ParserState {
    // Chain of responsibility for parsing metadata items
    private LineItemParsingHandler lineItemParsingHandler = null;

    protected final void setLineItemParsingHandler(LineItemParsingHandler lineItemParsingHandler) {
        this.lineItemParsingHandler = lineItemParsingHandler;
    }

    protected final LineItemParsingHandler getLineItemParsingHandler() {
        // Lazy building of the chain of responsibility that takes care of parsing the meta data section
        if (lineItemParsingHandler == null) {
            lineItemParsingHandler = buildLineItemParsingHandlerChain();
        }
        return lineItemParsingHandler;
    }

    // Change Parser State - Template Method
    protected final void changeState(MzTabParser context, ParserState newState) throws ParserStateException {
        if (!doValidateSubProduct(context)) {
            throw new ParserStateException("The current subproduct DOES NOT VALIDATE");
        }
        doChangeState(context, newState);
    }

    // Hook for modifying parser state change
    protected void doChangeState(MzTabParser context, ParserState newState) {
        // The responsibilities described for this quick parser at this time, it makes sense that all the different
        // strategies will do the same when it comes to change the state of the parser, as new parser states are created
        // via an abstract factory, and no special/additional steps are required to change the state.
        // Nonetheless, I leave this method here as a "landmark", to point at where special and/or additional steps
        // should be taken upon state change. This gives the software both a general algorithm and a fine tune point,
        // both super classes and subclasses a "say" upon a state change. Any additional processing upon state change
        // will push this method down the class hierarchy
        context.changeState(newState);
    }
    // Delegate to subclasses
    public abstract void parseLine(MzTabParser context, String line, long lineNumber, long offset) throws ParserStateException;
    // TODO By looking at the current delegates for subproduct validation, I could convert this method into a template method
    protected abstract boolean doValidateSubProduct(MzTabParser context) throws ParserStateException;
    // Get this state ID name
    protected abstract String getStateIdName();
    // Director and builder of the chain of responsibility
    protected abstract LineItemParsingHandler buildLineItemParsingHandlerChain();
}
