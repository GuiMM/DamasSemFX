/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damas2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public static void main(String[] args) {
        tabuleiro = new Tabuleiro(8);
        tabuleiro.printTabuleiro();
        
        while(!tabuleiro.fimDeJogo().contains("Fim de Jogo"))
        {
            rodadaHumano();
            rodadaMaquina();
            System.out.println();
            tabuleiro.printTabuleiro();
        }
    }

    private static void rodadaMaquina() {
        alphaBetaSearch(tabuleiro);
    }

    private static void alphaBetaSearch(Tabuleiro tabuleiro){
        Tabuleiro copia = new Tabuleiro();
        copia = tabuleiro.copiaTabuleiro();
        Tabuleiro retorno;
        retorno = maxValue(copia, -100000, +100000);
        tabuleiro=retorno.copiaTabuleiro();
    }
    
    private static Tabuleiro maxValue(Tabuleiro copia, int alfa, int beta) {
        ArrayList <Tabuleiro> possibilidades = new ArrayList<>();
        Tabuleiro retorno=null;
        int v = Integer.MIN_VALUE;
        if (copia.getPretas().isEmpty()||copia.getBrancas().isEmpty()) {
            return copia;
        }
        
       // vendo todas as possivei jogadas para cada peca preta no tabuleiro
        for (int i = 0; i < copia.getPretas().size(); i++) {
            Peca peca = copia.getPretas().get(i);
            ArrayList<Jogada> possibilidades_da_peca = copia.verificaJogadas(peca);
            for (int j = 0; j < possibilidades_da_peca.size(); j++) {
                Tabuleiro copia2 = copia.copiaTabuleiro();
                possibilidades_da_peca.get(j).realizaJogada(copia2, peca);
                possibilidades.add(copia2);
                copia2.printTabuleiro();
                System.out.println();
                System.out.println();
            }
        }
        
        for (int i = 0; i < possibilidades.size(); i++) {
            Tabuleiro no_retornado = minValue(possibilidades.get(i),alfa,beta);
            if (v<no_retornado.funcaoDeAvaliacao()) {
                retorno =no_retornado;
                v=no_retornado.funcaoDeAvaliacao();
            }
            if (v>=beta) 
                return retorno;
            
            if (alfa<v) 
                alfa=v;
            
        }
        
         return retorno;       
    }
    
    private static Tabuleiro minValue(Tabuleiro copia, int alfa, int beta) {
        ArrayList <Tabuleiro> possibilidades = new ArrayList<>();
        Tabuleiro retorno=null;
        int v = Integer.MAX_VALUE;
        if (copia.getPretas().isEmpty()||copia.getBrancas().isEmpty()) {
            return copia;
        }
        
       // vendo todas as possivei jogadas para cada peca preta no tabuleiro
        for (int i = 0; i < copia.getBrancas().size(); i++) {
            Peca peca = copia.getBrancas().get(i);
            ArrayList<Jogada> possibilidades_da_peca = copia.verificaJogadas(peca);
            for (int j = 0; j < possibilidades_da_peca.size(); j++) {
                Tabuleiro copia2 = copia.copiaTabuleiro();
                possibilidades_da_peca.get(j).realizaJogada(copia2, peca);
                possibilidades.add(copia2);
                                
                copia2.printTabuleiro();
                System.out.println();
                System.out.println();                
            }
        }
        
        for (int i = 0; i < possibilidades.size(); i++) {
            Tabuleiro no_retornado = maxValue(possibilidades.get(i),alfa,beta);
            if (v>no_retornado.funcaoDeAvaliacao()) {
                retorno =no_retornado;
                v=no_retornado.funcaoDeAvaliacao();
            }
            if (v<=alfa) 
                return retorno;
            
            if (alfa>v) 
                alfa=v;
        }
        return retorno;
    }
    
    private static void rodadaHumano() {
        System.out.println("Digite o xy da peça que vc quer mover e a posição final dela:");
        Scanner ler = new Scanner(System.in);
        int x = ler.nextInt();
        int y = ler.nextInt();
     
        Celula daPeca_a_Jogar = tabuleiro.getTabuleiro()[x][y];
        Peca a_Jogar = daPeca_a_Jogar.getPeca();
        
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
    }

    

    
    
}
