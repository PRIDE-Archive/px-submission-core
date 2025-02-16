package uk.ac.ebi.pride.data.mztab.model;

import uk.ac.ebi.pride.data.mztab.exceptions.InvalidCvParameterException;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab.model
 * Timestamp: 2016-07-05 15:08
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 */
public class PsmSearchEngineScore extends CvParameter {
    public PsmSearchEngineScore(String label, String accession, String name, String value) throws InvalidCvParameterException {
        super(label, accession, name, value);
    }

    public PsmSearchEngineScore(CvParameter cv) {
        super(cv);
    }

}
