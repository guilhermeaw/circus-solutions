package utils;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import entities.Operation;
import entities.Pane;
import entities.Role;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

public class PermissionXMLReader extends DefaultHandler {

    private Role role;
    private Operation operation;
    private Pane pane;
  
    private String roleTagValue;
    private String currentTag;
    private String accessTagValue;
    private boolean hasAccess;

    public PermissionXMLReader(Role role, Operation operation, Pane pane) {
        super();
        this.role = role;
        this.operation = operation;
        this.pane = pane;
    }

    public void parse() {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser;

        try {
            saxParser = factory.newSAXParser();
            saxParser.parse("src/permissions.xml", this);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            ApplicationUtilities.getInstance().handleException(e);
        }
    }

    // os métodos startDocument, endDocument, startElement, endElement e
    // characters, listados a seguir, representam os métodos de call-back da API
    // SAX

    /**
     * evento startDocument do SAX. Disparado antes do processamento da primeira
     * linha
     */
    public void startDocument() {
    }

    /**
     * evento endDocument do SAX. Disparado depois do processamento da última
     * linha
     */
    public void endDocument() {
    }

    /**
     * evento startElement do SAX. disparado quando o processador SAX identifica
     * a abertura de uma tag. Ele possibilita a captura do nome da tag e dos
     * nomes e valores de todos os atributos associados a esta tag, caso eles
     * existam.
     */
    public void startElement(String uri, String localName, String qName, Attributes atts) {
        // recupera o nome da tag atual
        currentTag = qName;

        if (qName.compareTo("role") == 0) {
          roleTagValue = atts.getValue(0);
        }

        if (qName.compareTo("permission") == 0) {
            accessTagValue = atts.getValue(0);
        }
    }

    /**
     * evento endElement do SAX. Disparado quando o processador SAX identifica o
     * fechamento de uma tag
     */
    public void endElement(String uri, String localName, String qName) throws SAXException {
        currentTag = "";
    }

    /**
     * evento characters do SAX. É onde podemos recuperar as informações texto
     * contidas no documento XML (textos contidos entre tags).
     */
    public void characters(char[] ch, int start, int length) throws SAXException {
        String xmlRule = new String(ch, start, length);

        if (currentTag.compareTo("permission") == 0) {
            if (role.getName().equals(roleTagValue) && xmlRule.split("\\.")[0].equals(pane.getName()) && xmlRule.split("\\.")[1].equals(operation.getName())) {
                if (accessTagValue.equals("true")) {
                    this.hasAccess = true;
                } else if (accessTagValue.equals("false")) {
                    this.hasAccess = false;
                }
            }
        }
    }

    /**
     * Verifica se usuario tem permissão na tela
     */
    public boolean canAccess() {
        return hasAccess;
    }
}
