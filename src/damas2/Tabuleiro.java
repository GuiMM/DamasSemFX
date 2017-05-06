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
    ArrayList <Peca> brancas = new ArrayList(12);
    ArrayList <Peca> pretas = new ArrayList(12);
    
    Celula[][] tabuleiro = new Celula[8][8];
    
    public Tabuleiro(){
        Peca a1 = new Peca("humano");
        Peca a2 = new Peca("humano");
        Peca a3 = new Peca("humano");
        Peca a4 = new Peca("humano");
        Peca a5 = new Peca("humano");
        Peca a6 = new Peca("humano");
        Peca a7 = new Peca("humano");
        Peca a8 = new Peca("humano");
        Peca a9 = new Peca("humano");
        Peca a10 = new Peca("humano");
        Peca a11 = new Peca("humano");
        Peca a12 = new Peca("humano");
        Peca b1 = new Peca("Computador");
        Peca b2 = new Peca("Computador");
        Peca b3 = new Peca("Computador");
        Peca b4 = new Peca("Computador");
        Peca b5 = new Peca("Computador");
        Peca b6 = new Peca("Computador");
        Peca b7 = new Peca("Computador");
        Peca b8 = new Peca("Computador");
        Peca b9 = new Peca("Computador");
        Peca b10 = new Peca("Computador");
        Peca b11 = new Peca("Computador");
        Peca b12 = new Peca("Computador");
        
        brancas.add(a1);
        brancas.add(a2);
        brancas.add(a3);
        brancas.add(a4);
        brancas.add(a5);
        brancas.add(a6);
        brancas.add(a7);
        brancas.add(a8);
        brancas.add(a9);
        brancas.add(a10);
        brancas.add(a11);
        brancas.add(a12);
    
        pretas.add(b1);
        pretas.add(b2);
        pretas.add(b3);
        pretas.add(b4);
        pretas.add(b5);
        pretas.add(b6);
        pretas.add(b7);
        pretas.add(b8);
        pretas.add(b9);
        pretas.add(b10);
        pretas.add(b11);
        pretas.add(b12);
        inicializarTabuleiro();
        
        
        
        
        
    }
    
    public void inicializarTabuleiro(){
        for(int i = 0; i < tabuleiro.length; i++){
            for(int j = 0; j < tabuleiro[i].length; j++){
                tabuleiro[i][j] = new Celula(i,j);
            }
        }
        
        int aux=0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
               
                if (i%2==0) {
                    brancas.get(aux).posX = i;
                    brancas.get(aux).posY = j*2;
                    tabuleiro[i][j*2].p = brancas.get(aux);                   
                }else
                {
                    brancas.get(aux).posX = i;
                    brancas.get(aux).posY = j*2+1;
                    tabuleiro[i][j*2+1].p = brancas.get(aux);
                }
                
                if (i%2==0) {
                    pretas.get(aux).posX = i+5;
                    pretas.get(aux).posY = j*2;
                    tabuleiro[i+5][j*2].p = pretas.get(aux);
                    
                }else
                {
                    pretas.get(aux).posX = i+5;
                    pretas.get(aux).posY = j*2+1;
                    tabuleiro[i+5][j*2+1].p = pretas.get(aux);
                    
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
                    System.out.print(" "+tabuleiro[i][j].p.jogador+" "+ "(" + tabuleiro[i][j].x + "," + tabuleiro[i][j].y + ")" );   
            }
            System.out.println("\n");
        }
    }
    
    
    
    //Método responsável por gerar uma lista de possíveis celulas para onde ele possa se mover.(ele sempre se moverá pra frente e se ele for dama?)
    public ArrayList proximaPos(Peca p){
        ArrayList<Celula> provaveisCel = new ArrayList<>(); 
        if(p.posY < 7){                                                     //evitando arrayIndexOutOfBounds
            if(tabuleiro[p.posX+1][p.posY+1].isEmpty())
            provaveisCel.add(tabuleiro[p.posX+1][p.posY+1]);
        }
        if (p.posY > 0) {                                                   //evitando arrayIndexOutOfBounds
            if(tabuleiro[p.posX+1][p.posY-1].isEmpty())
            provaveisCel.add(tabuleiro[p.posX+1][p.posY-1]);
        }
            
        
       
        
        return provaveisCel;
            
    }
    
     // este método analiza as peças que uma peça p pode comer
    public ArrayList proximoAtaque(Peca p){ 
            ArrayList<Peca> proximoAtaque = new ArrayList<>(); 
            
            if (p.posY < 6) {                                                                   //evitando arrayIndexOutOfBounds
                if (p.posX < 6)                                                                 //evitando arrayIndexOutOfBounds
                if (!tabuleiro[p.posX+1][p.posY+1].getPeca().jogador.equals(p.jogador)) {       // se nao é a sua peça,                         p
                    if (tabuleiro[p.posX+2][p.posY+2].isEmpty())                                // e a diagonal está livre,                        target
                        proximoAtaque.add(tabuleiro[p.posX+1][p.posY+1].getPeca());             //então vc pode comer.
                        
                }
                if (p.posX > 1) 
                if (!tabuleiro[p.posX-1][p.posY+1].getPeca().jogador.equals(p.jogador)) {       // se nao é a sua peça,                             target
                    if (tabuleiro[p.posX-2][p.posY+2].isEmpty())                                // e a diagonal está livre,                     p
                        proximoAtaque.add(tabuleiro[p.posX-1][p.posY+1].getPeca());             //então vc pode comer.
                       
                }
            
            }if (p.posY > 1) {
                if (p.posX < 6)                                                                 //evitando arrayIndexOutOfBounds
                if (!tabuleiro[p.posX+1][p.posY-1].getPeca().jogador.equals(p.jogador)) {       // se nao é a sua peça,                                 p
                    if (tabuleiro[p.posX+2][p.posY-2].isEmpty())                                // e a diagonal está livre,                     target
                        proximoAtaque.add(tabuleiro[p.posX+1][p.posY-1].getPeca());             //então vc pode comer.
        
                }
                if (p.posX > 1) 
                if (!tabuleiro[p.posX-1][p.posY-1].getPeca().jogador.equals(p.jogador)) {       // se nao é a sua peça,
                    if (tabuleiro[p.posX-2][p.posY-2].isEmpty())                                // e a diagonal está livre,                 target
                        proximoAtaque.add(tabuleiro[p.posX-1][p.posY-1].getPeca());             //então vc pode comer.                             p
                       
                }
                
            }
 
                
           
            
            return proximoAtaque;
    }
    
    //Método responsável por verificar se uma determinada célula na posição(x,y) pode ser uma das posições da lista de provaveis celulas.
    public boolean avaliaPosicao(Peca p, int x, int y){
        ArrayList <Celula> list = proximaPos(p);
        boolean chave = false;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).x == x && list.get(i).y == y)
                chave = true;
        }
        return chave;
    }
   
    public void capturar(Peca p1, Peca p2){
        
    }   
}    