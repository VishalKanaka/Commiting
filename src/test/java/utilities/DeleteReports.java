package utilities;

import java.io.File;

/**
 * This class provides a method to delete the test reports folder and its contents.
 */
public class DeleteReports {

    /**
     * Deletes the test reports folder and its contents if they exist.
     */
    public static void deleteTestReportsFolder() {
        // Get the project path
        String projectPath = System.getProperty("user.dir");
        // Define the path for the test reports folder
        String propertyFile = projectPath + "/test-output";
        // Create a File object for the test reports folder
        File testReportsFolder = new File(propertyFile);

        // Check if the test reports folder exists
        if (testReportsFolder.exists()) {
            // Call deleteFolder method to delete the test reports folder and its contents
            deleteFolder(testReportsFolder);
            System.out.println("Existing test-reports folder deleted.");
        } else {
            System.out.println("Not Present");
        }

        // Define the path for the test reports zip folder
        String propertyFileZip = projectPath + "/test-output.zip";
        // Create a File object for the test reports zip folder
        File testReportsFolderZip = new File(propertyFileZip);

        // Check if the test reports zip folder exists
        if (testReportsFolderZip.exists()) {
            // Call deleteFolder method to delete the test reports zip folder and its contents
            deleteFolder(testReportsFolderZip);
            System.out.println("Existing test-reports zip folder deleted.");
        } else {
            System.out.println("Not Present");
        }
    }

    /**
     * Recursively deletes a folder and its contents.
     * @param folder The folder to be deleted.
     */
    private static void deleteFolder(File folder) {
        // Get all files and subdirectories in the folder
        File[] files = folder.listFiles();
        // Check if the folder is not empty
        if (files != null) {
            
            for (File file : files) {
                // Check if the file is a directory
                if (file.isDirectory()) {
                    // Recursive call to delete subdirectory
                    deleteFolder(file);
                } else {
                    // Delete the file
                    file.delete();
                }
            }
        }
        // Delete the empty folder
        folder.delete();
    }
}
