package com.example.demo.Server;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

public class Sale {

    public static class Prize {
        private int id;
        private String description;
        public Prize(int id, String description) {
            this.description = description;
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }
    }

    public static class Participant {
        private String name;
        private int id;

        public Participant(int id, String name) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public int getId() {
            return id;
        }
    }

    private static int id = 1;
    private static int idParticipants = 1;
    private static int idPrize = 1;
    private int idSale;
    private String name;
    private String description;

    private Participant winner;
    private Prize prize;

    private List<Prize> prizes = new ArrayList<>();
    private List<Participant> participants = new ArrayList<>();


    public Sale(String name, String description) {
        this.idSale = id;
        this.name = name;
        this.description = description;
        id++;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Sale.id = id;
    }

    public int getIdSale() {
        return idSale;
    }

    public void setIdSale(int idSale) {
        this.idSale = idSale;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int addParticipant(String name) {
        this.participants.add(new Participant(this.idParticipants, name));
        idParticipants++;
        return idParticipants - 1;
    }

    public void deleteParticipant(int idParticipants) {
        int num = -1;
        for (int i = 0; i < participants.size(); i++) {
            if (participants.get(i).id == idParticipants) {
                num = i;
                break;
            }
        }
        if (num == -1) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }
        this.participants.remove(num);
    }

    public int addPrize(String name) {
        this.prizes.add(new Prize(this.idPrize, name));
        idPrize++;
        return idPrize - 1;
    }

    public void deletePrize(int idPrize) {
        int num = -1;
        for (int i = 0; i < prizes.size(); i++) {
            if (prizes.get(i).id == idPrize) {
                num = i;
                break;
            }
        }
        if (num == -1) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find resource");
        }
        this.prizes.remove(num);
    }

    public List<Prize> getPrizes() {
        return prizes;
    }

    public List<Participant> getParticipants() {
        return participants;
    }
}
