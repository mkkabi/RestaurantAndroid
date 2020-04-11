package com.mkkabi.restaurant.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Predicate;

public abstract class Supervisor extends Worker {

    public Supervisor(String login, String pass) {
        super(login, pass);
    }

    public List<Zakaz> pokazatZakaziPoStatusu(IRestoran restoran, StatusZakaza statusZakaza){
        return restoran.naitiZakaziPoStatusu(this, statusZakaza);
    }

//    // return a Map of Orders with the corresponding croes
//    public Map<Zakaz, Integer> proveritKachestvoObslugivaniyaPoDate(IRestoran restoran, LocalDate from, LocalDate to){
//        Map<Zakaz, Integer> result = new TreeMap<>();
//        List<Zakaz> zakazi = naitiZakaziZaPeriod(restoran, from, to);
//
//        zakazi.forEach(zakaz -> {
//            System.out.print("  Заказ номер - " + zakaz.getNomerZakaza() + "   Оценка - " + zakaz.getOcenkaPosetitelia());
//            result.put(zakaz, zakaz.getOcenkaPosetitelia());
//        });
//        return result;
//    }
//
//    public int skolkoZarabotanoZaPeriod(IRestoran restoran, LocalDate from, LocalDate to){
//        int[] result = new int[1];
//        List<Zakaz> zakazi = naitiZakaziZaPeriod(restoran, from, to);
//        zakazi.stream().map(Zakaz::poschitatStoimostZakaza).forEach(i -> result[0] += i);
//        return result[0];
//    }


}
