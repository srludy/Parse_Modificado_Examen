package parser_libros;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public class Parse_Libro {

		static Parser parser = new Parser();;

		public static void main(String[] args) {
	
		int opcion = 0;
		
			do {
				Scanner sc = new Scanner(System.in);

				System.out.println("Elije una opcion");
				System.out.println("1: Parsear un xml.");
				System.out.println("2: Serializar un Objetuo a un xml.");
				System.out.println("3: Salir.");
				System.out.println("4: AñadirLibro");
				System.out.println("5: Mostrar Libros del Sistema.");
				opcion = sc.nextInt();
				
				switch(opcion) {
				case 1: parseXml(); opcion = 0; break;
				case 2: serializaObjecto(); opcion = 0; break;
				default: System.out.println("Elije una opcion valida.");
				case 3: System.out.println("Cerrado.");break;
				case 4: addBook();break;
				case 5: parser.print();break;
				
				}
			}while(opcion == 0 || opcion != 3 );
			
		}
		
		private static void addBook() {
			Scanner sc = new Scanner(System.in);
			ArrayList <String> autores = new ArrayList ();
			String editorial = null;
			boolean autoresAñadidos = true;
			
			System.out.println("Nuevo Libro");
			System.out.println("Introduce el titulo del libro");
			String titulo = sc.nextLine();
			System.out.println("Introduce el año de publicacion del libro");
			String anyoLibro = sc.nextLine();
			System.out.println("Introduce el numero de paginas del libro.");
			String numPag = sc.nextLine();
			System.out.println("Introduce el nombre de la editorial.");
			editorial = sc.nextLine();
			System.out.println("¿Cuantos autores tiene?");
			try{
				int numAutores = sc.nextInt();
				sc.nextLine();
				for (int i = 0 ; i < numAutores; i++) {
					int count = i+1;
					System.out.println("Introduce el nombre del autor num: "+count);
					String autor = sc.nextLine();
					autores.add(autor);
										
				}
			
			}catch(Exception e){
				System.err.println("Dato erroneo, introduce un numero mayor que 0 de autores.");
				autoresAñadidos = false;
			}
			int opcion = 0;

			if(autoresAñadidos == true) {
				do{
					
					System.out.println("Deseas guardar este libro?.\n1-Si\n2-No");
					opcion = sc.nextInt();
					if(opcion == 1) {
						int sumaid =parser.getId()+1;
						String id = String.valueOf(sumaid);
						Libro libro = new Libro(titulo,id,autores,numPag,anyoLibro,editorial);
						parser.setId(sumaid);
						parser.getLibros().add(libro);
						System.out.println("Libro Guardado");
						libro.print();
					}else {
						System.out.println("El libro no ha sido guardado.");
					}
				}while (opcion != 1 && opcion != 2);
			}
			
		}

		
		private static void serializaObjecto() {
			Scanner sc = new Scanner(System.in);

			System.out.println("Introduce el nombre del fichero al que quieres volcar tu xml.");
			String nomFichero = sc.nextLine();
			File file = new File(nomFichero+".xml");
			if(!parser.getLibros().isEmpty()) {
				if(!file.exists()) {
					try {
						ArrayList<Libro> libreria = parser.getLibros();	
						MarshallerLibro mLibro = new MarshallerLibro(libreria);
						mLibro.newDocument();
						mLibro.buildDomStructure();
						mLibro.buildDomToXml(file);
						System.out.println("Generado XML correctamente.");
					} catch (ParserConfigurationException e){
						System.err.println("Error parseando el documento.");
					} catch (IOException e) {
						System.err.println("Error de ejecucion del programa.");
					} catch (TransformerException e) {
						System.err.println("Error Transformando el documento en XML ");
					}
				}else {
					System.err.println("El fichero ya existe.");
				}

			}else {
				System.err.println("No hay libros en el programa.");
			}
			
		}


		public static void parseXml() {
			Scanner sc = new Scanner(System.in);
			System.out.println("Introduce el nombre del xml que quieres introducir al sistema.");
			String nomFichero = sc.nextLine();
			File file = new File(nomFichero+".xml");
			if(file.exists()) {
				try {			
					parser.parseFicheroXml(nomFichero+".xml");
					parser.parseDocument();
					parser.print();
					
				} catch (IOException e) {
					System.err.println("Error de ejecucion.");
				} catch (ParserConfigurationException e) {
					System.err.println("Error de configuracion del parse.");
				} catch (SAXException e) {
					System.err.println("Error parseando el fichero XML");
				}
			}else {
				System.err.println("El fichero existe.");
			}
		}
		
	}