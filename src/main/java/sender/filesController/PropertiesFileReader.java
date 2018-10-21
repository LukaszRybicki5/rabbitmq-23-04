package sender.filesController;

import sender.filesController.propertiesChekers.*;
import sender.modelProperties.ModelProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/*
importujemy plik data.property, zmieniamy na Obiekt Property
testujemy zawartość i zwracamy Obiekt ModelProperties
 */
public class PropertiesFileReader {

    public ModelProperties getPropertiedFromFile() {

        Properties properties = new Properties();
        ModelProperties modelProperties = new ModelProperties();
        List<String> DirPaths = new ArrayList<>();
        PathSuffixChecker pathChecker = new PathSuffixChecker();
        try {
            properties.load(new FileInputStream("./data.properties"));
            String PathsToSplit = properties.getProperty("PathToSearch");
            String[] arraySplitedPaths = PathsToSplit.split(",");

            for (String SplidetString : arraySplitedPaths) {
                String PathToAdd = pathChecker.isAdequate(SplidetString);
                DirPaths.add(PathToAdd);
            }
            modelProperties.setPathToSearch(DirPaths);


            if(properties.getProperty("searchInterval").isEmpty() ||
                    properties.getProperty("batchSize").isEmpty() ||
                    properties.getProperty("suffixOfFiles").isEmpty() ||
                    properties.getProperty("punctuationMark").isEmpty() ||
                    DirPaths.isEmpty()){
                System.out.println("Uzupełnij plik data.properties!!!");
                System.exit(0);
            }

            ValueVerifier valueVerifier = new IntervalAndBatchSizeChecker();


            int searchInterval = Integer.parseInt(properties.getProperty("searchInterval").trim());
            if (valueVerifier.isIntegerAdequate(searchInterval)) {
                modelProperties.setSearchInterval(searchInterval);
            } else {
                System.out.println("Wprowadzono niepoprawną długość interwału!!!" +
                        "\nProponowane długość zawiera się między 0 a 1000 sekund.");
                System.exit(0);
            }

            int batchSize = Integer.parseInt(properties.getProperty("batchSize").trim());

            if (valueVerifier.isIntegerAdequate(batchSize)) {
                modelProperties.setBatchSize(batchSize);
            } else {
                System.out.println("Wprowadzono niepoprawną długość batch size!!!" +
                        "\nProponowane długość zawiera sie między 0 a 1000 linijek.");
                System.exit(0);
            }

            String Suffix = properties.getProperty("suffixOfFiles").trim();

            FileSuffixVerifier fileSuffixVerifier = new FileSuffixChecker();

            if (fileSuffixVerifier.isSuffixAdequate(Suffix)) {
                modelProperties.setSuffixOfFiles(Suffix);
            } else {
                System.out.println("Wprowadzono niepoprawny rodzaj rozszerzenia!!!" +
                        "\nProponowane rozszerzenie to csv lub tsv");
                System.exit(0);
            }

            modelProperties.setPunctuationMark(properties.getProperty("punctuationMark").trim().charAt(0));
        } catch (IOException | NumberFormatException ex) {
            System.out.println("Wystąpił błąd najprawdopodobniej związany z plikiem data.properties!!!");
            System.exit(0);
        }
        return modelProperties;
    }
}

