/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damas2;

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
    public static void main(String[] args) {
        tabuleiro = new Tabuleiro(8);
        tabuleiro.printTabuleiro();
        
        while(!tabuleiro.fimDeJogo().contains("Fim de Jogo"))
        {
            rodadaHumano();
            //rodadaMaquina();
            tabuleiro.printTabuleiro();
        }
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
