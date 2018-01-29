/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mupuzzle;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Daniel
 */
public class MUPuzzle {
    
    private ArrayList<String> arrStr;
    private ArrayList<Integer> arrRules;

    public MUPuzzle() {
    }

    
    /**
     * 
     * @return La serie de pasos realizados para llegar al teorema
     */
    public String getArrStr() {
        String res="";
        
        for (int i = this.arrStr.size()-1; i >= 0; i--) {
            res+=this.arrStr.get(i);
            if(i<=this.arrRules.size()-1)
                res=res+"\tRegla ("+this.arrRules.get(i)+")";
            else
                res=res+"\tAxioma";
            res+="\n";
        }
        
        return res;
    }
    
    //Primera regla: Agrega U
    /**
     * 
     * @param axioma    La cadena de M, I o U a demostrar
     * @return La cadena modificada por la regla 1 (Agrega U al final)
     */
    public String regla1(String axioma){
        return axioma+'U';
    }
    
    /**
     * 
     * @param axioma    La cadena de M, I o U a demostrar
     * @return Checa si el axioma es candidato a usar la regla 1
     */
    public boolean checaRegla1(String axioma){
        int size=axioma.length();
        char lastChar=axioma.charAt(size-1);
        if(lastChar=='I'){
            return true;
        }
        
        return false;
    }
    
    /**
     * 
     * @param axioma    La cadena de M, I o U a demostrar
     * @return La cadena modificada por la regla 2 (Mx -> Mxx)
     */
    public String regla2(String axioma){
        String dup=axioma.substring(1);
        
        return axioma+dup;
    }
    
    /**
     * 
     * @param axioma    La cadena de M, I o U a demostrar
     * @return Checa si el axioma es candidato a usar la regla 2
     */
    public boolean checaRegla2(String axioma){
        String fst2=axioma.substring(1,3);
        String lst2=axioma.substring(axioma.length()-2);
        
        if(axioma.length()>4&&((fst2.compareTo("UU")==0&&lst2.compareTo("UU")==0)||
                (fst2.compareTo("UI")==0&&lst2.compareTo("IU")==0)))
            return true;
        if((fst2.compareTo("II")==0&&lst2.compareTo("UI")==0)||(fst2.compareTo("IU")==0&&lst2.compareTo("II")==0)||(fst2.compareTo("II")==0&&lst2.compareTo("II")==0))
            return true;
        return false;
    }

    /**
     * 
     * @param axioma    La cadena de M, I o U a demostrar
     * @return La cadena modificada por la regla 3 (Sustituir "III" por "U")
     */
    public String regla3(String axioma){

        return axioma.replaceFirst("III", "U");
    }
    
    /**
     * 
     * @param axioma    La cadena de M, I o U a demostrar
     * @return Checa si el axioma es candidato a usar la regla 3
     */
    public boolean checaRegla3(String axioma){
        return axioma.contains("III");
    }
    
    /**
     * 
     * @param axioma    La cadena de M, I o U a demostrar
     * @return La cadena modificada por la regla 4 (Remover "UU")
     */
    public String regla4(String axioma){
        
        return axioma.replaceFirst("UU", "");
    }
    
    /**
     * 
     * @param axioma    La cadena de M, I o U a demostrar
     * @return Checa si el axioma es candidato a usar la regla 4
     */
    public boolean checaRegla4(String axioma){
        return axioma.contains("UU");
    }
    
    /**
     * 
     * @param teo   Cadena ingresada por el usuario para analizar si es demostrable
     * @return Si se pudo demostrar el teorema
     */
    public boolean demuestraTeo(String teo){
        
        this.arrStr=new ArrayList();
        this.arrRules=new ArrayList();
        
        return demuestraTeo(teo, "MI");
        
    }
    
    /**
     * 
     * @param teo   Cadena que contiene una combinación de letras (símbolos)
     * @return Si la cadena contiene solo los elementos
     * del sistema MU: M, I y/o U
     */
    private boolean checaAbecedario(String teo) {
        teo=teo.replace("M", "").replace("I", "").replace("U", "");
        return teo.isEmpty();
    }
    
    /**
     * 
     * @param teo       La cadena a demostrar
     * @param axioma    El único axioma del sistema: "MI"
     * @return  Si se pudo demostrar el teorema
     */
    private boolean demuestraTeo(String teo, String axioma){
        
        if(axioma.compareTo(teo)==0){
            this.arrStr.add(axioma);
            return true;
        }
        
        boolean status=false;
        
        // Ver qué reglas aplicar
        if(this.checaRegla1(axioma)){ //Regla 1
            status=this.demuestraTeo(teo, this.regla1(axioma));
            if(status)
                this.arrRules.add(1);
        }
        if(!status&&this.checaRegla3(axioma)){ //Regla 3
            status=this.demuestraTeo(teo, this.regla3(axioma));
            if(status)
                this.arrRules.add(3);
        }
        if(!status&&this.checaRegla4(axioma)){ //Regla 4
            status=this.demuestraTeo(teo, this.regla4(axioma));
            if(status)
                this.arrRules.add(4);
        }
        if(!status&&(axioma.compareTo("MI")==0||this.checaRegla2(axioma))){ //Regla 2
            status=this.demuestraTeo(teo, this.regla2(axioma));
            if(status)
                this.arrRules.add(2);
        }
        
        if(status)
            arrStr.add(axioma);
        
        return status;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MUPuzzle mu=new MUPuzzle();
        Scanner scan=new Scanner(System.in);
        
        System.out.println("Escribe un teorema o exit para salir del programa:");
        String teo=scan.next();
        
        while(teo.compareTo("exit")!=0){
            if(mu.checaAbecedario(teo)){
                try{
                   if(mu.demuestraTeo(teo)){
                    System.out.println("Listo");
                    System.out.println(mu.getArrStr());
                   }
                }catch(StackOverflowError e){
                    System.out.println("No se pudo encontrar solución");
                }            
            } else{
                System.out.println("SOLO palabras con M, I y/o U");
            }
            System.out.println("Escribe otro teorema o exit:");
            teo=scan.next();
        }
        
        System.exit(0);
    }
    
}
