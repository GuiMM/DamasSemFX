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
                Peca pr = new Peca("pr"+i,"C");
                pretas.add(pr);
            }else{
                Peca br = new Peca("br"+i,"H");
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
                    brancas.get(aux).setPosY(j*2+1);
                    tabuleiro[i][j*2+1].setPeca(brancas.get(aux));                   
                }else
                {
                    brancas.get(aux).setPosX(i);
                    brancas.get(aux).setPosY(j*2);
                    tabuleiro[i][j*2].setPeca(brancas.get(aux));
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
    
    public ArrayList<Jogada> verificaJogadas(Peca p){
        if(!p.eh_Dama()){
            return filtraJogadas(avaliaJogadasPecaComum(p));
        }else{
            return filtraJogadas(avaliaJogadasDama(p));
        }
    }
    
    private ArrayList<Jogada> verificaCaptura(ArrayList<Jogada> jogadas, 
                                Celula frente, Celula frenteSeguinte, 
                                Celula tras, Celula trasSeguinte, Peca p){
        if(!frente.getPeca().getJogador().equals(p.getJogador())
           && frenteSeguinte.isEmpty()){
            Peca pecaCapturar = frente.getPeca();
            jogadas.add(new Jogada("captura",frenteSeguinte,pecaCapturar));
        }
        if(tras!=null && trasSeguinte!=null){//no caso da dama, estas células são nulas para este método
            if(!tras.getPeca().getJogador().equals(p.getJogador())
               && trasSeguinte.isEmpty()){
                Peca pecaCapturar = tras.getPeca();
                jogadas.add(new Jogada("captura",trasSeguinte,pecaCapturar));
            }
        }
        
        return jogadas;
    }
    
    /*Método responsável por gerar uma lista de possíveis jogadas de movimentação
    ou captura, de uma peça comum.*/
    private ArrayList<Jogada> avaliaJogadasPecaComum(Peca p){
        ArrayList<Jogada> provaveisJogadas = new ArrayList<>();
        Celula diagonalEsqFrente;
        Celula diagonalEsqFrenteSeg;
        Celula diagonalDirFrente;
        Celula diagonalDirFrenteSeg;
        Celula diagonalEsqTras;
        Celula diagonalDirTras;
        Celula diagonalEsqTrasSeg;
        Celula diagonalDirTrasSeg;

        if (p.getId().contains("pr")) { //verifica se a peça da vez é preta ou branca
            try {diagonalDirFrente = tabuleiro[p.getPosX() - 1][p.getPosY() + 1];} catch (Exception e) {diagonalDirFrente = null;}
            try {diagonalDirFrenteSeg = tabuleiro[p.getPosX() - 2][p.getPosY() + 2];} catch (Exception e) {diagonalDirFrenteSeg = null;}
            try {diagonalEsqFrente = tabuleiro[p.getPosX() - 1][p.getPosY() - 1];} catch (Exception e) {diagonalEsqFrente = null;}
            try {diagonalEsqFrenteSeg = tabuleiro[p.getPosX() - 2][p.getPosY() - 2];} catch (Exception e) {diagonalEsqFrenteSeg = null;}
            try {diagonalDirTras = tabuleiro[p.getPosX() + 1][p.getPosY() + 1];} catch (Exception e) {diagonalDirTras = null;}
            try {diagonalEsqTras = tabuleiro[p.getPosX() + 1][p.getPosY() - 1];} catch (Exception e) {diagonalEsqTras = null;}
            try {diagonalDirTrasSeg = tabuleiro[p.getPosX() + 2][p.getPosY() + 2];} catch (Exception e) {diagonalDirTrasSeg = null;}
            try {diagonalEsqTrasSeg = tabuleiro[p.getPosX() + 2][p.getPosY() - 2];} catch (Exception e) {diagonalEsqTrasSeg = null;}
        } else {
            try {diagonalEsqFrente = tabuleiro[p.getPosX() + 1][p.getPosY() - 1];} catch (Exception e) {diagonalEsqFrente = null;}
            try {diagonalEsqFrenteSeg = tabuleiro[p.getPosX() + 2][p.getPosY() - 2];} catch (Exception e) {diagonalEsqFrenteSeg = null;}
            try {diagonalDirFrente = tabuleiro[p.getPosX() + 1][p.getPosY() + 1];} catch (Exception e) {diagonalDirFrente = null;}
            try {diagonalDirFrenteSeg = tabuleiro[p.getPosX() + 2][p.getPosY() + 2];} catch (Exception e) {diagonalDirFrenteSeg = null;}
            try {diagonalDirTras = tabuleiro[p.getPosX() - 1][p.getPosY() + 1];} catch (Exception e) {diagonalDirTras = null;}
            try {diagonalEsqTras = tabuleiro[p.getPosX() - 1][p.getPosY() - 1];} catch (Exception e) {diagonalEsqTras = null;}
            try {diagonalDirTrasSeg = tabuleiro[p.getPosX() - 2][p.getPosY() + 2];} catch (Exception e) {diagonalDirTrasSeg = null;}
            try {diagonalEsqTrasSeg = tabuleiro[p.getPosX() - 2][p.getPosY() - 2];} catch (Exception e) {diagonalEsqTrasSeg = null;}
        }

        if (p.getPosY() < 6 && p.getPosY() > 1) { //a peça não está próxima ou exatamente,
            //nas laterais
            if (diagonalEsqFrente.isEmpty()) {
                provaveisJogadas.add(new Jogada("movimentacao", diagonalEsqFrente));
            } else {
                ArrayList<Jogada> temp = verificaCaptura(provaveisJogadas, diagonalEsqFrente, diagonalEsqFrenteSeg,
                        diagonalEsqTras, diagonalEsqTrasSeg, p);
                if(temp!=null)
                    provaveisJogadas.addAll(temp);
            }

            if (diagonalDirFrente.isEmpty()) {
                provaveisJogadas.add(new Jogada("movimentacao", diagonalDirFrente));
            } else {
                ArrayList<Jogada> temp = verificaCaptura(provaveisJogadas, diagonalDirFrente, diagonalDirFrenteSeg,
                        diagonalDirTras, diagonalDirTrasSeg, p);
                if(temp!=null)
                    provaveisJogadas.addAll(temp);
            }

        } else if (p.getPosY() == 0) { //a peça está na lateral esquerda
            if (diagonalDirFrente.isEmpty()) {
                provaveisJogadas.add(new Jogada("movimentacao", diagonalDirFrente));
            } else {
                ArrayList<Jogada> temp = verificaCaptura(provaveisJogadas, diagonalDirFrente, diagonalDirFrenteSeg,
                        diagonalDirTras, diagonalDirTrasSeg, p);
                if(temp!=null)
                    provaveisJogadas.addAll(temp);
            }

        } else if (p.getPosY() == 7) { //a peça está na lateral direita
            if (diagonalEsqFrente.isEmpty()) {
                provaveisJogadas.add(new Jogada("movimentacao", diagonalEsqFrente));
            } else {
                ArrayList<Jogada> temp = verificaCaptura(provaveisJogadas, diagonalEsqFrente, diagonalEsqFrenteSeg,
                        diagonalEsqTras, diagonalEsqTrasSeg, p);
                if(temp!=null)
                    provaveisJogadas.addAll(temp);
            }
        } else if (p.getPosY() == 1) { //a peça está próxima a lateral esquerda
            if (diagonalEsqFrente.isEmpty()) {
                provaveisJogadas.add(new Jogada("movimentacao", diagonalEsqFrente));
            }

            if (diagonalDirFrente.isEmpty()) {
                provaveisJogadas.add(new Jogada("movimentacao", diagonalDirFrente));
            } else {
                ArrayList<Jogada> temp = verificaCaptura(provaveisJogadas, diagonalDirFrente, diagonalDirFrenteSeg,
                        diagonalDirTras, diagonalDirTrasSeg, p);
                if(temp!=null)
                    provaveisJogadas.addAll(temp);
            }

        } else if (p.getPosY() == 6) { //a peça está próxima a lateral direita
            if (diagonalEsqFrente.isEmpty()) {
                provaveisJogadas.add(new Jogada("movimentacao", diagonalEsqFrente));
            } else {
                ArrayList<Jogada> temp = verificaCaptura(provaveisJogadas, diagonalEsqFrente, diagonalEsqFrenteSeg,
                        diagonalEsqTras, diagonalEsqTrasSeg, p);
                if(temp!=null)
                    provaveisJogadas.addAll(temp);
            }

            if (diagonalDirFrente.isEmpty()) {
                provaveisJogadas.add(new Jogada("movimentacao", diagonalDirFrente));
            }
        }
        return provaveisJogadas;
    }
    
    /*Método responsável por gerar uma lista de possíveis jogadas de movimentação
    ou captura, de uma dama.*/
    private ArrayList<Jogada> avaliaJogadasDama(Peca p){
        ArrayList<Jogada> provaveisJogadas = new ArrayList<>();
        Celula atual = tabuleiro[p.getPosX()][p.getPosY()];
        Celula aux = atual;
        for (int i = 1; i < 5; i++) { //controle para percorrimento das 4 direções
            //(1->diagonalEsqFrente, 2->diagonalDirFrente, 3->diagonalDirTras, 4->diagonalEsqTras)
            switch (i) {
                case 1:
                    while(true){
                        aux.setX(aux.getX()-1);
                        aux.setY(aux.getY()-1);
                        if((aux.getX()>=0 && aux.getX()<=7) && (aux.getY()>=0 && aux.getY()<=7)){
                            if(aux.isEmpty())
                                provaveisJogadas.add(new Jogada("movimentacao",aux));
                            else{
                                Celula seguinte;
                                try{seguinte = tabuleiro[aux.getX()-1][aux.getY()-1];}catch(Exception e){seguinte = null;}
                                if(seguinte!=null)
                                    provaveisJogadas.addAll(verificaCaptura(provaveisJogadas,aux,seguinte,null,null,p));
                                break;
                            }
                        }
                        else{
                            break;
                        }
                    }
                    break;
                case 2:
                    while(true){
                        aux.setX(aux.getX()-1);
                        aux.setY(aux.getY()+1);
                        if((aux.getX()>=0 && aux.getX()<=7) && (aux.getY()>=0 && aux.getY()<=7)){
                            if(aux.isEmpty())
                                provaveisJogadas.add(new Jogada("movimentacao",aux));
                            else{
                                Celula seguinte;
                                try{seguinte = tabuleiro[aux.getX()-1][aux.getY()+1];}catch(Exception e){seguinte = null;}
                                if(seguinte!=null)
                                    provaveisJogadas.addAll(verificaCaptura(provaveisJogadas,aux,seguinte,null,null,p));
                                break;
                            }
                        }
                        else{
                            break;
                        }
                    }
                    break;
                case 3:
                    while(true){
                        aux.setX(aux.getX()+1);
                        aux.setY(aux.getY()+1);
                        if((aux.getX()>=0 && aux.getX()<=7) && (aux.getY()>=0 && aux.getY()<=7)){
                            if(aux.isEmpty())
                                provaveisJogadas.add(new Jogada("movimentacao",aux));
                            else{
                                Celula seguinte;
                                try{seguinte = tabuleiro[aux.getX()+1][aux.getY()+1];}catch(Exception e){seguinte = null;}
                                if(seguinte!=null)
                                    provaveisJogadas.addAll(verificaCaptura(provaveisJogadas,aux,seguinte,null,null,p));
                                break;
                            }
                        }
                        else{
                            break;
                        }
                    }
                    break;
                case 4:
                    while(true){
                        aux.setX(aux.getX()+1);
                        aux.setY(aux.getY()-1);
                        if((aux.getX()>=0 && aux.getX()<=7) && (aux.getY()>=0 && aux.getY()<=7)){
                            if(aux.isEmpty())
                                provaveisJogadas.add(new Jogada("movimentacao",aux));
                            else{
                                Celula seguinte;
                                try{seguinte = tabuleiro[aux.getX()+1][aux.getY()-1];}catch(Exception e){seguinte = null;}
                                if(seguinte!=null)
                                    provaveisJogadas.addAll(verificaCaptura(provaveisJogadas,aux,seguinte,null,null,p));
                                break;
                            }
                        }
                        else{
                            break;
                        }
                    }
                    break;
            }
        }
        return provaveisJogadas;   
    }

    private ArrayList<Jogada> filtraJogadas(ArrayList<Jogada> provaveisJogadas) {
        if(provaveisJogadas.contains("captura")){
            for (int i = 0; i < provaveisJogadas.size(); i++) {
                if(provaveisJogadas.get(i).getTipo().equals("movimentação"))
                    provaveisJogadas.remove(i);
            }
        }
        return provaveisJogadas;
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