/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damas2;

import java.util.ArrayList;

/**
 *
 * @author MQGuilherme
 */
class Tabuleiro {
    ArrayList <Peça> brancas = new ArrayList(12);
    ArrayList <Peça> pretas = new ArrayList(12);
    
    Peça[][] tabuleiro = new Peça[8][8];
    
    public void printTabuleiro(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print( " "+tabuleiro[i][j]+" " );   
            }
            System.out.println("\n");
        }
    
    
    }
}
