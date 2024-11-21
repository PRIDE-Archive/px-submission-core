package uk.ac.ebi.pride.data.mztab.model;

import lombok.extern.slf4j.Slf4j;
import uk.ac.ebi.pride.data.mztab.exceptions.InvalidCvParameterException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab.model
 * Timestamp: 2016-07-07 16:21
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 */
@Slf4j
public class Software {
    
    public class SoftwareValue extends CvParameter {
        public SoftwareValue(String label, String accession, String name, String value) throws InvalidCvParameterException {
            super(label, accession, name, value);
        }

        public SoftwareValue(CvParameter cv) {
            super(cv);
        }
    }

    // Bean
    private SoftwareValue value = null;
    private Map<Integer, String> settings = new HashMap<>();

    public SoftwareValue getValue() {
        return value;
    }

    public void setValue(CvParameter value) {
        this.value = new SoftwareValue(value);
    }

    public String getSetting(int index) {
        return settings.get(index);
    }

    public String updateSetting(int index, String setting) {
        return settings.put(index, setting);
    }

    public Set<Integer> getReportedSettingsIndexes() {
        return settings.keySet();
    }

    public boolean validate(MzTabDocument context) throws ValidationException {
        if (context.getMetaData().getMode() == MetaData.MzTabMode.COMPLETE) {
            if (getValue() == null) {
                log.error("Software entry DOES NOT VALIDATE because its value is missing");
                return false;
            }
        }
        if ((getValue() != null) && (!getValue().validate())) {
            log.error("Software value DOES NOT VALIDATE");
            return false;
        }
        return true;
    }
}
