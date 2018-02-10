package yu.stepanyuk.ra.csvview.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yu.stepanyuk.ra.csvview.dto.CsvDto;
import yu.stepanyuk.ra.csvview.service.CsvService;

import java.io.File;
import java.util.ArrayList;

@RestController
public class CsvConrtroller {

    @Autowired
    private CsvService csvService;

    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public String getHelloWorld(){
        return "helllo from RestController";
    }

    @GetMapping("/showcsv/{folder}")
    public String getDataSensors(@PathVariable String folder){

        File csvFolder = new File(folder);
        Gson gson = new Gson();

        if (!csvFolder.exists()) {
            String message = "Folder name isn't correct! There is no folder for this name. Folder need be in the project>";
            return gson.toJson(message);
        }

        ArrayList<CsvDto> res = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        for (CsvDto curCsv: csvService.getDataOfCsv(folder)
                ) {
            res.add(curCsv);
        }
        try {
            return gson.toJson(res);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

}
