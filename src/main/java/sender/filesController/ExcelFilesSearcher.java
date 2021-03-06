package sender.filesController;

import receiver.messageObject.MessageObject;
import sender.modelProperties.ModelProperties;
import sender.programController.MessageObjectSender;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.TimeoutException;

/*
Klasa wyszukuje wszystkie pliki .csv w katalogu i subkatalogach
 */
public class ExcelFilesSearcher extends TimerTask {

    public void run() {
        System.out.println("#################################################");
        PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
        ModelProperties modelProperties = propertiesFileReader.getPropertiedFromFile();
        char PunctuationMark = modelProperties.getPunctuationMark();
        String SuffixOfFile = modelProperties.getSuffixOfFiles();
        int batchSize = modelProperties.getBatchSize();
        List<String> DirectionPaths = modelProperties.getPathToSearch();

        for (String Direction : DirectionPaths) {
            try {
                getAllFilesInDirectory(Direction, SuffixOfFile, PunctuationMark, batchSize);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void getAllFilesInDirectory(String Direction, String SuffixOfFile, char PunctuationMark, int batchSize)
            throws FileNotFoundException {

        File[] fileList = getFileListInCatalog(Direction, SuffixOfFile);
        ExcelFilesToMessageObjectConverter FilesToJSONConverter = new ExcelFilesToMessageObjectConverter();

        //tu wyszukujemy pliki w folderze, zmieniamy je na json i wysyłamy
        for (File file : fileList) {
            System.out.println("File: " + file.getName());
            StringBuilder stringBuilder = new StringBuilder(Direction);
            String newDirPathToConvert = stringBuilder.append("\\").append(file.getName()).toString();

            StringBuilder fileName = new StringBuilder(file.getName());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH-mm-ss-SSSS");
            String NameForFile = simpleDateFormat.format(new Date()).toString();
            String newNameForFile = fileName.delete(fileName.length() - 4, fileName.length()).append("_").append(NameForFile).toString();

            //Zapisujemy .csv w object
            MessageObject messageObject = FilesToJSONConverter.getMessageObjectFromExcell(newDirPathToConvert, newNameForFile, PunctuationMark, batchSize);

            MessageObjectSender messageObjectSender = new MessageObjectSender();
            try {
                messageObjectSender.sendingMessageObject(messageObject);
            } catch (IOException | TimeoutException e) {
                e.printStackTrace();
            }
        }

        List<String> directoryLists = getDirectoryPath(Direction);
        for (String paths : directoryLists) {
            System.out.println(paths);
            getAllFilesInDirectory(paths, SuffixOfFile, PunctuationMark, batchSize);
        }
    }

    private File[] getFileListInCatalog(String DirPath, String SuffixOfFile) {
        File dir = new File(DirPath);
        File[] fileList = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(SuffixOfFile);
            }
        });
        return fileList;
    }

    private List<String> getDirectoryPath(String dirPath) {
        File file = new File(dirPath);
        String[] names = file.list();
        List<String> directoryList = new ArrayList<>();

        for (String name : names) {
            if (new File(dirPath + name).isDirectory()) {
                StringBuilder stringBuilder = new StringBuilder(dirPath);
                String newDirPath = stringBuilder.append(name).append("\\").toString();
                directoryList.add(newDirPath);
            }
        }
        return directoryList;
    }
}

