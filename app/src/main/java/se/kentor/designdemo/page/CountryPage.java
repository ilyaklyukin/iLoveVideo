package se.kentor.designdemo.page;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ilya Klyukin.
 */
public enum CountryPage {
    DESCRIPTION(0, CountryFragment.class),
    FINAL(1, FinalFragment.class);

    private int position;
    private Class clazz;

    private static final Map<Integer, CountryPage> intToTypeMap = new HashMap<>();

    static {
        for (CountryPage type : CountryPage.values()) {
            intToTypeMap.put(type.position, type);
        }
    }

    CountryPage(int position, Class clazz) {
        this.position = position;
        this.clazz = clazz;
    }

    public static CountryPage getByPosition(int position) {
        return intToTypeMap.get(position);
    }

    public int getPosition() {
        return position;
    }

    public Class getClazz() {
        return clazz;
    }
}
