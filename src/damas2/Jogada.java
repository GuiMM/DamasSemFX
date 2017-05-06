/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damas2;

/**
 *
 * @author Omar
 */
class Jogada {
    private final String tipo;
    
    public Jogada (String tipo){
        this.tipo = tipo;
    }
    
    public String getTipo(){
        return this.tipo;
    }
}
