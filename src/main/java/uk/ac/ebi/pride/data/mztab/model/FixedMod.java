package uk.ac.ebi.pride.data.mztab.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.pride.data.mztab.exceptions.InvalidCvParameterException;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab.model
 * Timestamp: 2016-07-06 11:37
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 */
public class FixedMod {
    private static final Logger logger = LoggerFactory.getLogger(FixedMod.class);

    public class FixedModValue extends CvParameter {
        public FixedModValue(String label, String accession, String name, String value) throws InvalidCvParameterException {
            super(label, accession, name, value);
        }

        public FixedModValue(CvParameter cv) {
            super(cv);
        }
    }

    // Bean
    private FixedModValue value = null;
    private String site = null;
    private String position = null;

    public FixedModValue getValue() {
        return value;
    }

    public void setValue(CvParameter value) {
        this.value = new FixedModValue(value);
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean validate() throws ValidationException {
        // There must be a value
        if (getValue() == null) {
            logger.error("MISSING value for fixed_mod item");
            return false;
        }
        if (!getValue().validate()) {
            logger.error("fixed_mod value IS INVALID!");
            return false;
        }
        return true;
    }
}
