package se.kentor.designdemo.network;

import java.util.List;

import se.kentor.designdemo.model.Country;

/**
 * Created by Ilya Klyukin.
 */
public interface RequestProcessor {
    void onPreExecute();

    void onPostExecute(List<Country> countries);
}
