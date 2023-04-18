package uk.ac.ebi.pride.data.model;

import uk.ac.ebi.pride.archive.dataprovider.file.ProjectFileType;
import uk.ac.ebi.pride.data.util.MassSpecFileFormat;

import java.io.Serializable;
import java.util.*;

/**
 * This class represents all the data collected for one Resubmission
 * It is used for generating a resubmission file
 *
 * @author Suresh Hewapathirana
 * @version $Id$
 */
public class Resubmission extends Submission implements Serializable {


    /**
     * A list of dataFiles managing their resubmission status
     * It means each datafile maintain their status as none(not modified), added, modified or deleted
     */
    Map<DataFile, ResubmissionFileChangeState> resubmission;



    public Resubmission() {
       super();
        this.resubmission = Collections.synchronizedMap(new HashMap<>());
    }

    public Resubmission(ProjectMetaData projectMetaData,
                        List<DataFile> dataFiles,
                        Map<DataFile, ResubmissionFileChangeState> resubmission) {
        super(projectMetaData,dataFiles);
        this.resubmission = resubmission;
    }

    @Override
    public synchronized void addDataFile(DataFile dataFile) {
        dataFiles.add(dataFile);
        addResubmissionDataFile(dataFile);
    }

    @Override
    public synchronized void removeDataFile(DataFile dataFile) {
        if (dataFile != null) {
            dataFiles.remove(dataFile);
            deleteResubmissionDataFile(dataFile);
        }
    }

    @Override
    public synchronized List<DataFile> getDataFilesByFormat(MassSpecFileFormat format) {
        List<DataFile> formattedDataFiles = new ArrayList();
        for(Map.Entry<DataFile, ResubmissionFileChangeState> entry : this.getResubmission().entrySet()) {
            if(format.equals(entry.getKey().getFileFormat())){
                formattedDataFiles.add(entry.getKey());
            }
        }
        return formattedDataFiles;
    }

    /**
     * Filter out the DataFiles by MassSpecFileFormat and exclude provided ResubmissionFileChangeState
     * @param format
     * @param resubmissionFileChangeState
     * @return
     */
    public synchronized List<DataFile> getFilteredDataFilesByFormat(MassSpecFileFormat format, ResubmissionFileChangeState resubmissionFileChangeState) {
        List<DataFile> formattedDataFiles = new ArrayList();
        for(Map.Entry<DataFile, ResubmissionFileChangeState> entry : this.getResubmission().entrySet()) {
            if(format.equals(entry.getKey().getFileFormat()) && !entry.getValue().equals(resubmissionFileChangeState)){
                formattedDataFiles.add(entry.getKey());
            }
        }
        return formattedDataFiles;
    }

    public synchronized List<DataFile> getDataFileByType(ProjectFileType fileType, ResubmissionFileChangeState resubmissionFileChangeState) {
        List<DataFile> typedDataFiles = new ArrayList<>();

        for(Map.Entry<DataFile, ResubmissionFileChangeState> entry : this.getResubmission().entrySet()) {
            if(fileType.equals(entry.getKey().getFileType()) && !entry.getValue().equals(resubmissionFileChangeState)){
                typedDataFiles.add(entry.getKey());
            }
        }
        return typedDataFiles;
    }

    public Map<DataFile, ResubmissionFileChangeState> getResubmission() {
        return resubmission;
    }

    public void setResubmission(Map<DataFile, ResubmissionFileChangeState> resubmission) {
        this.resubmission = resubmission;
    }

    public synchronized void addResubmissionDataFile(DataFile dataFile) {
        resubmission.put(dataFile, ResubmissionFileChangeState.NONE);
    }

    public synchronized void newResubmissionDataFile(DataFile dataFile) {
        resubmission.put(dataFile, ResubmissionFileChangeState.ADD);
    }

    public synchronized void modifyResubmissionDataFile(DataFile dataFile) {
        resubmission.put(dataFile, ResubmissionFileChangeState.MODIFY);
    }

    public synchronized void deleteResubmissionDataFile(DataFile dataFile) {
        resubmission.put(dataFile, ResubmissionFileChangeState.DELETE);
    }
}
