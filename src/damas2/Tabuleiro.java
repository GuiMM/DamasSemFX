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
                Peca pr = new Peca("Computador");
                pretas.add(pr);
            }else{
                Peca br = new Peca("  Humano  ");
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
    
    
    
    /*Método responsável por gerar uma lista de possíveis celulas para onde ele 
    possa se mover.(ele sempre se moverá pra frente e se ele for dama? e se ele 
    for do time da parte de baixo do tabuleiro?)*/
    public ArrayList proximaPos(Peca p){
        ArrayList<Celula> provaveisCel = new ArrayList<>(); 
        if(p.getPosY() < 7){      //evitando arrayIndexOutOfBounds
            if(tabuleiro[p.getPosX()+1][p.getPosY()+1].isEmpty())
            provaveisCel.add(tabuleiro[p.getPosX()+1][p.getPosY()+1]);
        }
        if (p.getPosY() > 0) {    //evitando arrayIndexOutOfBounds
            if(tabuleiro[p.getPosX()+1][p.getPosY()-1].isEmpty())
            provaveisCel.add(tabuleiro[p.getPosX()+1][p.getPosY()-1]);
        }
            
        
       
        
        return provaveisCel;
            
    }
    
    
    // este método identifica as peças que uma peça p pode comer
    public ArrayList proximoAtaque(Peca p){ 
        ArrayList<Peca> proximoAtaque = new ArrayList<>();

        if (p.getPosY() < 6) {   //evitando arrayIndexOutOfBounds
            if (p.getPosX() < 6) //evitando arrayIndexOutOfBounds
            {
                if (!tabuleiro[p.getPosX() + 1][p.getPosY() + 1].isEmpty()
                        && !tabuleiro[p.getPosX() + 1][p.getPosY() + 1].getPeca().getJogador().equals(p.getJogador())) { // se existe uma peca na celula e se nao é a sua peça,                        
                    if (tabuleiro[p.getPosX() + 2][p.getPosY() + 2].isEmpty()) // e a diagonal está livre, p então vc pode comer.  
                    {
                        proximoAtaque.add(tabuleiro[p.getPosX() + 1][p.getPosY() + 1].getPeca());
                    }
                }
            }
            if (p.getPosX() > 1) {
                if (!tabuleiro[p.getPosX() - 1][p.getPosY() + 1].isEmpty()
                        && !tabuleiro[p.getPosX() - 1][p.getPosY() + 1].getPeca().getJogador().equals(p.getJogador())) {  // se existe uma peca na celula e se nao é a sua peça,                             
                    if (tabuleiro[p.getPosX() - 2][p.getPosY() + 2].isEmpty()) // e a diagonal está livre, target então vc pode comer.
                    {
                        proximoAtaque.add(tabuleiro[p.getPosX() - 1][p.getPosY() + 1].getPeca());
                    }
                }
            }

        }
        if (p.getPosY() > 1) {
            if (p.getPosX() < 6) //evitando arrayIndexOutOfBounds
            {
                if (!tabuleiro[p.getPosX() + 1][p.getPosY() - 1].isEmpty()
                        && !tabuleiro[p.getPosX() + 1][p.getPosY() - 1].getPeca().getJogador().equals(p.getJogador())) {  // se existe uma peca na celula e se nao é a sua peça,                                 
                    if (tabuleiro[p.getPosX() + 2][p.getPosY() - 2].isEmpty()) // e a diagonal está livre, p então vc pode comer.
                    {
                        proximoAtaque.add(tabuleiro[p.getPosX() + 1][p.getPosY() - 1].getPeca());
                    }
                }
            }
            if (p.getPosX() > 1) {
                if (!tabuleiro[p.getPosX() - 1][p.getPosY() - 1].isEmpty()
                        && !tabuleiro[p.getPosX() - 1][p.getPosY() - 1].getPeca().getJogador().equals(p.getJogador())) {   // se existe uma peca na celula e se nao é a sua peça,
                    if (tabuleiro[p.getPosX() - 2][p.getPosY() - 2].isEmpty()) // e a diagonal está livre, target então vc pode comer.
                    {
                        proximoAtaque.add(tabuleiro[p.getPosX() - 1][p.getPosY() - 1].getPeca());
                    }
                }
            }

        }

        return proximoAtaque;
    }
    
    /*Método responsável por verificar se uma determinada célula na posição(x,y) 
    pode ser uma das posições da lista de provaveis celulas.*/
    public boolean avaliaPosicao(Peca p, int x, int y){
        ArrayList <Celula> list = proximaPos(p);
        boolean chave = false;
        for(int i = 0; i < list.size(); i++){
            if(list.get(i).getX() == x && list.get(i).getY() == y)
                chave = true;
        }
        return chave;
    }
    
    /*este método executa a captura de uma peça p2, por uma peça p1
    (não estou preocupado em validar arrayIndexOutOfBounds, 
    ou se é uma peça comível, pois o método proximoAtaque ja faz isso)*/
    public void capturar(Peca p1, Peca p2){
        boolean eh_peca_branca;
        if (p1.getJogador().equals("  Humano  ")) { /*verifica qual o time do p1, 
                                                    vamos fazer um método que pode 
                                                    ser usado pelo humano ou pelo PC*/
            eh_peca_branca=true;
        }else
            eh_peca_branca=false;
        
        if (p1.getPosX()==p2.getPosX()+1) {
            if (p1.getPosY()==p2.getPosY()+1) {
                if (eh_peca_branca)  //verifica qual peca deve morrer
                    pretas.remove(p2);                   
                else
                    brancas.remove(p2);
                tabuleiro[p2.getPosX()][p2.getPosY()] = new Celula(p2.getPosX(),p2.getPosY());  //limpa o tabuleiro
                tabuleiro[p1.getPosX()][p1.getPosY()] = new Celula(p1.getPosX(),p1.getPosY());  //limpa o tabuleiro
                p1.move(p1.getPosX()-2, p1.getPosY()-2);          
                tabuleiro[p1.getPosX()][p1.getPosY()].setPeca(p1);
            }else{  //p1.posY==p2.posY-1    
                if (eh_peca_branca)  //verifica qual peca deve morrer
                    pretas.remove(p2);                   
                else
                    brancas.remove(p2);
                tabuleiro[p2.getPosX()][p2.getPosY()] = new Celula(p2.getPosX(),p2.getPosY());  //limpa o tabuleiro
                tabuleiro[p1.getPosX()][p1.getPosY()] = new Celula(p1.getPosX(),p1.getPosY());  //limpa o tabuleiro           
                p1.move(p1.getPosX()-2, p1.getPosY()+2);
                tabuleiro[p1.getPosX()][p1.getPosY()].setPeca(p1);
            }
            
        }else{  //p1.posX==p2.posX-1
            if (p1.getPosY()==p2.getPosY()+1) {
                if (eh_peca_branca)  //verifica qual peca deve morrer
                    pretas.remove(p2);                   
                else
                    brancas.remove(p2);
                tabuleiro[p2.getPosX()][p2.getPosY()] = new Celula(p2.getPosX(),p2.getPosY());  //limpa o tabuleiro
                tabuleiro[p1.getPosX()][p1.getPosY()] = new Celula(p1.getPosX(),p1.getPosY());  //limpa o tabuleiro
                p1.move(p1.getPosX()+2, p1.getPosY()-2);
                tabuleiro[p1.getPosX()][p1.getPosY()].setPeca(p1);
            }else{   //p1.posY==p2.posY-1    
                if (eh_peca_branca)  //verifica qual peca deve morrer
                    pretas.remove(p2);                   
                else
                    brancas.remove(p2);
                tabuleiro[p2.getPosX()][p2.getPosY()] = new Celula(p2.getPosX(),p2.getPosY());  //limpa o tabuleiro
                tabuleiro[p1.getPosX()][p1.getPosY()] = new Celula(p1.getPosX(),p1.getPosY());  //limpa o tabuleiro
                p1.move(p1.getPosX()+2, p1.getPosY()+2);
                tabuleiro[p1.getPosX()][p1.getPosY()].setPeca(p1);
            }
        
        }
    }   

    
}    