package se.kentor.designdemo.page;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import se.kentor.designdemo.R;
import se.kentor.designdemo.model.Country;
import se.kentor.designdemo.network.DataProvider;
import se.kentor.designdemo.network.ImageLoader;
import se.kentor.designdemo.network.RequestProcessor;

/**
 * Created by Ilya Klyukin.
 */
public class CountryFragment extends Fragment implements RequestProcessor {

    private static final String TAG = "designdemo.kentor.se";

    private ProgressDialog progressDialog;
    private Country country;
    private ImageLoader imageLoader = new ImageLoader(getActivity());

    public CountryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_country, container, false);
        return rootView;
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        boolean isCountrySaved = savedInstanceState != null && savedInstanceState.containsKey("country");

        Country country = null;
        if (isCountrySaved) {
            try {
                country = (Country) savedInstanceState.getSerializable("country");
            } catch (Exception e) {
                Log.e(TAG, "Error on deserializaing country", e);
            }
        }

        //load data
        if (country == null && !isCountrySaved) {
            new DataProvider(this).execute();
        } else {
            this.country = country;
            displayCountry();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("country", country);
    }

    @Override
    public void onPreExecute() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle(getString(R.string.get_country_list));
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setIndeterminate(false);
        progressDialog.show();
    }

    @Override
    public void onPostExecute(List<Country> countries) {
        if (getActivity() == null)
            return;
        progressDialog.dismiss();

        if (countries != null && countries.size() != 0)
            this.country = selectCountry(countries);

        displayCountry();
    }

    //select country by random
    private Country selectCountry(List<Country> countries) {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(countries.size());
        return countries.get(index);
    }

    //set data to controls
    private void displayCountry() {
        Activity mainActivity = getActivity();
        if (mainActivity == null)
            return;

        String name = country == null ? getString(R.string.no_data) : country.getName();
        ((TextView) mainActivity.findViewById(R.id.country_name)).setText(name);

        String description = country == null ?
                getString(R.string.no_connection) :
                getString(R.string.population) + " : " + country.getPopulation();
        ((TextView) mainActivity.findViewById(R.id.country_description)).setText(description);

        ImageView flagView = (ImageView) mainActivity.findViewById(R.id.country_flag);
        if (country == null) {
            int id = getResources().getIdentifier("se.kentor.designdemo:drawable/temp_img", null, null);
            flagView.setImageResource(id);
        } else {
            imageLoader.DisplayImage(country.getFlag(), flagView);
        }
    }
}
