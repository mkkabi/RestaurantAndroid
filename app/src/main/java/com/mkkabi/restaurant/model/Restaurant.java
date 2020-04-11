package com.mkkabi.restaurant.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Restaurant {
    private String name, shortDescription, id, imageUrl;

    private List<Klient> workers = new ArrayList<>();
    private Set<MenuCategory> menuCategories = new HashSet<>();
    private Set<Strava> menu = new HashSet<>();
    private List<Zakaz> zakazi = new ArrayList<>();

    public Restaurant(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public List<Strava> naitiStraviPoImeni(String name) {
        List<Strava> result = new ArrayList<>();
        for(Strava strava : menu){
            if(strava.getName().toLowerCase().contains(name.toLowerCase())){
                result.add(strava);
            }
        }
        return result;
    }

    public Set<Strava> dobavitStraviVMenu(Worker povar, Strava... strava) {
        if(this.workers.contains(povar) && povar instanceof Povar) {
            this.menu.addAll(Arrays.asList(strava));
            return this.menu;
        }else{
            throw new UnsupportedOperationException("Заказы могут добавлять только повара");
        }
    }


    public Zakaz dobavitZakaz(Zakaz zakaz) {
        this.zakazi.add(zakaz);
        return zakaz;
    }

    public List<Zakaz> naitiZakaziPoPolzovatelu(Klient klient){
        List<Zakaz> result = new ArrayList<>();
        for(Zakaz zakaz : zakazi){
            if(zakaz.getKlient().equals(klient)){
                result.add(zakaz);
            }
        }
        return result;
    }

    public List<Zakaz> naitiZakaziPoStatusu(Worker worker, StatusZakaza statusZakaza){
        List<Zakaz> result = new ArrayList<>();
        for (Zakaz zakaz : zakazi){
            if(this.workers.contains(worker) && zakaz.getStatus().equals(statusZakaza)){
                result.add(zakaz);
            }
        }
        return result;
    }

    public List<Zakaz> showAllOrders(Director director){
        if(workers.contains(director) && director instanceof Director){
            return this.zakazi;
        }
        return new ArrayList<Zakaz>();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getshortDescription() {
        return shortDescription;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setshortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }


}
