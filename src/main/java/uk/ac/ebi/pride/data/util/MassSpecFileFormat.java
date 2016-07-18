package uk.ac.ebi.pride.data.util;

import uk.ac.ebi.pride.archive.dataprovider.file.ProjectFileType;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * Different mass spec file format, also include support methods for detecting the format
 *
 * @author Rui Wang
 * @version $Id$
 *          <p/>
 *          todo: add quant file formats
 */
public enum MassSpecFileFormat {
    MGF("mgf", true, ProjectFileType.PEAK),
    MZML("mzml", true, ProjectFileType.RAW),
    INDEXED_MZML("mzml", true, ProjectFileType.RAW),
    MZIDENTML("mzid", true, ProjectFileType.RESULT),
    PRIDE("xml", true, ProjectFileType.RESULT),
    DAT("dat", true, ProjectFileType.SEARCH),
    XTANDEM("xml", true, ProjectFileType.SEARCH),
    PKL("pkl", true, ProjectFileType.PEAK),
    PKL_SPO("spo", true, ProjectFileType.SEARCH),
    SEQUEST_DTA("dta", true, ProjectFileType.PEAK),
    SEQUEST_OUT("out", true, ProjectFileType.SEARCH),
    OMSSA_OMX("omx", true, ProjectFileType.SEARCH),
    MSGF("msgf", true, ProjectFileType.SEARCH),
    SPECTRAST("xls", true, ProjectFileType.SEARCH),
    CRUX("txt", true, ProjectFileType.SEARCH),
    PEPTIDE_PROPHET("pepxml", true, ProjectFileType.SEARCH),
    PROTEIN_PROPHET("protxml", true, ProjectFileType.SEARCH),
    PROTEIN_PILOT("group", true, ProjectFileType.SEARCH),
    PROTEOME_DISCOVERER("msf", true, ProjectFileType.SEARCH),
    VEMS_PKX("pkx", true, ProjectFileType.RAW),
    MS2("ms2", true, ProjectFileType.PEAK),
    MZDATA("mzdata", true, ProjectFileType.RAW),
    MZXML("mzxml", true, ProjectFileType.RAW),
    BRUKER_BAF("baf", true, ProjectFileType.RAW),
    BRUKER_FID("fid", true, ProjectFileType.RAW),
    BRUKER_YEP("yep", true, ProjectFileType.RAW),
    ABI_WIFF("wiff", true, ProjectFileType.RAW),
    ABI_SCAN("scan", true, ProjectFileType.RAW),
    ABI_MTD("mtd", true, ProjectFileType.RAW),
    RAW("raw", true, ProjectFileType.RAW),
    AGILENT_MASSHUNTER_RAW("d", false, ProjectFileType.RAW),
    IBD("ibd", true, ProjectFileType.RAW),
    IMG("img", true, ProjectFileType.RAW),
    IMZML("imzml", true, ProjectFileType.MS_IMAGE_DATA),
    HDR("hdr", true, ProjectFileType.MS_IMAGE_DATA),
    FASTA("fasta", true, ProjectFileType.FASTA),
    TIF("tif", true, ProjectFileType.GEL),
    GIF("gif", true, ProjectFileType.GEL),
    PNG("png", true, ProjectFileType.GEL),
    JPG("jpg", true, ProjectFileType.GEL),
    MZTAB("mztab", true, ProjectFileType.RESULT);


    private String fileExtension;
    private boolean fileFormat;
    private ProjectFileType fileType;

