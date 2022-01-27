package main.java.parser;

import main.java.entity.Chars;
import main.java.entity.Papers;
import org.apache.log4j.Logger;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;
import main.java.entity.Paper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

public class SaxParser extends DefaultHandler{

    public static final Logger LOG = Logger.getLogger(SaxParser.class.toString());

    Papers papers = new Papers();
    Paper paper;
    String thisElement = "";

    public SaxParser(){
        paper = new Paper();
    }

    public Papers getResult(){
        return papers;
    }

    @Override
    public void startDocument() throws SAXException {
        LOG.info("Start parse XML...");
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        thisElement = qName;
        if (thisElement == "paper"){
            paper = new Paper();
            papers.add(paper);
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        if (thisElement == "paper"){
            papers.add(paper);
        }
        thisElement = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        Field[] publicFields = paper.getClass().getDeclaredFields();
        Method[] methods = paper.getClass().getMethods();
        for (Field field : publicFields) {
            if (field.getName() == thisElement) {
                for (Method method : methods) {
                    String s = method.getName().toLowerCase();
                    String b = "set" + field.getName().toLowerCase();
                    if (Objects.equals(b.toLowerCase(), s.toLowerCase())) {
                        try {
                            method.invoke(paper, new String(ch, start, length));
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void endDocument() {
        LOG.info("Stop parse XML...");
    }
}