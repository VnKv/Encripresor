/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encripresor;

import java.math.BigDecimal;

/**
 *
 * @author Vanessa Sanjinez
 */
public class Encripresor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        codificacionAritmetica codificador = new codificacionAritmetica("ABACB");
        codificador.construirMapa();
        codificador.probabilidades();
        codificador.rangos();
        codificador.codificar();
        codificador.decodificador();
    }
    
}
