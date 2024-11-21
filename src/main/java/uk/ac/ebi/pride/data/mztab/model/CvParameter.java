package uk.ac.ebi.pride.data.mztab.model;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
public abstract class CvParameter {
    
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
        log.debug("Get label '" + label + "'");
        return label;
    }

    public void setLabel(String label) throws InvalidCvParameterException {
        log.debug("Set label '" + label + "'");
        this.label = label;
        // REVISIT validation
        if (!this.validate())
            this.reportInvalidCvParameter();
    }

    public String getAccession() {
        log.debug("Get accession '" + accession + "'");
        return accession;
    }

    public void setAccession(String accession) throws InvalidCvParameterException {
        log.debug("Set accession '" + accession + "'");
        this.accession = accession;
        // REVISIT validation
        if (!this.validate())
            this.reportInvalidCvParameter();
    }

    public String getName() {
        log.debug("Get name '" + name + "'");
        return name;
    }

    public void setName(String name) throws InvalidCvParameterException {
        log.debug("Set name '" + name + "'");
        this.name = name;
        // REVISIT validation
        if (!this.validate())
            this.reportInvalidCvParameter();
    }

    public String getValue() {
        log.debug("Get value '" + value + "'");
        return value;
    }

    public void setValue(String value) throws InvalidCvParameterException {
        log.debug("Set value '" + value + "'");
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
