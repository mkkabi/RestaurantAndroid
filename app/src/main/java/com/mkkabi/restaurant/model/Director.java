package com.mkkabi.restaurant.model;

import java.time.LocalDate;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;


public class Director extends Supervisor {

    public Director(String login, String pass) {
        super(login, pass);
    }

    public List<Zakaz> pokazatVseZakazi(Worker worker, Restaurant restoran) {
        return restoran.showAllOrders(this);
    }

}
