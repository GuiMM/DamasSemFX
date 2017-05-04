/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damas2;

/**
 *
 * @author MQGuilherme
 */
class Peca {
    
    String jogador;
    int posX;
    int posY;
    
    public Peca(String nome){
        jogador = nome;
    }
    
    public void move(int posX, int posY){
       this.posX = posX;
       this.posY = posY;
    }
}
