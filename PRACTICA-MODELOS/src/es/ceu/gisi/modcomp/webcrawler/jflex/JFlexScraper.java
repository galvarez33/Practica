package es.ceu.gisi.modcomp.webcrawler.jflex;

import es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Tipo;
import static es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Tipo.CLOSE;
import static es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Tipo.IGUAL;
import static es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Tipo.OPEN;
import static es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Tipo.PALABRA;
import static es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Tipo.SLASH;
import static es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Tipo.VALOR;
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
    
    ArrayList<String> enlacesA = new ArrayList();
    ArrayList<String> enlacesIMG = new ArrayList();
    Stack<String> etiquetasAbiertas = new Stack();
    HTMLParser analizador; 
    private Stack PILA = new Stack();
    private List <String> IMAGES = new ArrayList();
    private List <String> LINKS = new ArrayList();
    private boolean isBalanced = false;
    private int estado = 0;
    

    public JFlexScraper(File fichero) throws FileNotFoundException {
        Reader reader = new BufferedReader(new FileReader(fichero));
        analizador = new HTMLParser(reader);
    
    
    
    public void automata() throws IOException {
        int id=0;
        Token token;
        boolean etiquetaA=false;
        boolean etiquetaIMG=false;
        boolean valorHREF=false;
        boolean valorIMG=false;
        while ((token = analizador.nextToken()) != null) {
            //System.out.print(token.getValor() + " ");
            switch (estado) {
                
                case 0:
                    if (token.getTipo().equals(OPEN)) {
                        estado = 1;
                       break;
                    }
                case 1: 
                   if (token.getTipo().equals(PALABRA)) {                                          
                        estado = 2; 
                        etiquetasAbiertas.push(token.getValor().toLowerCase());
                                
                        if (token.getValor().toLowerCase().equals("a") ) {
                            etiquetaA =true;
                    }
                    
                        else if (token.getValor().toLowerCase().equals("img")) {
                            etiquetaIMG=true;
                    
                    } }
                   else if (token.getTipo().equals(SLASH)) {
                        estado = 6;
                    }
                    break;
        
                    
                    case 2:
                        if(token.getTipo().equals(PALABRA)){
                            estado =3;
                            if(etiquetaA){
                                if(token.getValor().equalsIgnoreCase("href")){
                              valorHREF= true;
                            }       
                            else if(etiquetaIMG){
                                if(token.getValor().equalsIgnoreCase("src")){
                                    valorIMG =true;
                                }
                                    }}}
                        break;
                case 3:
                    
                    if (token.getTipo().equals(IGUAL)) {
                        estado = 4;         
                    }                  
                    break;
                    
                case 4:
                    if(token.getTipo() == Tipo.VALOR){
                      if (valorHREF){
                          enlacesA.add(token.getValor());
                      }
                      if (valorIMG){
                          enlacesIMG.add(token.getValor());
                      }
                     }
                    
                    break;
                 case 5:
                    if (token.getTipo().equals(CLOSE)) {
                        estado = 0;
                       
                    }
                    break;
                    
                case 6:
                     if (token.getTipo().equals(PALABRA)) {
                        estado = 2;
                        }
                    if (id == 1) {
                        estado = 2;
                        LINKS.add(token.getValor());
                    }
                    if (id == 2) {
                        estado = 2;
                        IMAGES.add(token.getValor());
                    }
                    break;
                case 7:
                    if (token.getTipo().equals(PALABRA)) {
                        estado = 2;
                        }
                    break;

            }
            }
    }

   public List<String> getLinks(){
        return this.LINKS;
    }
   public List<String> getImagenes(){
        return this.IMAGES;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    

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
