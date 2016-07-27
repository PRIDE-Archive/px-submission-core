package uk.ac.ebi.pride.data.mztab.model;

import uk.ac.ebi.pride.data.mztab.exceptions.InvalidCvParameterException;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab.model
 * Timestamp: 2016-07-06 11:56
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 */
public class PeptideQuantificationUnit extends CvParameter {
    public PeptideQuantificationUnit(String label, String accession, String name, String value) throws InvalidCvParameterException {
        super(label, accession, name, value);
    }

    public PeptideQuantificationUnit(CvParameter cv) {
        super(cv);
    }
}
