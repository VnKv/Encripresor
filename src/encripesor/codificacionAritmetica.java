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
    private Map<String,tupla> mapTuplas;
    private double mensajeCodificado;
    
    public codificacionAritmetica(String mensaje){
        this.map = new HashMap<String,Double>();
        this.mapTuplas = new HashMap<String,tupla>();
        this.mensaje = mensaje;
        this.mensajeCodificado = 0;
    }

    //Obtiene las probabilidades de cada caracter que se encuentra en el mensaje
    public void probabilidades(){
        String letra;
        double largo = (double) mensaje.length();
        double probabilidad;
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
    //Calculas los rangos de cada caracter del mapa
    public void rangos(){
        String letra;
        double rangominTemporal = 0;
        double rangomaxTemporal = 0;
        Iterator it = map.keySet().iterator();
        while(it.hasNext()){
            letra = it.next().toString();
            rangomaxTemporal = rangominTemporal+map.get(letra);
            tupla rango = new tupla(rangominTemporal,rangomaxTemporal);
            mapTuplas.put(letra,rango);
            rangominTemporal = rangomaxTemporal;
        }
        revisarMapaTuplas();
    }
    //Muestra los rango de cada uno de los caracteres del mensaje
    public void revisarMapaTuplas(){
        String letra = "";
        Iterator it = mapTuplas.keySet().iterator();
        while(it.hasNext()){
            letra = it.next().toString();
            System.out.println("Clave: "+letra+" Valor: ["
                    +mapTuplas.get(letra).getRangomin()+","
                    +mapTuplas.get(letra).getRangomax()+"]");
        }
    }
    
    public tupla nuevoRango(tupla rangoActual,String letra){
        tupla nuevoRango =  new tupla(0,0);
        double a = rangoActual.getRangomin();
        double b = rangoActual.getRangomax();
        double ai = mapTuplas.get(letra).getRangomin();
        double bi = mapTuplas.get(letra).getRangomax();
        nuevoRango.setRangomin(a+((b-a)*ai));
        nuevoRango.setRangomax(a+((b-a)*bi));
        return nuevoRango;
    }
    
    public void prueba(){
        tupla ta = new tupla(0,0.5);
        tupla tb = new tupla(0.5,0.7);
        tupla tc = new tupla(0.7,1.0);
        mapTuplas.put("A",ta);
        mapTuplas.put("B",tb);
        mapTuplas.put("C",tc);
        codificar();
        System.out.println("Mensaje Codificado: "+mensajeCodificado);
    }
    
    public void codificar(){
        tupla actual = new tupla(0,1);
        for(int i=0;i<mensaje.length();i++){
            String letra = Character.toString(mensaje.charAt(i));
            tupla nueva = nuevoRango(actual,letra);
            System.out.println("Clave: "+letra+" Valor: ["
                    +nueva.getRangomin()+","
                    +nueva.getRangomax()+"]");
            actual = nueva;
        }
        mensajeCodificado = actual.getRangomin();
    }
    
    class tupla{
        double rangomin;
        double rangomax;

        public tupla(double rangomin,double rangomax){
            this.rangomin = rangomin;
            this.rangomax = rangomax;
        }

        public double getRangomin() {
            return rangomin;
        }

        public void setRangomin(double rangomin) {
            this.rangomin = rangomin;
        }

        public double getRangomax() {
            return rangomax;
        }

        public void setRangomax(double rangomax) {
            this.rangomax = rangomax;
        }
    }
}
