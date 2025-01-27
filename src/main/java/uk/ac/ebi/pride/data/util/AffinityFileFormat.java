package uk.ac.ebi.pride.data.util;

import uk.ac.ebi.pride.archive.dataprovider.file.ProjectFileType;

import java.io.File;
import java.io.IOException;

public enum AffinityFileFormat {
    ADAT("adat", true,ProjectFileType.RAW),
    BCL("bml", true, ProjectFileType.RAW),
    PARQUET("parquet", true, ProjectFileType.RAW),

    NPX("npx.csv", true, ProjectFileType.RESULT),
    RAW("raw.csv", true, ProjectFileType.RESULT);

    private String fileExtension;
    private boolean fileFormat;
    private ProjectFileType fileType;

    private AffinityFileFormat(String fileExtension, boolean fileFormat, ProjectFileType fileType) {
        this.fileExtension = fileExtension;
        this.fileFormat = fileFormat;
        this.fileType = fileType;
    }

    public static AffinityFileFormat checkFormat(File file) throws IOException {
        AffinityFileFormat format = null;

        if(file.getName().endsWith(NPX.getFileExtension())){
                return NPX;
            }

        if (file.getName().endsWith(RAW.getFileExtension())){
                return RAW;
            }

        String ext = FileUtil.getFileExtension(file);
        if (ext != null) {
                format = checkFormatByExtension(ext);
        }
        return format;
    }

    public static AffinityFileFormat checkFormatByExtension(String ext) {
        for (AffinityFileFormat value : values()) {
            if (value.getFileExtension().equalsIgnoreCase(ext) && value.isFileFormat()) {
                return value;
            }
        }
        return null;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public boolean isFileFormat() {
        return fileFormat;
    }

    public ProjectFileType getFileType() {
        return fileType;
    }
}
