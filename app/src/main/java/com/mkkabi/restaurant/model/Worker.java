package com.mkkabi.restaurant.model;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class Worker {

    protected String login, pass;

    public Worker(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }

    public List<Zakaz> otfiltrovatZakaziPoStatusu(IRestoran restoran, StatusZakaza statusZakaza) {
        return restoran.naitiZakaziPoStatusu(this, statusZakaza);
    }

//    public Map<Strava, Integer> naitiNaiboleePopularnuyuStravuZaPeriod(IRestoran restoran, LocalDate from, LocalDate to) {
//        Map<Strava, Integer> temp = new HashMap<>();
//        Map<Strava, Integer> result = new HashMap<>();
//        List<Zakaz> zakazi = naitiZakaziZaPeriod(restoran, from, to);
//        zakazi.stream().map(zakaz -> zakaz.getListStrav().entrySet()).forEach(entry -> {
//            entry.forEach(entryItem -> {
//                if (temp.containsKey(entryItem.getKey())) {
//                    temp.put(entryItem.getKey(), temp.get(entryItem.getKey()) + entryItem.getValue());
//                } else {
//                    temp.put(entryItem.getKey(), entryItem.getValue());
//                }
//            });
//        });
//        Optional<Map.Entry<Strava, Integer>> optionalZakaz = temp.entrySet().stream().max(Map.Entry.comparingByValue());
//        optionalZakaz.ifPresent(entry -> result.put(entry.getKey(), entry.getValue()));
//        return result;
//    }

//    public List<Zakaz> naitiZakaziZaPeriod(IRestoran restoran, LocalDate from, LocalDate to) {
//        return naitiZakaziPoUsloviyu(restoran, getPredicatePoDate(from, to));
//    }

//    private Predicate<Zakaz> getPredicatePoDate(LocalDate from, LocalDate to) {
//        return (z) -> z.getDate().isAfter(from) && z.getDate().isBefore(to);
//    }

//    private List<Zakaz> naitiZakaziPoUsloviyu(IRestoran restoran, Predicate<Zakaz> predicate) {
//        return restoran.getZakazi(this).stream().
//                filter(predicate).
//                collect(Collectors.toList());
//    }

    public Set<Strava> posmotretMenu(IRestoran restoran) {
        return restoran.getMenu();
    }
}
