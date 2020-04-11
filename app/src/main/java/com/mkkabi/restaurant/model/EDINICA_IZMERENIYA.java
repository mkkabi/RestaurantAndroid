package com.mkkabi.restaurant.model;

import static tools.Constants.*;

public enum EDINICA_IZMERENIYA {
    GRAMM(__GRAMM),
    MILILITR(__MILILITR),
    SHTUKA(__SHTUKA);

    public String getName() {

        return this.name;
    }

    private String name;

    EDINICA_IZMERENIYA(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return super.toString();
    }
}