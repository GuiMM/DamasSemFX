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
    boolean eh_dama;
    int posX;
    int posY;
    
    public Peca(String nome){
        jogador = nome;
        eh_dama=false;
    }
    
    public void move(int posX, int posY){
       this.posX = posX;
       this.posY = posY;
       ehDama();
    }
    
    //verifica para PC ou para humano se esta peça é uma dama. Deve ser chamado sempre a cada movimento. para saber se a peça se tornou dama.
    public void ehDama(){
        boolean eh_peca_branca;
        if (jogador.equals("humano")) {                                  //verifica qual o time do jogador, vamos fazer um método que pode ser usado pelo humano ou pelo PC
            eh_peca_branca=true;
        }else
            eh_peca_branca=false;
        
        if (eh_peca_branca && posX==7) {
            eh_dama=true;
        }else if (!eh_peca_branca && posX==0) {
            eh_dama=true;
        }
            
    
    }
}
