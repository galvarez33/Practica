package es.ceu.gisi.modcomp.webcrawler.jflex.lexico;

/**
 * Tipos de token que puede devolver el analizador léxico.
 *
 *
 *  * @author Sergio Saugar García
 */
public enum Tipo {

    /**
     * Representa la apertura de la etiqueta
     */
    OPEN,
    /**
     * Representa el cierre de la etiqueta
     */
    CLOSE,
    /**
     * Representa un =
     */
    IGUAL,
    /**
     * Representa cualquier palabra que se pueda escribir, debemos hacer la
     * especificicacion cuando recibimos una imagen "img src" o un link " a
     * href"
     */
    PALABRA,
    /**
     * Representa el valor que van a tener los link de las imagenes y los
     * hiperenlaces
     */
    VALOR,
    /**
     * Representa la barra lateral para indicar el cierre de eqtiquetas
     */
    SLASH
}
