package com.bmiapplication;

import com.example.projectx.Bmi;

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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/*https://apps.who.int/gho/athena/api/GHO/NCD_BMI_MEAN.json?filter=COUNTRY:FIN&profile=simple*/
public class bmiBackend {
    ArrayList<Bmi> bmiData = new ArrayList<Bmi>();

    public void whoRequest() {
    }
    public void request () {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            URL url = new URL("https://apps.who.int/gho/athena/api/GHO/NCD_BMI_MEAN.xml?filter=COUNTRY:FIN&profile=simple");
            URLConnection urlc = url.openConnection();
            urlc.setRequestProperty("User-Agent", "Mozilla 5.0 (Windows; U; "
                    + "Windows NT 5.1; en-US; rv:1.8.0.11) ");
            InputStream inputFile = urlc.getInputStream();
            Document doc = builder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("ROOT ELEMENT: " + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getDocumentElement().getElementsByTagName("Fact");

            for (int i = 0; i < nList.getLength(); i++) {

                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    //ArrayList<String> theatreInfo = new ArrayList<String>();
                    String year = element.getElementsByTagName("YEAR").item(0).getTextContent();
                    String name = element.getElementsByTagName("Numeric").item(0).getTextContent();
                    String sex = element.getElementsByTagName("SEX").item(0).getTextContent();
                    System.out.println(year +" "+ name +" "+ sex);
                    bmiData.add(new Bmi(name, year, sex));
                }

            }

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }
}