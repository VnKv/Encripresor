/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encripesor;

import java.util.*;

/**
 *
 * @author Kevin
 */
public class codificacionAritmetica {
    
    private String mensaje;
    private Map<String,Double> map;
    
    public codificacionAritmetica(String mensaje){
        this.map = new HashMap<String,Double>();
        this.mensaje = mensaje;
    }
    
    //Obtiene las probabilidades de cada caracter que se encuentra en el mensaje
    public void probabilidades(){
        String letra = "";
        double largo = (double) mensaje.length();
        double probabilidad = 0;
        Iterator it = map.keySet().iterator();
        while(it.hasNext()){
            letra = it.next().toString();
            probabilidad = map.get(letra)/largo;
            map.put(letra,probabilidad);
        }
        revisarMapa();
    }
    //Calcula la cantidad de veces que aparece un caracter
    public void construirMapa(){
        for(int i=0;i<mensaje.length();i++){
            String letra = Character.toString(mensaje.charAt(i));
            agregarLetra(letra);
        }
        revisarMapa();
    }
    //Agrega un nuevo caracter a el mapa comprovando previamente la existencia 
    //de este
    public void agregarLetra(String letra){
        if(!map.containsKey(letra)){
            map.put(letra,1.0);
        }else{
            map.put(letra,map.get(letra)+1);
        }
    }
    //Muestra un recorrido de todo el mapa
    public void revisarMapa(){
        String letra = "";
        Iterator it = map.keySet().iterator();
        while(it.hasNext()){
            letra = it.next().toString();
            System.out.println("Clave: "+letra+" Valor: "+map.get(letra));
        }
    }
    
    
}
