package receiver.jsonParser;

import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
/*
Zamieniamy nazwę pliku nazwe pliku i listę w plik JSON
 */
public class JSONFileMaker {

    public void putDataToPersonList(ArrayList arrayList, String FileName, String Path) {

        JSONObject obj = new JSONObject();
        obj.put(FileName, arrayList);
        StringBuilder stringBuilder = new StringBuilder(Path);
        String newPath = stringBuilder.append(FileName).append(".json") .toString();

        try (FileWriter file = new FileWriter(newPath)) {
            file.write(obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


