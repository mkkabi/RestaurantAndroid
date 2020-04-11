package com.mkkabi.restaurant.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Zakaz implements Comparable<Zakaz> {
    private LocalDate data;
    private String nomerZakaza;
    private Klient klient;
    private Oficiant oficiant;
    private StatusZakaza status;
    private Map<Strava, Integer> listStrav = new HashMap<>();
    private int ocenkaPosetitelia;

    public Zakaz(String nomerZakaza, Klient klient, Map<Strava, Integer> korzina) {}
//    public Zakaz(String nomerZakaza, Klient klient, Map<Strava, Integer> korzina) {
//        this.data = LocalDate.now();
//        this.nomerZakaza = nomerZakaza;
//        this.klient = klient;
//        this.status = StatusZakaza.RAZMESHEN;
//        korzina.forEach((s, i) -> listStrav.put(s, i));
//    }
//
//    public int poschitatStoimostZakaza() {
//        int[] result = new int[1];
//        this.listStrav.entrySet().stream().map(entry -> {
//            return entry.getKey().poschitatStoimostDliaKlienta() * entry.getValue();
//        }).forEach(item -> result[0] += item);
//        return result[0];
//    }

    // добавляет новое блюдо или изменяет текущее количество в заказе
    public Zakaz dobavitStravuVZakaz(Strava... stravi) {
        if (this.status != StatusZakaza.VIPOLNEN) {
            for (Strava strava : stravi) {
                if (listStrav.containsKey(strava)) {
                    listStrav.put(strava, listStrav.get(strava) + 1);
                } else {
                    listStrav.put(strava, 1);
                }
            }
        } else {
            throw new UnsupportedOperationException("Zakaz uge Vipolnen!");
        }
        return this;
    }

    public int getOcenkaPosetitelia() {
        return ocenkaPosetitelia;
    }

    public Zakaz setOcenkaPosetitelia(int ocenkaPosetitelia) {
        this.ocenkaPosetitelia = ocenkaPosetitelia;
        return this;
    }

    public LocalDate getDate() {
        return data;
    }

    public String getNomerZakaza() {
        return nomerZakaza;
    }

    public Klient getKlient() {
        return klient;
    }

    public Oficiant getOficiant() {
        return oficiant;
    }

    public Map<Strava, Integer> getListStrav() {
        return listStrav;
    }

    public StatusZakaza getStatus() {
        return status;
    }

    public void setStatus(StatusZakaza status) {
        this.status = status;
    }

    public Zakaz setDate(LocalDate date){
        this.data = date;
        return this;
    }

    @Override
    public int compareTo(Zakaz zakaz) {
        return this.nomerZakaza.compareTo(((Zakaz) zakaz).getNomerZakaza());
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Ingredient)) {
            return false;
        }
        Zakaz zakaz = (Zakaz) o;
        return zakaz.getNomerZakaza().equals(this.nomerZakaza);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + nomerZakaza.hashCode() + data.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Zakaz{" +
                " data = " + data +
                ", nomerZakaza = '" + nomerZakaza +
                ", listStrav = " + listStrav + "\n";
    }
}
