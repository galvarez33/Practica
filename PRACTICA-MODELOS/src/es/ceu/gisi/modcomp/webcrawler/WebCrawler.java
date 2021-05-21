package es.ceu.gisi.modcomp.webcrawler;

import es.ceu.gisi.modcomp.webcrawler.jflex.JFlexScraper;
import es.ceu.gisi.modcomp.webcrawler.jsoup.JsoupScraper;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Esta aplicación contiene el programa principal que ejecuta ambas partes del
 * proyecto de programación.
 *
 * @author Sergio Saugar García <sergio.saugargarcia@ceu.es>
 */
public class WebCrawler {

    private final static String PATH_PRUEBAS = new java.io.File("").getAbsolutePath()
            + "/test/es/ceu/gisi/modcomp/webcrawler/jflex/test/";
    private static File ficheroPrueba1 = new File(PATH_PRUEBAS + "prueba1.html");
    private static File ficheroPrueba2 = new File(PATH_PRUEBAS + "prueba2.html");

    public static void main(String[] args) throws IOException {
        // Deberá inicializar JFlexScraper con el fichero HTML a analizar
        // Y creará un fichero con todos los hiperenlaces que encuentre.
        // También deberá indicar, mediante un mensaje en pantalla que
        // el fichero HTML que se ha pasado está bien balanceado.
        JFlexScraper a = new JFlexScraper(ficheroPrueba2);

        System.out.println(
                "\nimg: : " + a.getImagenes());
        System.out.println(
                "links: " + a.getLinks());
        System.out.println(
                "la pila queda: " + a.getStack());
        System.out.println(
                "isBalanced?: " + a.getBalance());
        a.VolcarFicheroJFLEX();
        // Deberá inicializar JsoupScraper con la DIRECCIÓN HTTP de una página
        // web a analizar. Creará un fichero con todos los hiperenlaces que
        // encuentre en la página web. También obtendrá estadísticas de uso
        // de las etiquetas HTML más comunes: a, br, div, li, ul, p, span, table, td, tr
        JsoupScraper j = new JsoupScraper(new URL("https://elpais.com/"));

        System.out.println(j.estadisticasEtiqueta("a"));
        System.out.println(j.estadisticasEtiqueta("br"));
        System.out.println(j.estadisticasEtiqueta("div"));
        System.out.println(j.estadisticasEtiqueta("li"));
        System.out.println(j.estadisticasEtiqueta("ul"));
        System.out.println(j.estadisticasEtiqueta("p"));
        System.out.println(j.estadisticasEtiqueta("span"));
        System.out.println(j.estadisticasEtiqueta("table"));
        System.out.println(j.estadisticasEtiqueta("td"));
        System.out.println(j.estadisticasEtiqueta("tr"));
        System.out.println(j.obtenerContenidoImg());
        System.out.println(j.obtenerHiperenlaces());
        System.out.println(j.obtenerHiperenlacesImagenes());
        j.VolcarFichero();

    }
}
