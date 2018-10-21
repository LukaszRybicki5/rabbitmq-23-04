package sender.filesController.propertiesChekers;
/*
Sprawdzamy czy podane rozszerzenie to csv lub tsv
 */
public class FileSuffixChecker implements FileSuffixVerifier{

    public boolean isSuffixAdequate(String suffix) {

         String[] SuffixTable = new String[]{"csv","tsv"};

         if((suffix.equalsIgnoreCase(SuffixTable[0])) || (suffix.equalsIgnoreCase(SuffixTable[1]))){
             return true;
         }
        return false;
    }
}
