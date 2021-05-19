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
    private Stack pila = new Stack();
   // private List <String> IMG = new ArrayList();
    //private List <String> LINKS = new ArrayList();
    private boolean estaBalanceado = false;
    private int estado = 0;
    
    

    public JFlexScraper(File fichero) throws FileNotFoundException,IOException {
        Reader reader = new BufferedReader(new FileReader(fichero));
        analizador = new HTMLParser(reader);
        
         if (this.pila.empty()) {
            this.estaBalanceado = true;
        }
        
    
    
    
    //public void automata() throws IOException {
        
        Token token;
        boolean etiquetaA=false;
        boolean etiquetaIMG=false;
        boolean valorHREF=false;
        boolean valorIMG=false;
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
                        if (token.getTipo().equals(SLASH)) {
                        estado = 5;
                    }
                        if (token.getTipo().equals(CLOSE)) {
                        estado = 0;
                    }
                        
                        
                        break;
                case 3:
                    
                    if (token.getTipo().equals(IGUAL)) {
                        estado = 4;         
                    }                  
                    break;
                    
                case 4:
                    if(token.getTipo() == Tipo.VALOR){
                      if (valorHREF){
                           estado=2;
                           enlacesA.add(token.getValor());
                         
                      }
                      if (valorIMG){
                          estado=2;
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
                        if(token.getValor().equalsIgnoreCase(etiquetasAbiertas.peek())){
                            etiquetasAbiertas.pop();
                            estaBalanceado = false;
                        estado=7;                            
             
                        }
                        }// FALTA CHEQUEAR LO DE LA PILA
                   
                    break;
                case 7:
                    if (token.getTipo().equals(CLOSE)) {
                        estado = 0;
                        }
                    break;

            }
            }}
    

    //DEVOLUCION DE LOS DISTINTOS VALORES
    public List<String> getImagenes() {
        return this.enlacesIMG;
    }

    public List<String> getLinks() {
        return this.enlacesA;
    }

    public boolean getBalance() {
        return this.estaBalanceado;
    }

    public Stack getStack() {
        return this.pila;
    }

   
   


   
/*
    // Esta clase debe contener tu automata programado...
    public ArrayList<String> obtenerHiperenlaces() {
        // Habrá que programarlo..
        return new ArrayList<String>();
    }

    public ArrayList<String> obtenerHiperenlacesImagenes() {
        // Habrá que programarlo..
        return new ArrayList<String>();
    }
*/
    public boolean esDocumentoHTMLBienBalanceado() {
        // Habrá que programarlo..
        return !(estaBalanceado && etiquetasAbiertas.empty());
    
}}
