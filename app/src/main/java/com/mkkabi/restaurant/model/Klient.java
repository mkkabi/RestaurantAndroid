package com.mkkabi.restaurant.model;

import java.util.*;

public class Klient implements Posetitel{
    private String login, pass, id, name, email, telephone;
//    private Map<Strava, Integer> korzina = new HashMap<>();
    private Map<IRestoran, Map<Strava, Integer>> korzina = new HashMap<>();

    public Klient(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }

    public List<Zakaz> posmotretSvoiZakazi(IRestoran restoran) {
        return restoran.naitiZakaziPoPolzovatelu(this);
    }

    public Zakaz ocenitObslugivanie(Zakaz zakaz, int ocenka) {
        if (zakaz.getKlient() == this) {
            return zakaz.setOcenkaPosetitelia(ocenka);
        } else {
            throw new UnsupportedOperationException("Заказ может быть ощенен только пользователем, который его разместил");
        }
    }

    /**
     * мне кажется, нужно явно создавать новый объект, иначе изменения ингредиентов затронут Страву в Меню
     */
    public Strava dobavitDopIngredienti(Strava strava, Map<Ingredient, Integer> dopolnitelnieIngredienti) {
//        Strava result = new Strava(strava.getName(), strava.getId(), strava.getPrice()).setIngredienti(strava.pokazatIngredienti());
//        result.setDopolnitelnieIngredienti(dopolnitelnieIngredienti);
        return null;
    }

    public Zakaz razmestitZakaz(IRestoran restoran) throws UnsupportedOperationException {
        if (this.korzina != null && this.korzina.size() > 0) {
            Zakaz result = new Zakaz(String.valueOf(restoran.getNomerPoslednegoZakaza() +1), this, korzina.get(restoran));
            result = restoran.dobavitZakaz(result);
            korzina.clear();
            return result;
        } else {
            throw new UnsupportedOperationException("Empty shopping cart");
        }
    }

    public Map<Strava, Integer> dobavitStravuVkorzinu(IRestoran restoran, Strava... strava) {
        for (Strava s : strava) {
            if (korzina.get(restoran).containsKey(s)) {
                korzina.get(restoran).put(s, korzina.get(restoran).get(s) + 1);
            } else {
                korzina.get(restoran).put(s, 1);
            }
        }
        return korzina.get(restoran);
    }

    public Zakaz dobavitStravuVZakaz(Zakaz currentZakaz, Strava... stravai) {
        currentZakaz.dobavitStravuVZakaz(stravai);
        return currentZakaz;
    }

    public Set<Strava> posmotretMenu(IRestoran restoran) {
        return restoran.getMenu();
    }

    public Map<Strava, Integer> getKorzina(IRestoran restoran) {
        return korzina.get(restoran);
    }
}
