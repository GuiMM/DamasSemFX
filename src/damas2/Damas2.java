/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damas2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author MQGuilherme
 */
public class Damas2 {

    /**
     * @param args the command line arguments
     */
    static Tabuleiro tabuleiro;
    static ArrayList<String> testes = new ArrayList<>();
    
    public static void main(String[] args) {
        tabuleiro = new Tabuleiro(8);
        tabuleiro.printTabuleiro();
        
        
        while(!tabuleiro.fimDeJogo().contains("Fim de Jogo"))
        {
            rodadaHumano();
            
            System.out.println();
            System.out.println();
            tabuleiro.printTabuleiro();
                       
            
            rodadaMaquina();
            System.out.println();
            System.out.println();
            tabuleiro.printTabuleiro();
        }
        System.out.println(tabuleiro.fimDeJogo());
    }

    private static void rodadaMaquina() {
        minimax();
        //minimax c/ poda alfa beta
        //alfaBetaStart();
        
    }
    
    
    private static void minimax(){
        int v;
        v = Integer.MIN_VALUE;
        ArrayList<Tabuleiro> prox = tabuleiro.possiveisProximasJogadas();
        Tabuleiro prox_jogada=null;
        //v = valorMax(tabuleiro, 3);
        for(int i = 0; i < prox.size(); i++){
            int aux = valorMax(prox.get(i).copiaTabuleiro(),3);
            if (v<aux) {
                prox_jogada=prox.get(i);
                v=aux;
            }
        }
        tabuleiro=prox_jogada;
    }
    
    
    private static int valorMax(Tabuleiro t, int depth){
        ArrayList <Tabuleiro> possibilidades = new ArrayList<>();
        
        if(t.getPretas().isEmpty()||t.getBrancas().isEmpty() || depth == 0)
            return t.funcaoDeAvaliacao();
        int v;
        v = Integer.MIN_VALUE;
        for (int i = 0; i < t.getPretas().size(); i++) {
            Peca peca = t.getPretas().get(i);
            ArrayList<Jogada> possibilidades_da_peca = t.verificaJogadas(peca);
            for (int j = 0; j < possibilidades_da_peca.size(); j++) {
                Tabuleiro copia2 = t.copiaTabuleiro();               
                Peca aux = copia2.getPretas().get(i);
                //aux.setPosX(peca.getPosX());
                //aux.setPosY(peca.getPosY());
                possibilidades_da_peca.get(j).realizaJogada(copia2, aux);
                
                possibilidades.add(copia2);
                
            }
        }
        
         for (int i = 0; i < possibilidades.size(); i++) {           
             v = Integer.max(v, valorMin(possibilidades.get(i),depth-1));
         }
         return v;
    }
    
    private static int valorMin(Tabuleiro t, int depth){
        ArrayList <Tabuleiro> possibilidades = new ArrayList<>();
        HashMap<Integer,Tabuleiro> teste = new HashMap<>();
        if(t.getPretas().isEmpty()||t.getBrancas().isEmpty() || depth == 0)
            return t.funcaoDeAvaliacao();
        int v;
        v = Integer.MAX_VALUE;
        for (int i = 0; i < t.getPretas().size(); i++) {
            Peca peca = t.getPretas().get(i);
            ArrayList<Jogada> possibilidades_da_peca = t.verificaJogadas(peca);
            for (int j = 0; j < possibilidades_da_peca.size(); j++) {
                Tabuleiro copia2 = t.copiaTabuleiro();               
                Peca aux = copia2.getPretas().get(i);
                //aux.setPosX(peca.getPosX());
                //aux.setPosY(peca.getPosY());
                possibilidades_da_peca.get(j).realizaJogada(copia2, aux);
                //teste.put(copia2.funcaoDeAvaliacao(), copia2);
                possibilidades.add(copia2);
            }
        }
        
         for (int i = 0; i < possibilidades.size(); i++) {
             v = Integer.min(v, valorMax(possibilidades.get(i),depth-1));
         }
         return v;
    }
       
    
    private static void alfaBetaStart() {
        int v;
        ArrayList<Tabuleiro> prox = tabuleiro.possiveisProximasJogadas();
        v = alfaBeta(tabuleiro, 15, Integer.MAX_VALUE, Integer.MIN_VALUE, true);
        for (int i = 0; i < prox.size(); i++) {
            if (prox.get(i).getVal() == v) {
                tabuleiro = prox.get(i).copiaTabuleiro();
                break;
            }
        }
    }
    
