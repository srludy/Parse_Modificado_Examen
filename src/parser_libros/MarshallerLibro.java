package parser_libros;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class MarshallerLibro {

	ArrayList <Libro> libreria;
	Document dom;
	
	public MarshallerLibro (ArrayList <Libro> l) {
		libreria = l;
	}
	
	public void newDocument () throws ParserConfigurationException, IOException {
		
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
			DocumentBuilder db = dbf.newDocumentBuilder();
			dom = db.newDocument();
	}
	
	public void buildDomStructure() {
		
		Element mainEle = (Element) dom.createElement("biblioteca");
		dom.appendChild((Node) mainEle);
		
		Iterator it = libreria.iterator();
		
		while(it.hasNext()) {
			Libro l = (Libro) it.next();
			
			Element eleLibro = getEleLibro(l);
			mainEle.appendChild(eleLibro);
		}
	}

	private Element getEleLibro(Libro l) {
		Element eleLibro = dom.createElement("libro");
		
		//lineas que recogen el valor del TAG titulo y lo introducen.
		Element eleTitulo = setValueInTag(l.getTitulo(), "titulo");
		//Añadimos el Atributo al TAG 'titulo'
		eleTitulo.setAttribute("anyo", l.getAñoPub());
		eleLibro.appendChild(eleTitulo);
		
		//Lineas que recogen el valor de los autores.
		ArrayList <String> autores = l.getAutor();
		Iterator it = autores.iterator();
		Element eleAutores = dom.createElement("autor");
		while(it.hasNext()) {
			Element eleNomAutor = setValueInTag(it.next().toString(), "nombre");
			eleAutores.appendChild(eleNomAutor);
		}
		eleLibro.appendChild(eleAutores);
		
		//Lineas que recogen el valor de editor.
		Element eleEditor = setValueInTag(l.getEditorial(), "editor");
		eleLibro.appendChild(eleEditor);
		
		//Lienas que recogen el valor de nºPaginas.
		Element eleNPaginas = setValueInTag(l.getNumPaginas(), "paginas");
		eleLibro.appendChild(eleNPaginas);
		
		
		
		return eleLibro;
	}
	
	private Element setValueInTag(String valueTag, String tag) {
		Element element = null;
		element = dom.createElement(tag);
		Text valorTag = (Text) dom.createTextNode(valueTag);
		element.appendChild(valorTag);
		return element;
	}
	/*private Element setAttributeInTag(Element ele) {
		Element element = null;
		eleTitulo.setAttribute("anyo", l.getAñoPub());

		return element;
	}*/
	
	public void buildDomToXml (File file) throws TransformerException {
		
		Transformer trn = TransformerFactory.newInstance().newTransformer();
		trn.setOutputProperty(OutputKeys.INDENT, "yes");
		
		StreamResult sr = new StreamResult(file);
		DOMSource ds = new DOMSource(dom);
		trn.transform(ds, sr);
		
	}
}
