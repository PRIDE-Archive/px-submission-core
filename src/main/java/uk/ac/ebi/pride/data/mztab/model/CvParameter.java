package uk.ac.ebi.pride.data.mztab.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.ac.ebi.pride.data.mztab.exceptions.InvalidCvParameterException;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab
 * Timestamp: 2016-06-08 11:17
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 *
 * This class models a CV Parameter
 */

public abstract class CvParameter {
    private static final Logger logger = LoggerFactory.getLogger(CvParameter.class);

    // Default value
    public static final String DEFAULT_VALUE = "";
    // Bean
    private String label = DEFAULT_VALUE;
    private String accession = DEFAULT_VALUE;
    private String name = DEFAULT_VALUE;
    private String value = DEFAULT_VALUE;

    public CvParameter(String label, String accession, String name, String value) throws InvalidCvParameterException {
        this.label = label;
        this.accession = accession;
        this.name = name;
        this.value = value;
        if (!this.validate()) {
            this.reportInvalidCvParameter();
        }
    }

    public CvParameter(CvParameter cv) {
        this(cv.label, cv.accession, cv.name, cv.value);
    }

    public String getLabel() {
        logger.debug("Get label '" + label + "'");
        return label;
    }

    public void setLabel(String label) throws InvalidCvParameterException {
        logger.debug("Set label '" + label + "'");
        this.label = label;
        // REVISIT validation
        if (!this.validate())
            this.reportInvalidCvParameter();
    }

    public String getAccession() {
        logger.debug("Get accession '" + accession + "'");
        return accession;
    }

    public void setAccession(String accession) throws InvalidCvParameterException {
        logger.debug("Set accession '" + accession + "'");
        this.accession = accession;
        // REVISIT validation
        if (!this.validate())
            this.reportInvalidCvParameter();
    }

    public String getName() {
        logger.debug("Get name '" + name + "'");
        return name;
    }

    public void setName(String name) throws InvalidCvParameterException {
        logger.debug("Set name '" + name + "'");
        this.name = name;
        // REVISIT validation
        if (!this.validate())
            this.reportInvalidCvParameter();
    }

    public String getValue() {
        logger.debug("Get value '" + value + "'");
        return value;
    }

    public void setValue(String value) throws InvalidCvParameterException {
        logger.debug("Set value '" + value + "'");
        this.value = value;
        // REVISIT validation
        if (!this.validate())
            this.reportInvalidCvParameter();
    }

    @Override
    public String toString() {
        return "[" + label + ", " + accession + ", " + name + ", " + value + "]";
    }

    private void reportInvalidCvParameter() throws InvalidCvParameterException {
        String msg = this.getClass().getName().toString() + " - Invalid CV Parameter: '" + this.toString() + "'";
        throw new InvalidCvParameterException(msg);
    }

    /**
     * The following method validates the current CV Parameter instance
     * It's been placed here in case we want to implement a meaningful validation process for CV parameters, if we need
     * it.
     * @return true if it is valid, false otherwise
     */
    public boolean validate() {
        return true;
    }
}
