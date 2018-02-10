package yu.stepanyuk.ra.csvview.service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import yu.stepanyuk.ra.csvview.dto.ColumnDto;
import yu.stepanyuk.ra.csvview.dto.CsvDto;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CsvService {

    private final String STRING_REGEX = "[a-zA-Z]+";
    private final String INTEGER_REGEX = "[0-9]+";
    private final String FLOAT_REGEX = "([0-9]+.[0-9]+)+";

    private Pattern stringValidation;
    private Pattern integerValidation;
    private Pattern floatValidation;

    public CsvService() {
        stringValidation = Pattern.compile(STRING_REGEX);
        integerValidation = Pattern.compile(INTEGER_REGEX);
        floatValidation = Pattern.compile(FLOAT_REGEX);
    }

    public List<CsvDto> getDataOfCsv(@NonNull String folder) {

        List<CsvDto> data = new ArrayList<>();

        File csvFolder = new File(folder);

        CsvMapper mapper = new CsvMapper();
        mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);

        FilenameFilter filter = new MyFilter();
        String listFiles[] = csvFolder.list(filter);

        for (String curFile: listFiles) {
            System.out.println(curFile);

            File csvFile = new File(folder, curFile) ;

            String separator = guessSeparator(csvFile);

            CsvSchema schema = mapper.schemaFor(String[].class).withColumnSeparator(separator.charAt(0));

            MappingIterator<String[]> it = null;
            try {
                it = mapper.readerFor(String[].class).with(schema).readValues(csvFile);

            } catch (IOException e) {
                e.printStackTrace();
            }

            Map<Integer, ColumnDto> columnsData = new HashMap<>();

            String name = curFile;
            Integer columns = 0;
            Integer rows = -1;
            Integer emptys = 0;

            if (it != null && it.hasNext()) {

                for (MappingIterator<String[]> it1 = it; it1.hasNext(); ) {
                    String[] row = it1.next();

                    rows += 1;

                    if (rows == 0) {
                        columns = row.length;
                        fillColumn(row, columnsData);
                        continue;
                    }

                    if (rows == 1) {
                        recognizeDataOfColumn(row, columnsData);
                    }

                    for (int i = 0; i < row.length; i++) {
                        if (checkEmptyValue(i, row, columnsData)) {
                            emptys += 1;
                        }
                    }

                }

            }

            data.add(new CsvDto(name, columns, rows, emptys, separator, columnsData.values().toArray(new ColumnDto[0])));

        }

        return data;
    }

    private String guessSeparator(File csvFile) {
        try (BufferedReader reader = Files.newBufferedReader(csvFile.toPath())) {
            String line;
            if((line = reader.readLine()) != null ){
                String[] columns = line.split(",");
                if (columns.length >= 2) {
                    return ",";
                }

                columns = line.split(";");
                if (columns.length >= 2) {
                    return ";";
                }

                columns = line.split(" ");
                if (columns.length >= 2) {
                    return " ";
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ",";
    }

    private boolean checkEmptyValue(int numColumn, String[] row, Map<Integer, ColumnDto> columnsData) {
        String valueRow = row[numColumn];
        if (valueRow.equals("")) {
            ColumnDto column = columnsData.get(Integer.valueOf(numColumn));
            column.setEpmties(column.getEpmties()+1);
            return true;
        }
        return false;
    }

    private void recognizeDataOfColumn(String[] row, Map<Integer, ColumnDto> columnsData) {
        for (int i = 0; i < row.length; i++) {
            String typeOfValue = recognizeValue(row[i]);
            ColumnDto column = columnsData.get(Integer.valueOf(i));
            column.setType(typeOfValue);
        }
    }

    private void fillColumn(String[] row, Map<Integer, ColumnDto> columnsData) {
        for (int i = 0; i < row.length; i++) {
            ColumnDto newColumn = new ColumnDto();
            newColumn.setColumnName(row[i]);
            columnsData.put(Integer.valueOf(i), newColumn);
        }
    }


    private String recognizeValue(@NonNull String value){

        Matcher matcher = stringValidation.matcher(value);

        boolean fieldResult = matcher.matches();
        if (fieldResult) {
            return "String";
        }

        Matcher matcherFloat = floatValidation.matcher(value);
        fieldResult = matcherFloat.matches();
        if (fieldResult) {
            return "Float";
        }

        Matcher matcherInt = integerValidation.matcher(value);
        fieldResult = matcherInt.matches();
        if (fieldResult) {
            return "Integer";
        }

        return "Undefined";

    }

    class MyFilter implements FilenameFilter {

        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(".csv");
        }
    }
}
