package uk.ac.ebi.pride.data.mztab.model;

import uk.ac.ebi.pride.data.mztab.exceptions.InvalidProteinSection;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab.model
 * Timestamp: 2016-07-01 13:45
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 *
 * Common behaviour across all the sections
 *
 * I'm not using this really... but I'll leave it here as a preventive thing for future needs, I can always remove it later
 * if I don't use it for anything
 */
public interface MzTabSection {
    /**
     * Implements the validation of a particular subsection by using the given validation strategy
     * @param mzTabDocument context for performing the validation
     * @param validator validation algorithm/strategy
     * @throws InvalidProteinSection thrown if the section is not valid
     */
    boolean validate(MzTabDocument mzTabDocument, MzTabSectionValidator validator) throws InvalidMzTabSectionException;
}
