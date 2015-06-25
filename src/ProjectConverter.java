
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class ProjectConverter {

    private RuleSet ruleSet;
    private List<ConversionException> exception;
    private File Vaadin7Project;


    /**
     * Ask for concrete matcher implementation depending on the conversion mode
     * Ask for user confirmation to convert the selected source directory if valid
     * Ask for user confirmation in case of number of files to convert > 1000
     */
    public ProjectConverter(int conversionType) {
        //this.progressListener = progressListener;
        ruleSet = RuleSetFactory.getMatcherImpl(conversionType);
        if (ruleSet == null) {
            addException(new ConversionException(ConversionException.NOT_IMPLEMENTED));
        }
    }

    public void convertProject(File folder) {
        String[] splitedPath = folder.getPath().split("/");
        String newPath = "/";
        for (int i = 1; i < splitedPath.length - 1; i++) {
            newPath += splitedPath[i] + "/";
        }
        newPath += "VAADIN7_Project";
        Vaadin7Project = new File(newPath);
        if (!Vaadin7Project.exists()) {
            //Vaadin7Project.mkdirs();
            try {
                FileUtils.copyDirectory(folder, Vaadin7Project);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            try {
                System.out.print("The directory 'Vaadin7_Project' already exists. Removing it... Then... ");
                FileUtils.deleteDirectory(Vaadin7Project);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                splitedPath = folder.getPath().split("/");
                newPath = "/";
                System.out.println(" \n Creating new Directory 'Vaadin7_Project' and Converting again ");
                for (int i = 1; i < splitedPath.length - 1; i++) {
                    newPath += splitedPath[i] + "/";
                }
                newPath += "VAADIN7_Project";
                Vaadin7Project = new File(newPath);
                FileUtils.copyDirectory(folder, Vaadin7Project);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // System.out.print("The directory 'Vaadin7_Project' already exists. Remove it or rename please.");
            //System.exit(0);
        }
        FileSelector fs = new FileSelector();
        List<File> fileList = fs.selectJavaFilesInFolder(Vaadin7Project);

        scanFileList(fileList);

    }

    /**
     * Convert a list of files
     *
     * @param lstFiles
     */
    private void scanFileList(List<File> lstFiles) {
        //progressListener.onFileScanBegin();
        Iterator<File> itFile = lstFiles.iterator();
        while (itFile.hasNext()) {
            File currentFile = itFile.next();
            // progressListener.onFileScan(currentFile);
            scanFile(currentFile);
        }
    }

    /**
     * Convert the specified file Read each line and ask matcher implementation
     * for conversion Rewrite the line returned by matcher
     */
    private void scanFile(File file) {
        try {
            InplaceFileConverter fc = new InplaceFileConverter(ruleSet);
            fc.convert(file);
        } catch (IOException exc) {
            addException(new ConversionException(exc.toString()));
        }
    }

    public void addException(ConversionException exc) {
        if (exception == null) {
            exception = new ArrayList<ConversionException>();
        }
        exception.add(exc);
    }

    public void printException() {
        if (exception != null) {
            Iterator<ConversionException> iterator = exception.iterator();
            while (iterator.hasNext()) {
                ConversionException exc = (ConversionException) iterator.next();
                exc.print();
            }
            exception = null;
        }
    }
}