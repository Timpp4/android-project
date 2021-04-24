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
import java.lang.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Tämä luokka hakee tietoa WHOn Athena APIsta, jossa on keskimääräiset BMIt vuosittain maan ja
 * sukupuolen mukaan.
 */
/*https://apps.who.int/gho/athena/api/GHO/NCD_BMI_MEAN.json?filter=COUNTRY:FIN&profile=simple*/
public class BmiBackend {
    ArrayList<BmiObject> bmiData = new ArrayList<BmiObject>();

    public void whoRequest () {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            URL url = new URL("https://apps.who.int/gho/athena/api/GHO/NCD_BMI_MEAN.xml?filter=COUNTRY:FIN&profile=simple");
            URLConnection urlc = url.openConnection();
            urlc.setRequestProperty("User-Agent", "Mozilla 5.0 (Windows; U; "
                    + "Windows NT 5.1; en-US; rv:1.8.0.11) ");
            InputStream inputFile = urlc.getInputStream();
            Document doc = builder.parse(inputFile);
            doc.getDocumentElement().normalize();
            //System.out.println("ROOT ELEMENT: " + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getDocumentElement().getElementsByTagName("Fact");

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

            }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }
    public String getBmiFromWho(String sex, String year){
        //double avgBmi = 0;
        String avgBmiByYear = null;
        for (int i = 0; i < bmiData.size(); i++){
            if (bmiData.get(i).getYear().equals(year) & bmiData.get(i).getSex().equals(sex))
                //avgBmi = Double.parseDouble(bmiData.get(i).getBmi());
                avgBmiByYear = (bmiData.get(i).getBmi());
        }
        return avgBmiByYear;
    }
}