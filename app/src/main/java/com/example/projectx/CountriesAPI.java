package com.example.projectx;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class CountriesAPI {
    public void countriesRequest (String country) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String requestUrl = "https://restcountries.eu/rest/v2/name/"+country+"?fields=alpha3Code";
            System.out.println(requestUrl);
            URL url = new URL(requestUrl);
            URLConnection urlc = url.openConnection();
            urlc.setRequestProperty("User-Agent", "Mozilla 5.0 (Windows; U; "
                    + "Windows NT 5.1; en-US; rv:1.8.0.11) ");
            InputStream inputFile = urlc.getInputStream();
            Document doc = builder.parse(inputFile);
            System.out.println("*****DOC: " +doc);
            doc.getDocumentElement().normalize();
            System.out.println("*****DOC: " +doc);
            System.out.println("ROOT ELEMENT: " + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getDocumentElement().getElementsByTagName("Fact");
/*
            for (int i = 0; i < nList.getLength(); i++) {

                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    //ArrayList<String> theatreInfo = new ArrayList<String>();
                    String year = element.getElementsByTagName("YEAR").item(0).getTextContent();
                    String bmi = element.getElementsByTagName("Numeric").item(0).getTextContent();
                    String sex = element.getElementsByTagName("SEX").item(0).getTextContent();
                    bmiData.add(new BmiObject(year, bmi, sex));
                }

            }*/

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

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
