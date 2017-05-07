/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damas2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author MQGuilherme
 */
class Tabuleiro {
    private final ArrayList <Peca> brancas = new ArrayList(12);
    private final ArrayList <Peca> pretas = new ArrayList(12);
    private final Celula[][] tabuleiro = new Celula[8][8];
    
    public Tabuleiro(){
        inicializarTabuleiro();
        criaPecas();
        distribuiPecas();
    }
    
    private void criaPecas(){
        for (int i=0;i<24;i++){
            if(i>=12){
                Peca pr = new Peca("pr"+i,"Computador");
                pretas.add(pr);
            }else{
                Peca br = new Peca("br"+i,"  Humano  ");
                brancas.add(br);
            }
        }
    }
    
    private void inicializarTabuleiro(){
        for(int i = 0; i < tabuleiro.length; i++){
            for(int j = 0; j < tabuleiro[i].length; j++){
                tabuleiro[i][j] = new Celula(i,j);
            }
        }
    }
    
    private void distribuiPecas() {
        int aux=0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
               
                if (i%2==0) {
                    brancas.get(aux).setPosX(i);
                    brancas.get(aux).setPosY(j*2);
                    tabuleiro[i][j*2].setPeca(brancas.get(aux));                   
                }else
                {
                    brancas.get(aux).setPosX(i);
                    brancas.get(aux).setPosY(j*2+1);
                    tabuleiro[i][j*2+1].setPeca(brancas.get(aux));
                }
                
                if (i%2==0) {
                    pretas.get(aux).setPosX(i+5);
                    pretas.get(aux).setPosY(j*2);
                    tabuleiro[i+5][j*2].setPeca(pretas.get(aux));
                    
                }else
                {
                    pretas.get(aux).setPosX(i+5);
                    pretas.get(aux).setPosY(j*2+1);
                    tabuleiro[i+5][j*2+1].setPeca(pretas.get(aux));
                    
                }
                aux++;
            }
        }
    }
    
    public void printTabuleiro(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tabuleiro[i][j].isEmpty()) {
                    System.out.print( " ______ " );
                }else
                    System.out.print(" "+tabuleiro[i][j].getPeca().getJogador()+" "+ "(" + tabuleiro[i][j].getX() + "," + tabuleiro[i][j].getY() + ")" );   
            }
            System.out.println("\n");
        }
    }
    
    public ArrayList<Peca> getBrancas() {
        return brancas;
    }

    public ArrayList<Peca> getPretas() {
        return pretas;
    }

    public Celula[][] getTabuleiro() {
        return tabuleiro;
    }
    
    /*Método responsável por gerar uma lista de possíveis jogadas de movimentação
    ou captura, de uma peça comum. (FALTA ANALISAR CAPTURA PARA TRÁS)*/
    public Map<String,Jogada> verificaJogadas(Peca p){
        Map<String,Jogada> provaveisJogadas = new HashMap<>();
        Celula diagonalEsq;
        Celula diagonalEsqSeguinte;
        Celula diagonalDir;
        Celula diagonalDirSeguinte;
        
        if (p.getId().contains("pr")) { //verifica se a peça da vez é preta ou branca
            diagonalEsq = tabuleiro[p.getPosX() - 1][p.getPosY() + 1];
            diagonalEsqSeguinte = tabuleiro[p.getPosX() - 2][p.getPosY() + 2];
            diagonalDir = tabuleiro[p.getPosX() + 1][p.getPosY() - 1];
            diagonalDirSeguinte = tabuleiro[p.getPosX() + 2][p.getPosY() - 2];
        } else {
            diagonalEsq = tabuleiro[p.getPosX() - 1][p.getPosY() - 1];
            diagonalEsqSeguinte = tabuleiro[p.getPosX() - 2][p.getPosY() - 2];
            diagonalDir = tabuleiro[p.getPosX() + 1][p.getPosY() + 1];
            diagonalDirSeguinte = tabuleiro[p.getPosX() + 2][p.getPosY() + 2];
        }
        
        if (p.getPosY() < 6 || p.getPosY() > 1) { //a peça não está próxima ou exatamente,
                                                  //nas laterais
            if (diagonalEsq.isEmpty()) {
                provaveisJogadas.put("movimentacao",new Jogada("movimentacao",diagonalEsq));
            } else if (!diagonalEsq.getPeca().getJogador().equals(p.getJogador())
                       && diagonalEsqSeguinte.isEmpty()) {
                Peca pecaCapturar = diagonalEsq.getPeca();
                provaveisJogadas.put("captura",new Jogada("captura",diagonalEsqSeguinte,pecaCapturar));
            }
            
            if (diagonalDir.isEmpty()) {
                provaveisJogadas.put("movimentacao",new Jogada("movimentacao",diagonalDir));
            } else if (!diagonalDir.getPeca().getJogador().equals(p.getJogador())
                       && diagonalDirSeguinte.isEmpty()) {
                Peca pecaCapturar = diagonalDir.getPeca();
                provaveisJogadas.put("captura",new Jogada("captura",diagonalDirSeguinte,pecaCapturar));
            }
            
        } else if (p.getPosY() == 0) { //a peça está na lateral esquerda
            if (diagonalDir.isEmpty()) {
                provaveisJogadas.put("movimentacao",new Jogada("movimentacao",diagonalDir));
            } else if (!diagonalDir.getPeca().getJogador().equals(p.getJogador())
                       && diagonalDirSeguinte.isEmpty()) {
                Peca pecaCapturar = diagonalDir.getPeca();
                provaveisJogadas.put("captura",new Jogada("captura",diagonalDirSeguinte,pecaCapturar));
            }
            
        } else if (p.getPosY() == 7) { //a peça está na lateral direita
            if (diagonalEsq.isEmpty()) {
                provaveisJogadas.put("movimentacao",new Jogada("movimentacao",diagonalEsq));
            } else if (!diagonalEsq.getPeca().getJogador().equals(p.getJogador())
                       && diagonalEsqSeguinte.isEmpty()) {
                Peca pecaCapturar = diagonalEsq.getPeca();
                provaveisJogadas.put("captura",new Jogada("captura",diagonalEsqSeguinte,pecaCapturar));
            }
        } else if(p.getPosY()== 1){ //a peça está próxima a lateral esquerda
            if (diagonalEsq.isEmpty()) {
                provaveisJogadas.put("movimentacao",new Jogada("movimentacao",diagonalEsq));
            }
            
            if (diagonalDir.isEmpty()) {
                provaveisJogadas.put("movimentacao",new Jogada("movimentacao",diagonalDir));
            } else if (!diagonalDir.getPeca().getJogador().equals(p.getJogador())
                       && diagonalDirSeguinte.isEmpty()) {
                Peca pecaCapturar = diagonalDir.getPeca();
                provaveisJogadas.put("captura",new Jogada("captura",diagonalDirSeguinte,pecaCapturar));
            }
            
        } else if(p.getPosY()== 6){ //a peça está próxima a lateral direita
            if (diagonalEsq.isEmpty()) {
                provaveisJogadas.put("movimentacao",new Jogada("movimentacao",diagonalEsq));
            } else if (!diagonalEsq.getPeca().getJogador().equals(p.getJogador())
                       && diagonalEsqSeguinte.isEmpty()) {
                Peca pecaCapturar = diagonalEsq.getPeca();
                provaveisJogadas.put("captura",new Jogada("captura",diagonalEsqSeguinte,pecaCapturar));
            }
            
            if (diagonalDir.isEmpty()) {
                provaveisJogadas.put("movimentacao",new Jogada("movimentacao",diagonalDir));
            }
        }
        
        return filtraJogadas(provaveisJogadas);
    }

    private Map<String, Jogada> filtraJogadas(Map<String, Jogada> provaveisJogadas) {
        if(provaveisJogadas.containsKey("captura"))
            provaveisJogadas.remove("movimentacao");
        return provaveisJogadas;
    }
}    