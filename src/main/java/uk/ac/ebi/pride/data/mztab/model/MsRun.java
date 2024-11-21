package uk.ac.ebi.pride.data.mztab.model;


import lombok.extern.slf4j.Slf4j;

import java.net.URL;

/**
 * Project: px-submission-tool
 * Package: uk.ac.ebi.pride.gui.data.mztab
 * Timestamp: 2016-06-08 11:02
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 *
 * This class models an ms-run entry in the metadata section of mzTab files
 */

@Slf4j
public class MsRun {
    
    // Bean
    private MsRunFormat msRunFormat = null;
    private MsRunIdFormat msRunIdFormat = null;
    private URL location = null;
    private MsRunHashMethod hashMethod = null;
    private String hash = null;
    // Flags
    private boolean locationSeen = false;

    public MsRun() {
    }

    public MsRun(MsRunFormat msRunFormat, MsRunIdFormat msRunIdFormat, URL location) {
        this.msRunFormat = msRunFormat;
        this.msRunIdFormat = msRunIdFormat;
        this.location = location;
    }

    public MsRunFormat getMsRunFormat() {
        return msRunFormat;
    }

    public MsRunIdFormat getMsRunIdFormat() {
        return msRunIdFormat;
    }

    public URL getLocation() {
        return location;
    }

    public boolean hasLocationBeenSeen() {
        return locationSeen;
    }

    public void setMsRunFormat(MsRunFormat msRunFormat) {
        this.msRunFormat = msRunFormat;
    }

    public void setMsRunIdFormat(MsRunIdFormat msRunIdFormat) {
        this.msRunIdFormat = msRunIdFormat;
    }

    public void setLocation(URL location) {
        this.location = location;
        setLocationSeen();
    }

    public void setLocationSeen() {
        locationSeen = true;
    }

    public MsRunHashMethod getHashMethod() {
        return hashMethod;
    }

    public void setHashMethod(MsRunHashMethod hashMethod) {
        this.hashMethod = hashMethod;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public boolean validate() throws ValidationException {
        // Validation Criteria
        // location must always be reported
        if (!hasLocationBeenSeen()) {
            log.error("MISSING ms_run location information");
            return false;
        }
        // If ms-run format is reported, ms-run id_format is required
        if ((getMsRunFormat() != null) && (getMsRunIdFormat() == null)) {
            log.error("ms_run format specified, but MISSING ms-run ID_format");
            return false;
        }
        if ((getMsRunFormat() != null) && (!getMsRunFormat().validate())) {
            log.error("ms_run format IS INVALID");
            return false;
        }
        if ((getMsRunIdFormat() != null) && (!getMsRunIdFormat().validate())) {
            log.error("ms_run ID format IS INVALID");
            return false;
        }
        if ((getHash() != null) && (getHashMethod() == null)) {
            log.error("ms_run hash is present BUT ms_run hash_method IS MISSING!");
            return false;
        }
        if ((getHashMethod() != null) && (!getHashMethod().validate())) {
            log.error("This ms_run DOES NOT VALIDATE because its hash_method IS INVALID");
            return false;
        }
        return true;
    }
}
