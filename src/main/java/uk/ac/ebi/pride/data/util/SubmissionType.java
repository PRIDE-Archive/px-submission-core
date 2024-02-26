package uk.ac.ebi.pride.data.util;

public enum SubmissionType {
    COMPLETE,
    PARTIAL,
    RAW,
    AFFINITY,
    PRIDE;


    public static SubmissionType fromString(String submissionType) {
        if (submissionType != null) {
            for (SubmissionType s : SubmissionType.values()) {
                if (s.toString().equalsIgnoreCase(submissionType.trim())) {
                    return s;
                }
            }
        }
        return null;
    }

}