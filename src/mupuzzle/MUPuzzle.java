/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mupuzzle;

/**
 *
 * @author Daniel
 */
public class MUPuzzle {
    
    String str="MI"; 

    public MUPuzzle() {
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
    
    public boolean addU(){
        
        int size=str.length();
        char lastChar=str.charAt(size-1);
        if(lastChar=='I'){
            str=str+'U';
            return true;
        }
        
        return false;
    }
    
    public boolean duplicate(){
        String dup=str.substring(1);
        
        if(!dup.isEmpty()){
            str=str+dup;
            return true;
        }
        
        return false;
    }
    
    public boolean changeI2U(int inicio, int fin){
        String strI=str.substring(inicio, fin);
        
        if(strI.length()==3 && strI.compareTo("III")==0){
            str=str.substring(0, inicio)+'U'+str.substring(fin, str.length());
            return true;
        }
        
        return false;
    }
    
    public boolean delU(int inicio, int fin){
        String strU=str.substring(inicio, fin);
        
        if(strU.length()==2 && strU.compareTo("UU")==0){
            str=str.substring(0, inicio)+str.substring(fin, str.length());
            return true;
        }
        
        return false;
    }
    
    public void buildStr(String strReq){
        buildStr(strReq, "MI");
    }
    
    private void buildStr(String strReq, String strActual){
        if(strReq.compareTo(strActual)==0)
            return;
        
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MUPuzzle mu=new MUPuzzle();
        
        System.out.println(mu.getStr());
        //        MI
        mu.duplicate();
        System.out.println(mu.getStr());
        //        MII
        mu.duplicate();
        System.out.println(mu.getStr());
        //        MIIII
        if(mu.changeI2U(1, 4))
            System.out.println(mu.getStr());
        else
            System.out.println("Error");
        //        MUI
        mu.addU();
        System.out.println(mu.getStr());
        //        MUIU
        mu.duplicate();
        System.out.println(mu.getStr());
        //        MUIUUIU
        
        if(mu.delU(3, 5))
            System.out.println(mu.getStr());
        else
            System.out.println("Error");
        //        MUIIU

    }
    
}
