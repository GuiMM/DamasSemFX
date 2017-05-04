/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damas2;

/**
 *
 * @author VBrum
 */
public class Celula {
    public Peca p;
    public int x;
    public int y;
    
    public Celula(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    public Peca getPeca(){
        return p;
    }
    
    public void setPeca(Peca p){
        this.p = p;
    }
    
    public int getX(){
        return x;
    }
    
    public void setX(int x){
        this.x = x;
    }
    
    public int getY(){
        return y;
    }
    
    public void setY(int y){
        this.y = y;
    }
    
    public boolean isEmpty(){
        return p == null;
    }
    
    public void inserirPeca(Peca p){
        this.p = p;
    }
    
}
