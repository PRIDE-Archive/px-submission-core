package uk.ac.ebi.pride.data.mztab.model;


import lombok.extern.slf4j.Slf4j;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab.model
 * Timestamp: 2016-06-08 23:31
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 */
@Slf4j
public class MzTabDocument implements ValidableProduct {
    
    // mzTab Sections
    private MetaData metaData = null;
    private ProteinData proteinData = null;
    private PsmData psmData = null;
    private SmallMoleculeData smallMoleculeData = null;
    private PeptideData peptideData = null;

    public MzTabDocument() {
        metaData = null;
    }

    public MetaData getMetaData() {
        return metaData;
    }

    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    public ProteinData getProteinData() {
        return proteinData;
    }

    public void setProteinData(ProteinData proteinData) {
        this.proteinData = proteinData;
    }

    public PsmData getPsmData() {
        return psmData;
    }

    public void setPsmData(PsmData psmData) {
        this.psmData = psmData;
    }

    public SmallMoleculeData getSmallMoleculeData() {
        return smallMoleculeData;
    }

    public void setSmallMoleculeData(SmallMoleculeData smallMoleculeData) {
        this.smallMoleculeData = smallMoleculeData;
    }

    public PeptideData getPeptideData() {
        return peptideData;
    }

    public void setPeptideData(PeptideData peptideData) {
        this.peptideData = peptideData;
    }

    @Override
    public boolean validate(MzTabSectionValidator validator) throws ValidationException {
        // Call Validate on every subproduct
        // Validate Metadata section (required)
        if (getMetaData() == null) {
            log.error("MISSING REQUIRED Metadata section!!! Seriously! What are you doing!?!?");
            return false;
        }
        if (!getMetaData().validate(this, validator)) {
            log.error("Metadata section is NOT VALID, please, check logging messages");
            return false;
        }
        // Validate protein section
        if ((getProteinData() != null) && (!getProteinData().validate(this, validator))) {
            log.error("Protein section is NOT VALID, please, check logging messages");
            return false;
        }
        // Validate Peptide section
        if ((getPeptideData() != null) && (!getPeptideData().validate(this, validator))) {
            log.error("Peptide section is NOT VALID, please, check logging messages");
            return false;
        }
        // Validate PSM section
        if ((getPsmData() != null) && (!getPsmData().validate(this, validator))) {
            log.error("PSM section is NOT VALID, please, check logging messages");
            return false;
        }
        // Validate Small Molecules section
        if ((getSmallMoleculeData() != null) && (!getSmallMoleculeData().validate(this, validator))) {
            log.error("Small Molecule section is NOT VALID, please, check logging messages");
            return false;
        }
        // TODO - apply document wide validation criteria (like requirements depending on mzTab type and mode specified)
        return true;
    }
}
