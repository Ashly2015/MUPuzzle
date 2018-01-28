/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mupuzzle;

import java.util.ArrayList;

/**
 *
 * @author Daniel
 */
public class MUPuzzle {
    
    private ArrayList<String> arrStr=new ArrayList();

    public MUPuzzle() {
    }

    public String getArrStr() {
        String res="";
        
        for (int i = arrStr.size()-1; i >= 0; i--) {
            res+=arrStr.get(i);
            res+="\n";
        }
        
        return res;
    }
    
    //Primera regla: Agrega U
    public String regla1(String axioma){
        return axioma+'U';
    }
    
    public boolean checaRegla1(String axioma){
        int size=axioma.length();
        char lastChar=axioma.charAt(size-1);
        if(lastChar=='I'){
            return true;
        }
        
        return false;
    }
    
    public String regla2(String axioma){
        String dup=axioma.substring(1);
        
        return axioma+dup;
    }
    
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

    public String regla3(String axioma){

        return axioma.replaceFirst("III", "U");
    }
    
    public boolean checaRegla3(String axioma){
        return axioma.contains("III");
    }
    
    public String regla4(String axioma){
        
        return axioma.replaceFirst("UU", "");
    }
    
    public boolean checaRegla4(String axioma){
        return axioma.contains("UU");
    }
    
    public boolean checaI(String str){
        String strTmp=str.replaceAll("III", "");
        if(str.length()>strTmp.length())
            return true;
        return false;
    }
    
    public boolean checaU(String str){
        String strTmp=str.replaceAll("UU", "");
        if(str.length()>strTmp.length())
            return true;
        return false;
    }
    
    public boolean demuestraTeo(String teo){
        
        if(checaAbecedario(teo))
            return demuestraTeo(teo, "MI");
        System.out.println("SOLO palabras con M, I y/o U");
        return false;
        
    }
    
    private boolean checaAbecedario(String teo) {
        teo=teo.replace("M", "").replace("I", "").replace("U", "");
        return teo.isEmpty();
    }
    
    private boolean demuestraTeo(String teo, String axioma){
        
        if(axioma.compareTo(teo)==0){
            arrStr.add(axioma);
            return true;
        }
        
        boolean status=false;
        
        // Ver qué reglas aplicar
        if(!status&&this.checaRegla1(axioma)) //Regla 1
            status=this.demuestraTeo(teo, this.regla1(axioma));
        if(!status&&this.checaRegla3(axioma)) //Regla 3
            status=this.demuestraTeo(teo, this.regla3(axioma));
        if(!status&&this.checaRegla4(axioma))
            status=this.demuestraTeo(teo, this.regla4(axioma));
        if(!status&&(axioma.compareTo("MI")==0||this.checaRegla2(axioma))) //Regla 2
            status=this.demuestraTeo(teo, this.regla2(axioma));
        
        if(status)
            arrStr.add(axioma);
        
        return status;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MUPuzzle mu=new MUPuzzle();
        
//        System.out.println(mu.getAxioma());
//        //        MI
//        mu.duplicate();
//        System.out.println(mu.getAxioma());
//        //        MII
//        mu.duplicate();
//        System.out.println(mu.getAxioma());
//        //        MIIII
//        if(mu.changeI2U())
//            System.out.println(mu.getAxioma());
//        else
//            System.out.println("Error");
//        //        MUI
//        mu.addU();
//        System.out.println(mu.getAxioma());
//        //        MUIU
//        mu.duplicate();
//        System.out.println(mu.getAxioma());
//        //        MUIUUIU
//        
//        if(mu.delU())
//            System.out.println(mu.getAxioma());
//        else
//            System.out.println("Error");
//        //        MUIIU

        //Probrando el último método
        try{
           if(mu.demuestraTeo("MUIIU")){
            System.out.println("Listo");
            System.out.println(mu.getArrStr());
           }
        }catch(StackOverflowError e){
            System.out.println("No se pudo encontrar solución");
        }
        
    }
    
}
