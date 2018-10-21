package sender;

import sender.filesController.ExcelFilesSearcher;
import sender.filesController.PropertiesFileReader;
import sender.modelProperties.ModelProperties;

import java.util.Timer;
import java.util.TimerTask;

/*
Klasa rozruchowa wysyłająca pliki, ustawiamy tu interwał
 */
public class MessageSender {

    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task = new ExcelFilesSearcher();

        PropertiesFileReader propertiesFileReader = new PropertiesFileReader();
        ModelProperties modelProperties = propertiesFileReader.getPropertiedFromFile();
        timer.schedule(task, 0, modelProperties.getSearchInterval() * 100 + 1);
    }
}