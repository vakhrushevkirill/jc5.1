import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "res" + File.separator + "data.csv";
        String jsonFile = "res" + File.separator + "json.json";
        List<Employee> list = parseCSV(columnMapping, fileName);
        String json = listToJson(list);
        writeString(json, jsonFile);
    }

    private static void writeString(String json, String jsonFile) {
        File file = new File(jsonFile);
        try (FileWriter writer = new FileWriter(file, false)){
            writer.write(json.toString());
            writer.flush();
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    private static String listToJson(List<Employee> list) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Type listType = new TypeToken<List<Employee>>() {}.getType();
        String json = gson.toJson(list, listType);
        System.out.println(json);
        return json;
    }

    private static List<Employee> parseCSV(String[] columnMapping, String fileName) {
        try (CSVReader reader = new CSVReader(new FileReader(fileName))){
            ColumnPositionMappingStrategy columnPositionMappingStrategy = new ColumnPositionMappingStrategy();
            columnPositionMappingStrategy.setType(Employee.class);
            columnPositionMappingStrategy.setColumnMapping(columnMapping);
            CsvToBean csv = new CsvToBeanBuilder(reader).withMappingStrategy(columnPositionMappingStrategy).build();
            List<Employee> employees = csv.parse();
            return employees;
        } catch (Exception ex){

        }
        return null;
    }
}
