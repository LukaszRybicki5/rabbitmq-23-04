package sender.filesController;

import receiver.messageObject.MessageObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/*
Zmieniamy plik tsv lub csv w listę o określonej długości batch size
 */
public class ExcelFilesToMessageObjectConverter {

    public MessageObject getMessageObjectFromExcell(String Path, String FileName, char PunctuationMark, int batchSize) throws FileNotFoundException {

        File file = new File(Path);
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter(String.valueOf(PunctuationMark));

        MessageObject messageObject = new MessageObject();
        messageObject.setFileName(FileName);
        ArrayList<String> listOfFields = new ArrayList<>(batchSize + 1);
        int count = 0;

        while (scanner.hasNext() && count < batchSize) {
            String data = scanner.nextLine();
            count++;
            if (count < batchSize) {
                listOfFields.add(data);
            } else {
                System.out.println("Wielkość pliku została zmniejszona!");
            }
        }
        messageObject.setCSVList(listOfFields);
        System.out.println(messageObject.getFileName());
        scanner.close();
        return messageObject;
    }
}

