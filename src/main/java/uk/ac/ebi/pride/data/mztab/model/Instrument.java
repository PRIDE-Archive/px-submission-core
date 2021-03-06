package uk.ac.ebi.pride.data.mztab.model;

import uk.ac.ebi.pride.data.mztab.exceptions.InvalidCvParameterException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Project: px-submission-core
 * Package: uk.ac.ebi.pride.data.mztab.model
 * Timestamp: 2016-08-24 9:55
 * ---
 * © 2016 Manuel Bernal Llinares <mbdebian@gmail.com>
 * All rights reserved.
 */
public class Instrument {

    // Name
    public class Name extends CvParameter {
        public Name(String label, String accession, String name, String value) throws InvalidCvParameterException {
            super(label, accession, name, value);
        }

        public Name(CvParameter cv) {
            super(cv);
        }
    }

    // Source
    public class Source extends CvParameter {
        public Source(String label, String accession, String name, String value) throws InvalidCvParameterException {
            super(label, accession, name, value);
        }

        public Source(CvParameter cv) {
            super(cv);
        }
    }

    // Analyzer
    public class Analyzer extends CvParameter {
        public Analyzer(String label, String accession, String name, String value) throws InvalidCvParameterException {
            super(label, accession, name, value);
        }

        public Analyzer(CvParameter cv) {
            super(cv);
        }
    }

    // Detector
    public class Detector extends CvParameter {
        public Detector(String label, String accession, String name, String value) throws InvalidCvParameterException {
            super(label, accession, name, value);
        }

        public Detector(CvParameter cv) {
            super(cv);
        }
    }

    // Attributes
    private Name name = null;
    private Source source = null;
    private Detector detector = null;
    private Map<Integer, Analyzer> analyzers = new HashMap<>();

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setName(CvParameter name) {
        this.name = new Name(name);
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public void setSource(CvParameter source) {
        this.source = new Source(source);
    }

    public Detector getDetector() {
        return detector;
    }

    public void setDetector(Detector detector) {
        this.detector = detector;
    }

    public void setDetector(CvParameter detector) {
        this.detector = new Detector(detector);
    }

    // Analyzers management
    public Analyzer updateAnalyzer(Analyzer analyzer, int index) {
        return analyzers.put(index, analyzer);
    }

    public Analyzer updateAnalyzer(CvParameter analyzer, int index) {
        return updateAnalyzer(new Analyzer(analyzer), index);
    }

    public Analyzer getAnalyzerEntry(int index) {
        return analyzers.get(index);
    }

    public Set<Integer> getAvailableAnalyzerIndexes() {
        return analyzers.keySet();
    }

    // Validation criteria
    public boolean validate(MzTabDocument mzTabDocument) throws ValidationException {
        // TODO - Currently, there is no validation criteria defined for this item in the mzTab specification, so we'll
        // TODO - just say that, if this is present, it should have at least one attribute
        boolean analyzersValidate = false;
        if (analyzers.size() > 0) {
            analyzersValidate = true;
            for (int index :
                    analyzers.keySet()) {
                analyzersValidate &= analyzers.get(index).validate();
            }
        }
        return ((name != null) && name.validate())
                || ((source != null) && source.validate())
                || ((detector != null) && detector.validate())
                || analyzersValidate;
    }
}
