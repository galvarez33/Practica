package es.ceu.gisi.modcomp.webcrawler.jflex;

import static es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Tipo.CLOSE;
import static es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Tipo.IGUAL;
import static es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Tipo.OPEN;
import static es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Tipo.PALABRA;
import static es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Tipo.SLASH;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Token;
import java.io.*;


/**
 * Esta clase encapsula toda la lógica de interacción con el parser HTML.
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 */

public class JFlexScraper {
    HTMLParser analizador; 
    private Stack PILA = new Stack();
    private List <String> IMAGES = new ArrayList();
    private List <String> LINKS = new ArrayList();
    private boolean isBalanced = false;
    private int estado = 0;
    

    public JFlexScraper(File fichero) throws FileNotFoundException {
        Reader reader = new BufferedReader(new FileReader(fichero));
        analizador = new HTMLParser(reader);
    }
    
    
    public void automata() throws IOException {
        int id=0;
        Token token;
        
        while ((token = analizador.nextToken()) != null) {
            System.out.print(token.getValor() + " ");
            switch (estado) {
                
                case 0:
                    if (token.getTipo().equals(OPEN)) {
                        estado = 1;
                       break;
                    }
                case 1: 
                   if (token.getTipo().equals(PALABRA)) {
                        PILA.push(token.getValor());
                       
                        estado = 2;
                    }
                   
                    if (token.getValor().toLowerCase().equals("a") && token.getTipo().equals(PALABRA)) {
                        estado = 3;
                        id = 1;
                    }
                    if (token.getValor().toLowerCase().equals("img") && token.getTipo().equals(PALABRA)) {
                        estado = 4;
                        id= 2;
                    }
                    break;
        
                    
                    case 2:
                    if (token.getTipo().equals(CLOSE)) {
                        
                        estado = 0;
                    } else if (token.getTipo().equals(SLASH)) {
                        estado = 2;
                        
                       
                    }
                    break;
                    
                case 3:
                    if (token.getTipo().equals(PALABRA)) {
                        estado = 3;
                       
                    }
                    if (token.getTipo().equals(IGUAL)) {
                        estado = 3;
                        
                    }
                    if (token.getValor().toLowerCase().equals("href") && token.getTipo().equals(PALABRA)) {
                        estado = 5;
                        id = 1;
                    }
                    if (token.getValor().equals(CLOSE)) {
                        estado = 0;
                     
                    }

                    break;
                case 4:
                    if (token.getTipo().equals(PALABRA)) {
                        estado = 4;
                        
                    }
                    if (token.getTipo().equals(IGUAL)) {
                        estado = 4;
                        
                    }
                    if (token.getValor().toLowerCase().equals("src") && token.getTipo().equals(PALABRA)) {
                        estado = 5;
                        id = 2;
                    }
                    if (token.getValor().equals(CLOSE)) {
                        estado = 0;
                       
                    }
                    break;
    
    
    
    
    
    
    
    
    
    
    
    
    

    // Esta clase debe contener tu automata programado...
    public ArrayList<String> obtenerHiperenlaces() {
        // Habrá que programarlo..
        return new ArrayList<String>();
    }

    public ArrayList<String> obtenerHiperenlacesImagenes() {
        // Habrá que programarlo..
        return new ArrayList<String>();
    }

    public boolean esDocumentoHTMLBienBalanceado() {
        // Habrá que programarlo..
        return false;
    }
}
