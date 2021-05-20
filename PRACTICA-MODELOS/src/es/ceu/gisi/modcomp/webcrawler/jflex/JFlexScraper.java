package es.ceu.gisi.modcomp.webcrawler.jflex;

import es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Tipo;
import static es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Tipo.CLOSE;
import static es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Tipo.OPEN;
import static es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Tipo.PALABRA;
import static es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Tipo.SLASH;
import es.ceu.gisi.modcomp.webcrawler.jflex.lexico.Token;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
    private boolean estaBalanceado = true;
    private int estado = 0;

    public JFlexScraper(File fichero) throws FileNotFoundException, IOException {
        Reader reader = new BufferedReader(new FileReader(fichero));
        analizador = new HTMLParser(reader);

        //public void automata() throws IOException {
        Token token;
        boolean etiquetaA = false;
        boolean etiquetaIMG = false;
        boolean valorHREF = false;
        boolean valorIMG = false;
        while ((token = analizador.nextToken()) != null) {
            // System.out.print(token.getValor() + " ");
            switch (estado) {

                case 0:
                    if (token.getTipo().equals(OPEN)) {
                        estado = 1;
                    }

                    break;
                case 1:
                    if (token.getTipo().equals(PALABRA)) {
                        estado = 2;
                        etiquetasAbiertas.push(token.getValor().toLowerCase());

                        if (token.getValor().toLowerCase().equals("a")) {
                            etiquetaA = true;
                            etiquetaIMG = false;
                        } else if (token.getValor().toLowerCase().equals("img")) {
                            etiquetaIMG = true;
                            etiquetaA = false;

                        } else {
                            etiquetaIMG = false;
                            etiquetaA = false;

                        }
                    } else if (token.getTipo().equals(SLASH)) {
                        estado = 6;
                    }
                    // System.out.println("etqueta a " + etiquetaA + " " + token.getValor());
                    break;

                case 2:
                    if (token.getTipo().equals(PALABRA)) {
                        estado = 3;
                        // System.out.println("etiqueta " + etiquetaA + " " + token.getValor());
                        if (etiquetaA) {
                            if (token.getValor().equalsIgnoreCase("href")) {

                                valorHREF = true;
                                valorIMG = false;
                            }
                        } else if (etiquetaIMG) {
                            if (token.getValor().equalsIgnoreCase("src")) {

                                valorIMG = true;
                                valorHREF = false;
                            }
                        } else {
                            valorHREF = false;
                            valorIMG = false;
                        }
                    }

                    if (token.getTipo().equals(SLASH)) {
                        estado = 5;
                        etiquetasAbiertas.pop();
                    }
                    if (token.getTipo().equals(CLOSE)) {
                        estado = 0;
                    }

                    break;
                case 3:

                    if (token.getTipo() == Tipo.IGUAL) {
                        estado = 4;
                    }
                    break;

                case 4:

                    if (token.getTipo() == Tipo.VALOR) {
                        estado = 2;
                        System.out.println("href " + valorHREF);
                        if (valorHREF) {

                            enlacesA.add(token.getValor());

                        }
                        System.out.println("img " + valorIMG);
                        if (valorIMG) {

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
                        System.out.println("PILA " + etiquetasAbiertas + " Palabra " + token.getValor() + "tocho :" + !token.getValor().equalsIgnoreCase(etiquetasAbiertas.peek()) + " Etiquets: " + etiquetasAbiertas.peek());
                        if (!token.getValor().equalsIgnoreCase(etiquetasAbiertas.pop())) {

                            estaBalanceado = false;

                        }
                        estado = 7;
                        System.out.println("PILA " + etiquetasAbiertas + " Palabra " + token.getValor() + " balanceado " + estaBalanceado);
                    }// FALTA CHEQUEAR LO DE LA PILA

                    break;
                case 7:
                    if (token.getTipo().equals(CLOSE)) {
                        estado = 0;
                    }
                    break;

            }
            // System.out.println("ESTADO " + estado);

        }
    }

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

    }
}
