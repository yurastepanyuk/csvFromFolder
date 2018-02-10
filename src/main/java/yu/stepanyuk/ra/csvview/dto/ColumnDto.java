package yu.stepanyuk.ra.csvview.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ColumnDto {
    String columnName;
    String type;
    int epmties;
}
