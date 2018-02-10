package yu.stepanyuk.ra.csvview.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CsvDto {
    String name;
    Integer columns;
    Integer rows;
    Integer emptys;
    String separator;
    ColumnDto[] columnsData;
}
