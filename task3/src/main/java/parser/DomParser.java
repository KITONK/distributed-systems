package main.java.parser;

import main.java.entity.Paper;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class DomParser {
    Paper paper;

    public DomParser(){
        paper = new Paper();
    }



    public Paper parse(Document input) {
        return parseNode(input.getDocumentElement());
    }

    private Paper parseNode(Node node) {

        if (node.getNodeName().equals("#text"))
            return null;

        StringBuffer result = new StringBuffer();

        if (getElementContent(node) != null && !(getElementContent(node).equals(""))){
            switch (node.getNodeName()){
                case "title": paper.setTitle(getElementContent(node));
            }
            switch (node.getNodeName()){
            case "title": paper.setTitle(getElementContent(node));
                break;
            case "type": paper.setType(getElementContent(node));
                break;
            case "monthly": paper.setMonthly(getElementContent(node));
                break;
            case "color": paper.setColor(getElementContent(node));
                break;
            case "numberOfPage": paper.setNumberOfPage(getElementContent(node));
                break;
            case "glossy": paper.setGlossy(getElementContent(node));
                break;
            case "subscriptionIndex": paper.setSubscriptionIndex(getElementContent(node));
                break;
            }
        }

            result.append("Element content = '" + getElementContent(node)+"'\n");

        NodeList nodeList = node.getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++){
            result.append(parseNode(nodeList.item(i)));
        }

        return paper;
    }

    private String getElementContent(Node node) {

        Node contentNode = node.getFirstChild();
        if (contentNode != null)
            if (contentNode.getNodeName().equals("#text")) {
                String value = contentNode.getNodeValue();
                if (value != null)
                    return value.trim();
            }
        return null;
    }
}