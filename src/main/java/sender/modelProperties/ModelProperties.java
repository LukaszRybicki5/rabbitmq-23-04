package sender.modelProperties;

import java.util.List;

/*
Klasa opisuje właściwości  ModelProperties czyli ustawienia programu
 */
public class ModelProperties {

    private List<String> PathToSearch;
    private int searchInterval;
    private String suffixOfFiles;
    private char punctuationMark;
    private int batchSize;

    public ModelProperties() {
    }

    public ModelProperties(List<String> pathToSearch, int searchInterval, String suffixOfFiles, char punctuationMark, int batchSize) {
        PathToSearch = pathToSearch;
        this.searchInterval = searchInterval;
        this.suffixOfFiles = suffixOfFiles;
        this.punctuationMark = punctuationMark;
        this.batchSize = batchSize;
    }

    public List<String> getPathToSearch() {
        return PathToSearch;
    }

    public void setPathToSearch(List<String> pathToSearch) {
        PathToSearch = pathToSearch;
    }

    public int getSearchInterval() {
        return this.searchInterval;
    }

    public void setSearchInterval(int searchInterval) {
        this.searchInterval = searchInterval;
    }

    public String getSuffixOfFiles() {
        return suffixOfFiles;
    }

    public void setSuffixOfFiles(String suffixOfFiles) {
        this.suffixOfFiles = suffixOfFiles;
    }

    public char getPunctuationMark() {
        return punctuationMark;
    }

    public void setPunctuationMark(char punctuationMark) {
        this.punctuationMark = punctuationMark;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }
}
