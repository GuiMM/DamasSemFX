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
    private ArrayList <Peca> brancas = new ArrayList(12);
    private ArrayList <Peca> pretas = new ArrayList(12);
    private Celula[][] tabuleiro;
    
    public Tabuleiro(int tam){
        tabuleiro = new Celula[tam][tam];
        inicializarTabuleiro();
        criaPecas();
        distribuiPecas();
    }
    
    public Tabuleiro(){
        
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

    public void setBrancas(ArrayList<Peca> brancas) {
        this.brancas = brancas;
    }

    public void setPretas(ArrayList<Peca> pretas) {
        this.pretas = pretas;
    }

    public void setTabuleiro(Celula[][] tabuleiro) {
        this.tabuleiro = tabuleiro;
    }
    
     public int funcaoDeAvaliacao(){
        int result =0;        
        result = pretas.size()-brancas.size();
        return result;
    }
    /*Método responsável por gerar uma lista de possíveis jogadas de movimentação
    ou captura, de uma peça comum. (FALTA ANALISAR CAPTURA PARA TRÁS)*/
    public ArrayList<Jogada> verificaJogadas(Peca p){
        Map<String,Jogada> provaveisJogadas = new HashMap<>();
        Celula diagonalEsq;
        Celula diagonalEsqSeguinte;
        Celula diagonalDir;
        Celula diagonalDirSeguinte;
        
        if (p.getId().contains("pr")) { //verifica se a peça da vez é preta ou branca
            try{diagonalDir = tabuleiro[p.getPosX() - 1][p.getPosY() + 1];}catch(Exception e){diagonalDir = null;}
            try{diagonalDirSeguinte = tabuleiro[p.getPosX() - 2][p.getPosY() + 2];}catch(Exception e){diagonalDirSeguinte = null;}
            try{diagonalEsq = tabuleiro[p.getPosX() + 1][p.getPosY() - 1];}catch(Exception e){diagonalEsq = null;}
            try{diagonalEsqSeguinte = tabuleiro[p.getPosX() + 2][p.getPosY() - 2];}catch(Exception e){diagonalEsqSeguinte = null;}
        } else {
            try{diagonalEsq = tabuleiro[p.getPosX() - 1][p.getPosY() - 1];}catch(Exception e){diagonalEsq = null;}
            try{diagonalEsqSeguinte = tabuleiro[p.getPosX() - 2][p.getPosY() - 2];}catch(Exception e){diagonalEsqSeguinte = null;}
            try{diagonalDir = tabuleiro[p.getPosX() + 1][p.getPosY() + 1];}catch(Exception e){diagonalDir = null;}
            try{diagonalDirSeguinte = tabuleiro[p.getPosX() + 2][p.getPosY() + 2];}catch(Exception e){diagonalDirSeguinte = null;}
        }
        
        if (p.getPosY() < 6 && p.getPosY() > 1) { //a peça não está próxima ou exatamente,
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
    
    /*Método responsável por gerar uma lista de possíveis jogadas de movimentação
    ou captura, de uma dama. (FALTA ANALISAR CAPTURA PARA TRÁS)*/
//    public Map<String,Jogada> verificaJogadasDama(Peca p){
//        
//    }

    private ArrayList<Jogada> filtraJogadas(Map<String, Jogada> provaveisJogadas) {
        if(provaveisJogadas.containsKey("captura"))
            provaveisJogadas.remove("movimentacao");
        return new ArrayList<>(provaveisJogadas.values());
    }
    
    public String fimDeJogo(){
        String resp = "Fim de Jogo.";
        if(brancas.isEmpty())
            return resp+" Vencedor: COMPUTADOR";
        else {
            if(pretas.isEmpty())
                return resp+" Vencedor: HUMANO";
            else{
                return "";
            }
        }
    }
    
    public Tabuleiro copiaTabuleiro(){
        Tabuleiro novo = new Tabuleiro();
        ArrayList<Peca> br = new ArrayList<>();
        for (int i = 0; i < brancas.size(); i++) {
            br.add(brancas.get(i));
        }
        novo.setBrancas(br);
        ArrayList<Peca> pr = new ArrayList<>();
        for (int i = 0; i < pretas.size(); i++) {
            br.add(pretas.get(i));
        }
        novo.setPretas(pr);
        Celula[][] tab = new Celula[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tab[i][j]=tabuleiro[i][j];
            }
        }       
        novo.setTabuleiro(tab);
        return novo;
    }
}    