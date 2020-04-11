package com.mkkabi.restaurant.model;

import java.util.List;
import java.util.Set;

public interface IRestoran {

    public List<Strava> naitiStraviPoImeni(String name);
    public Zakaz dobavitZakaz(Zakaz zakaz);

    public Set<Strava> dobavitStraviVMenu(Worker povar, Strava... strava);

    public List<Zakaz> naitiZakaziPoPolzovatelu(Klient klient);

    public List<Zakaz> naitiZakaziPoStatusu(Worker worker, StatusZakaza statusZakaza);

    public Set<Strava> getMenu();

    public List<Zakaz> getZakazi(Worker worker);

    public String getName();

    public int getNomerPoslednegoZakaza();


}
