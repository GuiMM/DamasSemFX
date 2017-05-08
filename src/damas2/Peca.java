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
    
    private final String id;
    private String jogador;
    private boolean eh_dama;
    private int posX;
    private int posY;
    
    public Peca(String id, String jogador){
        this.id = id;
        this.jogador = jogador;
        this.eh_dama = false;
    }
    
    public void move(int posX, int posY){
       this.posX = posX;
       this.posY = posY;
       ehDama();
    }
    
    /*verifica para PC ou para humano se esta peça é uma dama. Deve ser chamado 
    sempre a cada movimento. para saber se a peça se tornou dama.*/
    public void ehDama(){
        boolean eh_peca_branca;
        /*verifica qual o time do jogador, vamos fazer um método que pode ser
        usado pelo humano ou pelo PC*/
        eh_peca_branca = jogador.equals("H");   
        if (eh_peca_branca && posX==7) {
            eh_dama=true;
        }else if (!eh_peca_branca && posX==0) {
            eh_dama=true;
        }
    }

    public String getJogador() {
        return jogador;
    }

    public void setJogador(String jogador) {
        this.jogador = jogador;
    }

    public boolean eh_Dama() {
        return eh_dama;
    }

    public void setEh_dama(boolean eh_dama) {
        this.eh_dama = eh_dama;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public String getId() {
        return id;
    }
    
}
