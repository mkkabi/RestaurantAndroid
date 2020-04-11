package com.mkkabi.restaurant.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Posetitel {


    public List<Zakaz> posmotretSvoiZakazi(IRestoran restoran);
    public Zakaz ocenitObslugivanie(Zakaz zakaz, int ocenka);
    public Strava dobavitDopIngredienti(Strava strava, Map<Ingredient, Integer> dopolnitelnieIngredienti);
    public Zakaz razmestitZakaz(IRestoran restoran) throws UnsupportedOperationException;
    public Map<Strava, Integer> dobavitStravuVkorzinu(IRestoran restoran, Strava... strava);
    public Zakaz dobavitStravuVZakaz(Zakaz currentZakaz, Strava... stravai);
    public Set<Strava> posmotretMenu(IRestoran restoran);
    public Map<Strava, Integer> getKorzina(IRestoran restoran);

}
