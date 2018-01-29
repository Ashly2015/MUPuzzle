/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Daniel
 */

import java.util.ArrayList;
import java.util.Scanner;

class Teorema{
    public String cad;
    public Teorema papa;
    public int id;

    public Teorema(String cad){
        this.cad = cad;
        papa = null;
    }

    public Teorema(String cad, Teorema t){
        this.cad = cad ;
        this.papa = t;
    }

    public String toString(){
        return "Teo : "+ cad;
    }
    
}


public class GeneradorTeoremas{
    
    
    //Regresa todos los posibles cambios XIIIY -> XUY
    public String cambiaI_U(int inicio, int fin, String teo){
        String strI=teo.substring(inicio, fin);
        String res ="";
        
        if(strI.length()==3 && strI.compareTo("III")==0){
            res=teo.substring(0, inicio)+'U'+teo.substring(fin, teo.length());
        }
        
        return res;
    }

    //Regresa todos los posibles cambios XUUY -> XY
    public String quitaUU(int inicio, int fin, String teo){
        String strU=teo.substring(inicio, fin);
        String res ="";
        
        if(strU.length()==2 && strU.compareTo("UU")==0){
            res=teo.substring(0, inicio)+teo.substring(fin, teo.length());
        }
        
        return res;
    }

    //regresa XI -> XIU
    public String ponU(String teo){
        String res = "";
        int size=teo.length();
        char lastChar=teo.charAt(size-1);

        if(lastChar=='I'){
            res=teo+'U';
            //return true;
        }
        
        //return false;
        return res;
    }

    //MX -> MXX
    public String duplica(String teo){
        String dup=teo.substring(1);
        String res = teo;
        
        if(!dup.isEmpty()){
            res=res+dup;
        }
        else
            res = "";
        
        return res;
    }

    //Imprime los resultados , debe guardarlos en lista
    public void posiblesCambiosI_U(String teo, ArrayList<String> res){
        int tam = teo.length();
        int i = 0;
        String aux ="";

        while(0 <= i && i <= tam-4){

            i = teo.indexOf("III", i);
            if( i >= 0){
                aux = cambiaI_U(i, i+3, teo);
                if(aux != "")
                    res.add(aux);
                i++;
            }

        }
    }

    //Falta hacer que guarde resultados
    public void posiblesCambios_UU(String teo, ArrayList<String> res){
        int tam = teo.length();
        int i = 0;
        String aux ="";

        while(0 <= i && i <= tam-3){

            i = teo.indexOf("UU", i);
            if( i >= 0){
                aux = quitaUU(i, i+2, teo);
                if(aux != "")
                    res.add(aux);
                i++;
            }

        }
    }

    public ArrayList<String> generaSigPaso(String teo){
        String aux = "";
        ArrayList<String> res = new ArrayList();

        aux = ponU(teo);
        if( aux != "" )
            res.add(aux);

        aux = duplica(teo);
        if( aux != "")
            res.add(aux);
        posiblesCambiosI_U(teo, res);
        posiblesCambios_UU(teo, res);

        return res;
    }

    public ArrayList <Teorema> generaTeo(int n, String teo){
        ArrayList<Teorema> res = new ArrayList();
        ArrayList<Teorema> pisoAct = new ArrayList();
        ArrayList<Teorema> pisoSig = new ArrayList();
        ArrayList<String> sigPaso ;
        Teorema axioma = new Teorema(teo);
        res.add(axioma);
        int cont = 0;
        pisoAct.add(axioma);
        System.out.println("Inicio: "+teo);

        while( cont < n){

            for(Teorema teoAct : pisoAct){
                sigPaso = generaSigPaso(teoAct.cad);

                for( String cad : sigPaso){

                    if(cont >= n)
                        return res;


                    Teorema aux = new Teorema(cad, teoAct); 
                    pisoSig.add( aux);
                    res.add(aux);
                    cont++;
                    aux.id = cont;
                    System.out.println(cont+": "+cad);
                }
            }
            pisoAct = pisoSig;
            pisoSig = new ArrayList();
        }

        return res;
    }

    public void demuestra(int id, ArrayList<Teorema> teos){

        if(id < teos.size()){
            demuestra(teos.get(id), 0);
            System.out.println("");
        }
    }

    public void demuestra(Teorema teo, int indicador){
        Teorema act;
        act = teo;

        if( act.papa == null)
            System.out.print(act.cad + " => ");
            else{
                demuestra(act.papa, indicador + 1);
                System.out.print(act.cad);
                if( indicador != 0)
                    System.out.print(" => ");
            }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MUPuzzle mu=new MUPuzzle();
        Scanner scanner = new Scanner(System.in);
        int id = 40;
        int n = 40;
        String inicio = "MI";
        boolean seguir = true;
        ArrayList<Teorema> res;

        while( true ){
            System.out.println("Escriba cadena de inicio: ");
            inicio= scanner.next();
            System.out.println("Numero de teoremas a generar: ");
            n = scanner.nextInt();
            res = mu.generaTeo(n,inicio);

            while( true ){
                System.out.println("Numero de teorema a demostrar: ");
                id = scanner.nextInt();
                System.out.println("Demostracion de teorema: "+id);
                mu.demuestra(id, res);
                //System.out.println("Seguir ? ");
                //seguir = scanner.nextBoolean();
            }
            


        }

        
        
       
        
        //scanner.close();

        //System.out.println(n != "");

       // Teorema axioma = new Teorema("MI");
       // System.out.println("Este es un axioma: " +axioma.cad + axioma.papa);


    }
    
}
