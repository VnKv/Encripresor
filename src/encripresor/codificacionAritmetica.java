/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encripresor;

import java.util.*;

/**
 *
 * @author Kevin
 */
public class codificacionAritmetica {
    
    private String mensaje;
    private Map<String,Float> map;
    private Map<String,tupla> mapTuplas;
    private float mensajeCodificado;
    private String mensajeDecodificado;
    
    public codificacionAritmetica(String mensaje){
        this.map = new HashMap<String,Float>();
        this.mapTuplas = new HashMap<String,tupla>();
        this.mensaje = mensaje;
        this.mensajeCodificado = 0;
        this.mensajeDecodificado = "";
    }

    //Obtiene las probabilidades de cada caracter que se encuentra en el mensaje
    public void probabilidades(){
        String letra;
        float largo = (float) mensaje.length();
        float probabilidad;
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
            map.put(letra,1f);
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
        float rangominTemporal = 0;
        float rangomaxTemporal = 0;
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
    //Calculo de nueva Rango para el proceso de compresion
    public tupla nuevoRango(tupla rangoActual,String letra){
        tupla nuevoRango =  new tupla(0,0);
        float a = rangoActual.getRangomin();
        float b = rangoActual.getRangomax();
        float ai = mapTuplas.get(letra).getRangomin();
        float bi = mapTuplas.get(letra).getRangomax();
        nuevoRango.setRangomin(a+((b-a)*ai));
        nuevoRango.setRangomax(a+((b-a)*bi));
        return nuevoRango;
    }
    
    public void prueba(){
        tupla ta = new tupla(0f,0.5f);
        tupla tb = new tupla(0.5f,0.7f);
        tupla tc = new tupla(0.7f,1.0f);
        mapTuplas.put("A",ta);
        mapTuplas.put("B",tb);
        mapTuplas.put("C",tc);
        codificar();
        System.out.println("Mensaje Codificado: "+mensajeCodificado);
        decodificador();
    }
    //Codificador aritmetico
    public void codificar(){
        System.out.println("Codificacion");
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
        System.out.println("Mensaje Codificacido: "+mensajeCodificado);
    }
    
    public void decodificador(){
        System.out.println("Decodificacion");
        float valorRango = mensajeCodificado;
        String letra;
        for(int i=0;i<mensaje.length();i++){
            letra = enRango(valorRango);
            System.out.println("ValorRango: "+valorRango
                    +"LetraDecodificada: " + letra);
            mensajeDecodificado = mensajeDecodificado + letra;
            valorRango = valorRango(valorRango,mapTuplas.get(letra));
        }
        System.out.println("Mensaje Decodificado: "+mensajeDecodificado);
    }
    
    public float valorRango(float valorActual,tupla rango){
        float a = rango.getRangomin();
        float b = rango.getRangomax();
        float ai = valorActual;
        return (ai - a) /(b - a);
    }
    
    public String enRango(float valorRango){
        String letra = "";
        Iterator it = mapTuplas.keySet().iterator();
        while(it.hasNext()){
            letra = it.next().toString();
            if(mapTuplas.get(letra).getRangomin() <= valorRango 
                    && valorRango < mapTuplas.get(letra).getRangomax()){
                return letra;
            }
        }
        return null;
    }
    
    class tupla{
        float rangomin;
        float rangomax;

        public tupla(float rangomin, float rangomax) {
            this.rangomin = rangomin;
            this.rangomax = rangomax;
        }

        public float getRangomin() {
            return rangomin;
        }

        public void setRangomin(float rangomin) {
            this.rangomin = rangomin;
        }

        public float getRangomax() {
            return rangomax;
        }

        public void setRangomax(float rangomax) {
            this.rangomax = rangomax;
        }
        
    }
}
