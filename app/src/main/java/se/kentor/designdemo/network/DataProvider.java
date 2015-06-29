package se.kentor.designdemo.network;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import se.kentor.designdemo.model.Country;

/**
 * Created by Ilya Klyukin.
 */
public class DataProvider extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "designdemo.kentor.se";
    private static final String SERVER_URL = "http://www.androidbegin.com/tutorial/jsonparsetutorial.txt";

    RequestProcessor requestProcessor;

    JSONObject jsonobject;
    JSONArray jsonarray;
    List<Country> countries;

    public DataProvider(RequestProcessor requestProcessor) {
        this.requestProcessor = requestProcessor;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        requestProcessor.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void args) {
        requestProcessor.onPostExecute(countries);
    }

    @Override
    protected Void doInBackground(Void... params) {

        countries = new ArrayList<>();
        // Retrieve JSON Objects from the given URL address
        jsonobject = JSONfunctions.getJSONfromURL(SERVER_URL);

        if (jsonobject == null)
            return null;

        try {
            // Locate the array name in JSON
            jsonarray = jsonobject.getJSONArray("worldpopulation");

            for (int i = 0; i < jsonarray.length(); i++) {
                jsonobject = jsonarray.getJSONObject(i);

                Country country = new Country();
                country.setName(jsonobject.getString("country"));
                country.setPopulation(jsonobject.getString("population"));
                country.setRank(jsonobject.getInt("rank"));
                country.setFlag(jsonobject.getString("flag"));

                countries.add(country);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Error on parsing data", e);
        }
        return null;
    }
}
