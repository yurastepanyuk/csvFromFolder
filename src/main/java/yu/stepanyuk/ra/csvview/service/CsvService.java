package yu.stepanyuk.ra.csvview.service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import yu.stepanyuk.ra.csvview.dto.CsvDto;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvService {

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
            MappingIterator<String[]> it = null;
            try {
                it = mapper.readerFor(String[].class).readValues(csvFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

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
                    }

                    for (String valueRow:row) {
                        if (valueRow.equals("")) {
                            emptys += 1;
                        }
                    }

                    System.out.println(row.toString());
                }

            }

            data.add(new CsvDto(name, columns, rows, emptys));

        }

        return data;
    }

    class MyFilter implements FilenameFilter {

        @Override
        public boolean accept(File dir, String name) {
            return name.endsWith(".csv");
        }
    }
}
