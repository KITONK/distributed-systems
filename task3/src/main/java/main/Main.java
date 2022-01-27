package main.java.main;

import main.java.parser.DomParser;
import main.java.parser.SaxParser;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.*;

import org.w3c.dom.Document;

public class Main {

    public static final Logger LOG = Logger.getLogger(Main.class.toString());

    public static void main (String args[]) throws Exception {
        File file = new File("Paper.xml");
        //SAX
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = null;
        try {
            parser = factory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
        SaxParser saxp = new SaxParser();
        try {
            parser.parse(file, saxp);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        LOG.info("Sax parser result : " + saxp.getResult());
        //DOM
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            DomParser domParser = new DomParser();
            LOG.info("DOM parser result:\n" + domParser.parse(doc));
        } catch (Exception e) {
            LOG.error("Dom exception", e);
        }
    }


}