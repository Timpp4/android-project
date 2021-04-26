package com.example.projectx.interfaces;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 *
 */
public class CountriesAPI {
    /**
     * This function makes request from Countries API and gets alpha 3 code from source
     * @param country pass country name from spinner to function
     * @return alpha 3 code of a contry as string
     */
    public String countriesRequest (String country) {
        String returnString = "";
        try {
            String requestUrl = "https://restcountries.eu/rest/v2/name/"+country+"?fields=alpha3Code";
            URL url = new URL(requestUrl);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputFromApi;
            StringBuffer stringBuffer = new StringBuffer();
            while ((inputFromApi = br.readLine()) != null)
            {
                stringBuffer.append(inputFromApi);
            }
            br.close();
            returnString = stringBuffer.toString().replaceAll("\\[", "")
                    .replaceAll("\\]", "").replaceAll("\\{", "")
                    .replaceAll("\\}", "").replaceAll("\"", "")
                    .replaceAll("alpha3Code", "");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    /**
     * This function makes ArrayList from countries which is used in HomeFragment spinner
     * @return ArrayList which includes countries
     */
    public ArrayList<String> countryList(){
        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length() > 0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries);
        return countries;
    }

}
