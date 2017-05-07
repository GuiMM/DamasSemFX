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
    private final Celula destino;
    private Peca pecaCapturada;
    
    public Jogada (String tipo, Celula destino){
        this.tipo = tipo;
        this.destino = destino;
    }
    
    public Jogada (String tipo, Celula destino, Peca pecaCapturada){
        this.tipo = tipo;
        this.destino = destino;
        this.pecaCapturada = pecaCapturada;
    }
    
    public String getTipo(){
        return this.tipo;
    }
    
    public Celula getDestino() {
        return destino;
    }
    
    public void realizaJogada(Tabuleiro t, Peca aJogar){
        switch(this.tipo){
            case "captura":
                capturar(t,aJogar);
                break;
            case "movimentacao":
                movimentar(t,aJogar);
                break;
        }
    }
    
    private void capturar(Tabuleiro t, Peca aJogar){
        if (pecaCapturada.getId().contains("pr")) {
            t.getPretas().remove(pecaCapturada);
        } else {
            t.getBrancas().remove(pecaCapturada);
        }
        t.getTabuleiro()[pecaCapturada.getPosX()][pecaCapturada.getPosY()] = null;
        movimentar(t,aJogar);
    }
    
    private void movimentar(Tabuleiro t, Peca aJogar){
        t.getTabuleiro()[destino.getX()][destino.getY()].setPeca(aJogar);
        t.getTabuleiro()[aJogar.getPosX()][aJogar.getPosY()] = null;
        if((aJogar.getPosX()==0 && aJogar.getId().contains("pr"))||
           (aJogar.getPosX()==7 && aJogar.getId().contains("br")))
            fazerDama(t, aJogar);
    }
    
    private void fazerDama(Tabuleiro t, Peca aJogar){
        aJogar.setEh_dama(true);
    }
}
