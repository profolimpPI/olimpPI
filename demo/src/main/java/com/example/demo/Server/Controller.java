package com.example.demo.Server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class Controller {

    private ServiceDemo serviceDemo;
    private List<Sale> sales = new ArrayList<>();

    @Autowired
    public void setServiceThree(ServiceDemo serviceThree) {
        this.serviceDemo = serviceThree;
    }

    @PostMapping("/promo")
    public int pushProme(@RequestBody String ob) throws org.json.simple.parser.ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(ob);
        String name = (String) jsonObject.get("name");
        if (name == null || name.equals("")) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }
        String description = "";
        description = (String) jsonObject.get("description");
        sales.add(new Sale(name, description));
        return sales.get(sales.size() - 1).getIdSale();
    }

    @GetMapping("/promo")
    public String getProme() throws InterruptedException, ParseException, org.json.simple.parser.ParseException {
        JSONArray saleJSON = new JSONArray();
        for (Sale sale : sales) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", sale.getIdSale());
            jsonObject.put("name", sale.getName());
            jsonObject.put("description", sale.getDescription());
            saleJSON.add(jsonObject);
        }
        return saleJSON.toString();
    }

    @PutMapping("/promo/{id}")
    public String changeProme(@RequestBody String ob, @PathVariable("id") int id) throws InterruptedException, ParseException, org.json.simple.parser.ParseException {
        int num = -1;
        for (int i = 0; i < sales.size(); i++) {
            if (sales.get(i).getIdSale() == id) {
                num = i;
                break;
            }
        }
        if (num == -1) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(ob);
        String name = (String) jsonObject.get("name");
        if (name == null || name.equals("")) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }
        String description = "";
        description = (String) jsonObject.get("description");
        sales.get(num).setName(name);
        sales.get(num).setDescription(description);
        return "";
    }

    @DeleteMapping("/promo/{id}")
    public void deleteProme(@RequestBody String ob, @PathVariable("id") int id) throws InterruptedException, ParseException, org.json.simple.parser.ParseException {
        int num = -1;
        for (int i = 0; i < sales.size(); i++) {
            if (sales.get(i).getIdSale() == id) {
                num = i;
                break;
            }
        }
        if (num == -1) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }
        sales.remove(num);
    }

    @PostMapping("/promo/{id}/participant")
    public int pushParticipant(@RequestBody String ob, @PathVariable("id") int id) throws org.json.simple.parser.ParseException {
        int num = -1;
        for (int i = 0; i < sales.size(); i++) {
            if (sales.get(i).getIdSale() == id) {
                num = i;
                break;
            }
        }
        if (num == -1) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(ob);
        String name = (String) jsonObject.get("name");
        if (name == null || name.equals("")) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }
        return sales.get(num).addParticipant(name);
    }

    @DeleteMapping("/promo/{id}/participant/{idPart}")
    public void pushParticipant(@RequestBody String ob, @PathVariable("id") int id, @PathVariable("idPart") int idPart) throws org.json.simple.parser.ParseException {
        int num = -1;
        for (int i = 0; i < sales.size(); i++) {
            if (sales.get(i).getIdSale() == id) {
                num = i;
                break;
            }
        }
        if (num == -1) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }
        sales.get(num).deleteParticipant(idPart);
    }

    @PostMapping("/promo/{id}/prize")
    public int pushPrize(@RequestBody String ob, @PathVariable("id") int id) throws org.json.simple.parser.ParseException {
        int num = -1;
        for (int i = 0; i < sales.size(); i++) {
            if (sales.get(i).getIdSale() == id) {
                num = i;
                break;
            }
        }
        if (num == -1) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(ob);
        String description = (String) jsonObject.get("description");
        if (description == null || description.equals("")) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }
        return sales.get(num).addPrize(description);
    }

    @DeleteMapping("/promo/{id}/prize/{idPrize}")
    public void pushPrize(@RequestBody String ob, @PathVariable("id") int id, @PathVariable("idPart") int idPrize) throws org.json.simple.parser.ParseException {
        int num = -1;
        for (int i = 0; i < sales.size(); i++) {
            if (sales.get(i).getIdSale() == id) {
                num = i;
                break;
            }
        }
        if (num == -1) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }
        sales.get(num).deletePrize(idPrize);
    }

    @GetMapping("/promo/{id}")
    public String getPromeById(@PathVariable("id") int id) throws InterruptedException, ParseException, org.json.simple.parser.ParseException {
        int num = -1;
        for (int i = 0; i < sales.size(); i++) {
            if (sales.get(i).getIdSale() == id) {
                num = i;
                break;
            }
        }
        if (num == -1) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", sales.get(num).getIdSale());
        jsonObject.put("name", sales.get(num).getName());
        jsonObject.put("description", sales.get(num).getDescription());
        JSONArray prize = new JSONArray();
        for (int i = 0; i < sales.get(num).getPrizes().size(); i++) {
            JSONObject jsonPrize = new JSONObject();
            jsonObject.put("id", sales.get(num).getPrizes().get(i).getId());
            jsonObject.put("description", sales.get(num).getPrizes().get(i).getDescription());
            prize.add(jsonPrize);
        }
        jsonObject.put("prizes", prize);
        JSONArray part = new JSONArray();
        for (int i = 0; i < sales.get(num).getParticipants().size(); i++) {
            JSONObject jsonPar = new JSONObject();
            jsonObject.put("id", sales.get(num).getParticipants().get(i).getId());
            jsonObject.put("name", sales.get(num).getParticipants().get(i).getName());
            prize.add(jsonPar);
        }
        jsonObject.put("participants", prize);
        return jsonObject.toString();
    }

}