    private MassSpecFileFormat(String fileExtension, boolean fileFormat, ProjectFileType fileType) {
        this.fileExtension = fileExtension;
        this.fileFormat = fileFormat;
        this.fileType = fileType;
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

    /**
     * Check whether a folder is a recognized mass spec data folder
     *
     * @param folder input folder
     * @return boolean true means mass spec data folder
     */
    public static boolean isMassSpecDataFolder(File folder) throws IOException {
        boolean msFolder = false;

        if (folder.isDirectory()) {
            MassSpecFileFormat format = checkFormat(folder);
            if (format != null) {
                msFolder = true;
            }
        }

        return msFolder;
    }

    /**
     * Get mass spec file type
     *
     * @param file given file
     * @return MassSpecFileType    mass spec file type
     */
    public static ProjectFileType getType(File file) throws IOException {
        MassSpecFileFormat format = checkFormat(file);

        return format == null ? ProjectFileType.OTHER : format.getFileType();
    }

    /**
     * Get mass spec file type
     *
     * @param url given URL
     * @return MassSpecFileType    mass spec file type
     */
    public static ProjectFileType getType(URL url) throws IOException, URISyntaxException {
        MassSpecFileFormat format = checkFormat(url);

        return format == null ? ProjectFileType.OTHER : format.getFileType();
    }

    /**
     * Detect mass spec file format
     *
     * @param url input URL
     * @return MassSpecFileFormat  mass spec file format
     */
    public static MassSpecFileFormat checkFormat(URL url) throws IOException, URISyntaxException {
        String urlStr = url.getFile();
        String fileName = urlStr.substring(urlStr.lastIndexOf("/") + 1, urlStr.length());
        File file = new File(fileName);

        return checkFormat(file);
    }

    /**
     * Detect mass spec file format
     *
     * @param file input file
     * @return MassSpecFileFormat  mass spec file format
     */
    public static MassSpecFileFormat checkFormat(File file) throws IOException {
        MassSpecFileFormat format = null;

        String ext = FileUtil.getFileExtension(file);

        if (ext != null) {
            if ("xml".equalsIgnoreCase(ext)) {
                format = checkXmlFile(file);
            } else if ("zip".equalsIgnoreCase(ext)) {
                format = checkZippedFile(file);
            } else if ("gz".equalsIgnoreCase(ext)) {
                format = checkGzippedFile(file);
            } else if ("mzml".equalsIgnoreCase(ext)) {
                format = file.exists() ? checkXmlFileContent(file) : MZML;
            } else if ("txt".equalsIgnoreCase(ext)) {
                format = null;
            } else if ("xls".equalsIgnoreCase(ext)) {
                format = null;
            } else {
                format = checkFormatByExtension(ext);
            }
        }

        return format;
    }

    /**
     * Detect file format by detecting file extension
     *
     * @param ext    extension of a given file or folder
     * @return MassSpecFileFormat  mass spec file format
     */
    private static MassSpecFileFormat checkFormatByExtension(String ext) {
        for (MassSpecFileFormat value : values()) {
            if (value.getFileExtension().equalsIgnoreCase(ext) && value.isFileFormat()) {
                return value;
            }
        }

        return null;
    }


    /**
     * Check the file format of a xml file
     *
     * @param file  input xml file
     * @return  file format
     * @throws IOException
     */
    private static MassSpecFileFormat checkXmlFile(File file) throws IOException {
        MassSpecFileFormat fileFormat = checkXmlFileExtension(file);

        if (fileFormat == null && file.exists() && !FileUtil.isFileEmpty(file)) {
            fileFormat = checkXmlFileContent(file);
        }

        return fileFormat;
    }

    /**
     * Check the file format of a xml file by its extension
     *
     * @param file
     * @return
     * @throws IOException
     */
    private static MassSpecFileFormat checkXmlFileExtension(File file) throws IOException {
        String fileName = file.getName();

        MassSpecFileFormat format = null;

        if (fileName.endsWith(".xt.xml")) {
            format = MassSpecFileFormat.XTANDEM;
        } else if (fileName.endsWith(".pride.xml")) {
            format = MassSpecFileFormat.PRIDE;
        }

        return format;
    }

    /**
     * Detect the file format of a xml file
     *
     * @param file xml file
     * @return MassSpecFileFormat  mass spec file format
     */
    private static MassSpecFileFormat checkXmlFileContent(File file) throws IOException {
        MassSpecFileFormat format = null;
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(file));
            // read first ten lines
            StringBuilder content = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                content.append(reader.readLine());
            }
            // detect format
            format = detectFormat(content.toString());
        } catch (IOException ioe) {
            //do nothing here
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return format;
    }


    private static MassSpecFileFormat checkZippedFileExtension(File file) throws IOException {
        MassSpecFileFormat format = null;

        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(file);

            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            if (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String fileName = entry.getName();
                String fileExtension = FileUtil.getFileExtension(fileName);
                format = checkFormatByExtension(fileExtension);
            }
        } catch (ZipException ze) {
            // unable to extract zip file, perhaps has nested directories or too large in size
            format = null;
        } finally {
            if (zipFile != null) {
                zipFile.close();
            }
        }

        return format;
    }


    /**
     * Check the file format of a zip file
     * <p/>
     * Taking into account of both the file name and file content if the file exists
     *
     * @param file
     * @return
     * @throws IOException
     */
    private static MassSpecFileFormat checkZippedFile(File file) throws IOException {
        MassSpecFileFormat fileFormat = checkZippedFileExtension(file);

        if (fileFormat == null && file.exists() && !FileUtil.isFileEmpty(file)) {
            fileFormat = checkZippedFileContent(file);
        }

        return fileFormat;
    }

    /**
     * Check the file format of a zip file
     *
     * @param file zipped file
     * @return mass spec file format
     */
    private static MassSpecFileFormat checkZippedFileContent(File file) throws IOException {
        MassSpecFileFormat format = null;

        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(file);

            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            if (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                // reading buffer size
                int BUFFER = 1048;
                byte data[] = new byte[BUFFER];

                InputStream inputStream = zipFile.getInputStream(entry);
                inputStream.read(data, 0, BUFFER);

                // convert byte array to string
                String content = new String(data);
                format = detectFormat(content);
            }
        } catch (ZipException ze) {
            // unable to extract zip file, perhaps has nested directories or too large in size
            format = null;
        } finally {
            if (zipFile != null) {
                zipFile.close();
            }
        }

        return format;
    }


    /**
     * Check the file format of a gzipped file
     * <p/>
     * Taking into account of both the file extension and file content if the file exists
     *
     * @param file gzipped input file
     * @return file format
     * @throws IOException
     */
    private static MassSpecFileFormat checkGzippedFile(File file) throws IOException {
        MassSpecFileFormat fileFormat = checkGzippedFileExtension(file);
 
        if (fileFormat != null && fileFormat.equals(MassSpecFileFormat.MZML) && file.exists()) {
        	fileFormat = checkGzippedFileContent(file);
        }
        if (fileFormat == null && file.exists() && !FileUtil.isFileEmpty(file)) {
            fileFormat = checkGzippedFileContent(file);
        }

        return fileFormat;
    }

    /**
     * Check gzipped file by extension
     *
     * @param file gzipped input file
     * @return file format
     * @throws IOException
     */
    private static MassSpecFileFormat checkGzippedFileExtension(File file) throws IOException {
        MassSpecFileFormat format;

        String fileName = file.getName();
        fileName = fileName.substring(0, fileName.length() - 3);
        String fileExtension = FileUtil.getFileExtension(fileName);
        format = checkFormatByExtension(fileExtension);
        return format;
    }

    /**
     * Check the file format of a gzipped file
     *
     * @param file gzipped file
     * @return mass spec file format
     */
    private static MassSpecFileFormat checkGzippedFileContent(File file) throws IOException {
        MassSpecFileFormat format = null;

        GZIPInputStream gzipInputStream = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            gzipInputStream = new GZIPInputStream(new BufferedInputStream(fileInputStream));

            // reading buffer size
            int BUFFER = 1048;
            byte data[] = new byte[BUFFER];

            gzipInputStream.read(data, 0, BUFFER);

            // convert byte array to string
            String content = new String(data);
            format = detectFormat(content);

        } finally {
            if (gzipInputStream != null) {
                gzipInputStream.close();
            }
        }

        return format;
    }

    /**
     * Detect the format based on a given string
     *
     * @param content input content
     * @return mass spec file format
     */
    private static MassSpecFileFormat detectFormat(String content) {
        MassSpecFileFormat format = null;

        if (MassSpecFileRegx.PRIDE_XML_PATTERN.matcher(content).find()) {
            format = PRIDE;
        } else if (MassSpecFileRegx.INDEXED_MZML_PATTERN.matcher(content).find()) {
            format = INDEXED_MZML;
        } else if (MassSpecFileRegx.MZML_PATTERN.matcher(content).find()) {
             format = MZML;
        } else if (MassSpecFileRegx.MZIDENTML_PATTERN.matcher(content).find()) {
            format = MZIDENTML;
        } else if (MassSpecFileRegx.MZXML_PATTERN.matcher(content).find()) {
            format = MZXML;
        } else if (MassSpecFileRegx.MZDATA_PATTERN.matcher(content).find()) {
            format = MZDATA;
        }

        return format;
    }
}
