package yu.stepanyuk.ra.csvview.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import yu.stepanyuk.ra.csvview.dto.CsvDto;
import yu.stepanyuk.ra.csvview.dto.ErrorDto;
import yu.stepanyuk.ra.csvview.service.CsvService;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CsvConrtroller {

    @Autowired
    private CsvService csvService;

    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public String getHelloWorld(){
        return "helllo from RestController";
    }

    @GetMapping("/showcsv/{folder}")
    public String getDataOfCsv(@PathVariable String folder){

        File csvFolder = new File(folder);
        Gson gson = new Gson();

        if (!csvFolder.exists()) {
            String message = "Folder name isn't correct! There is no folder for this name. Folder need be near the project";
            ErrorDto errorDto = new ErrorDto();
            errorDto.setMessage(message);
            errorDto.setStatusCode(String.valueOf(HttpServletResponse.SC_INTERNAL_SERVER_ERROR));
            return gson.toJson(errorDto);
        }

        List<CsvDto> res = null;
        try {
            res = csvService.getDataOfCsv(folder);
        } catch (IOException e) {
            String message = "There was some problem in the server." + e.getMessage();
            return gson.toJson(message);
        }

        return gson.toJson(res);

    }

}
