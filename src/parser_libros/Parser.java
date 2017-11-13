package parser_libros;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Parser {

	private Document dom = null;
	private ArrayList<Libro> libros;

	private int id = 0;

	public Parser() {
		libros = new ArrayList<Libro>();
	}

	public void parseFicheroXml(String fichero) throws IOException, ParserConfigurationException, SAXException  {
		// creamos una factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
 
			// creamos un documentbuilder
		DocumentBuilder db = dbf.newDocumentBuilder();

			// parseamos el XML y obtenemos una representación DOM
		dom = db.parse(fichero);

	}

	public void parseDocument() {
		// obtenemos el elemento raíz
		Element docEle = dom.getDocumentElement();

		// obtenemos el nodelist de elementos
		NodeList nl = docEle.getElementsByTagName("libro");
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {

				// obtenemos un elemento de la lista (persona)
				Element el = (Element) nl.item(i);

				// obtenemos una persona
				Libro p = getLibro(el);

				// lo añadimos al array
				libros.add(p);
			} 
		}
	}
	
	private Libro getLibro(Element libroEle){
		id++;
		String idString = String.valueOf(id);
		//para cada elemento persona, obtenemos su nombre y su edad
		String titulo = getTextValue(libroEle,"titulo");
		String fechaPubli = getAtribute(libroEle, "titulo", "anyo");
		ArrayList <String> autor = getArrayValue (libroEle, "autor", "nombre");
		String editor =  getTextValue(libroEle, "editor");
		String numPag = getTextValue(libroEle, "paginas");
		
		//Creamos una nueva persona con los elementos leídos del nodo
		Libro e = new Libro(titulo,idString,autor,numPag,fechaPubli,editor);
		return e;		
		
	}
	private ArrayList <String> getArrayValue (Element ele, String tagName, String subTagName){
		ArrayList <String> autor = new ArrayList();
		String textValue;
		NodeList nl = ele.getElementsByTagName(tagName);
	
		if (nl != null && nl.getLength() > 0) {

			for (int i =0 ; i < nl.getLength() ; i++) {
				
				Element el = (Element)nl.item(i);
				
				NodeList nl2 = el.getElementsByTagName(subTagName);
				
				if(nl2 != null && nl2.getLength() > 1){
					for(int e = 0 ; e < nl2.getLength(); e++) {
						
						Element eleNom = (Element)nl2.item(e);
						
						textValue = eleNom.getFirstChild().getNodeValue();
						autor.add(textValue);
					}
				}
			}	
		}
		
		return autor;
	}
	
	private String getAtribute(Element ele, String tagName, String tagAtri) {
		String atributo = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			atributo = el.getAttribute(tagAtri);
		
		}
		return atributo;
	}
	
	private String getTextValue(Element ele, String tagName) {
		
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {
				Element el = (Element)nl.item(i);
				textVal = el.getFirstChild().getNodeValue();;
				}
		}
		return textVal;
	}
	
	private int getIntValue(Element ele, String tagName) {				
		return Integer.parseInt(getTextValue(ele,tagName));
	}
	
	public void print(){

		Iterator it = libros.iterator();
		while(it.hasNext()) {
			Libro p=(Libro) it.next();
			p.print();
		}
	}

	public ArrayList<Libro> getLibros() {
		return libros;
	}

	public void setLibros(ArrayList<Libro> libros) {
		this.libros = libros;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	

}