    public static int alfaBeta(Tabuleiro t, int depth, int alfa, int beta, boolean maximizingPlayer) {
        ArrayList<Tabuleiro> possibilidades = new ArrayList<>();
        int v;
        if (t.getPretas().isEmpty() || t.getBrancas().isEmpty() || depth == 0) {
            return t.funcaoDeAvaliacao();
        }
        if (maximizingPlayer) {

            v = Integer.MIN_VALUE;
            for (int i = 0; i < t.getPretas().size(); i++) {
                Peca peca = t.getPretas().get(i);
                ArrayList<Jogada> possibilidades_da_peca = t.verificaJogadas(peca);
                for (int j = 0; j < possibilidades_da_peca.size(); j++) {
                    Tabuleiro copia2 = t.copiaTabuleiro();
                    Peca aux = copia2.getPretas().get(i);
                    aux.setPosX(peca.getPosX());
                    aux.setPosY(peca.getPosY());
                    possibilidades_da_peca.get(j).realizaJogada(copia2, aux);
                    possibilidades.add(copia2);
                }
            }
            for (int i = 0; i < possibilidades.size(); i++) {
                v = Integer.max(v, alfaBeta(possibilidades.get(i), depth - 1, alfa , beta, false));
                alfa = Integer.max(alfa, v);
                if (beta <= alfa) {
                    break; //cut-off
                }
            }

            return v;
        } else {
            v = Integer.MAX_VALUE;
            for (int i = 0; i < t.getPretas().size(); i++) {
                Peca peca = t.getPretas().get(i);
                ArrayList<Jogada> possibilidades_da_peca = t.verificaJogadas(peca);
                for (int j = 0; j < possibilidades_da_peca.size(); j++) {
                    Tabuleiro copia2 = t.copiaTabuleiro();
                    Peca aux = copia2.getPretas().get(i);
                    aux.setPosX(peca.getPosX());
                    aux.setPosY(peca.getPosY());
                    possibilidades_da_peca.get(j).realizaJogada(copia2, aux);

                    possibilidades.add(copia2);
                }
            }
            for (int i = 0; i < possibilidades.size(); i++) {
                v = Integer.min(v, alfaBeta(possibilidades.get(i), depth - 1, alfa, beta, true));
                beta = Integer.min(beta, v);
                if (beta <= alfa) {
                    break; //cutoff
                }
            }

            return v;
        }

    }
    
    private static void rodadaHumano() {
        ArrayList<Peca> pecas = tabuleiro.getBrancas();
        ArrayList<Jogada> possiveisJogadasIniciais = new ArrayList<>();
        for (int i = 0; i < pecas.size(); i++) {
            Peca p = pecas.get(i);
            ArrayList<Jogada> temp = tabuleiro.verificaJogadas(p);
            if(temp!=null && temp.contains(new Jogada("captura",null))){
                for (int j = 0; j < temp.size(); j++) {
                    Jogada jg = temp.get(j);
                    jg.setPecaJogada(p);
                    possiveisJogadasIniciais.add(jg);
                }
            }
        }
        
        Scanner ler = new Scanner(System.in);
        
        if(possiveisJogadasIniciais.isEmpty()){
            
            Peca a_Jogar = null;
            while(a_Jogar==null){
                System.out.println("Digite o id da peça que você quer mover:");
                String id = ler.nextLine();
                a_Jogar = tabuleiro.getPecaBranca(id);
                if(a_Jogar==null)
                    System.out.println("Esta peça não existe ou não é sua peça!!");
            }
            List<Jogada> jogadas = tabuleiro.verificaJogadas(a_Jogar);
            do{
                for (int i = 0; i < jogadas.size(); i++) {
                    System.out.println(i+1+" - "+jogadas.get(i).getTipo()+"  "+
                                                jogadas.get(i).getDestino().getX()+","+
                                                jogadas.get(i).getDestino().getY());
                }

                System.out.println("Digite o nº da jogada a ser feita:");
                int nJogada = ler.nextInt();

                jogadas.get(nJogada-1).realizaJogada(tabuleiro, a_Jogar);
            }while(jogadas.contains("captura"));
            
        }else{
            
            for (int i = 0; i < possiveisJogadasIniciais.size(); i++) {
                System.out.println((i + 1) + " - " + possiveisJogadasIniciais.get(i).getPecaJogada().getId()
                        + " "
                        + possiveisJogadasIniciais.get(i).getTipo() + "  "
                        + possiveisJogadasIniciais.get(i).getDestino().getX() + ","
                        + possiveisJogadasIniciais.get(i).getDestino().getY());
            }

            System.out.println("Digite o nº da jogada a ser feita:");
            int nJogada = ler.nextInt();
            
            Jogada escolhida = possiveisJogadasIniciais.get(nJogada - 1);
            Peca a_Jogar = escolhida.getPecaJogada();
            escolhida.setPecaJogada(null);
            escolhida.realizaJogada(tabuleiro, a_Jogar);
        }
    }   
}
