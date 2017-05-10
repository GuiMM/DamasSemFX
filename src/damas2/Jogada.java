/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damas2;

import java.util.Objects;

/**
 *
 * @author Omar
 */
class Jogada {
    private final String tipo;
    private final Celula destino;
    private Peca pecaCapturada;
    private Peca pecaJogada;

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

    public void setPecaJogada(Peca pecaJogada) {
        this.pecaJogada = pecaJogada;
    }
    
    public Peca getPecaJogada() {
        return pecaJogada;
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
        if (pecaCapturada.getJogador().equals("C")) {
            t.getPretas().remove(pecaCapturada);
        } else {
            t.getBrancas().remove(pecaCapturada);
        }
        t.getTabuleiro()[pecaCapturada.getPosX()][pecaCapturada.getPosY()].setPeca(null);
        movimentar(t,aJogar);
    }
    
    private void movimentar(Tabuleiro t, Peca aJogar){
        t.getTabuleiro()[destino.getX()][destino.getY()].setPeca(aJogar);
        t.getTabuleiro()[aJogar.getPosX()][aJogar.getPosY()].setPeca(null);
        aJogar.setPosX(destino.getX()); aJogar.setPosY(destino.getY());         //atualizando a posicao da peca
        if((aJogar.getPosX()==0 && aJogar.getJogador().equals("C"))||
           (aJogar.getPosX()==7 && aJogar.getJogador().equals("H")))
            fazerDama(aJogar);
    }
    
    private void fazerDama(Peca aJogar){
        aJogar.setEh_dama(true);
        String novoId = aJogar.getId();
        if(aJogar.getJogador().equals("C"))
            aJogar.setId(novoId.replace("pr","PR"));
        else
            aJogar.setId(novoId.replace("br","BR"));
    }
    
    @Override
    public boolean equals(Object o){
        boolean retVal = false;
        if(o instanceof Jogada){
            Jogada j = (Jogada)o;
            retVal = j.tipo.equals(this.tipo);
        }
        return retVal;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.tipo);
        return hash;
    }
}
