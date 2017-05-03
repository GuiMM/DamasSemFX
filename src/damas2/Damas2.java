/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damas2;

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
        tabuleiro = montarTabuleiro();
        //tabuleiro.printTabuleiro();
        String ganhador =null;
        boolean fim_de_jogo=false;
        
//        while(!fim_de_jogo)
//        {
            rodadaHumano();
//            rodadaMaquina();
//            ganhador = testeVencedor();
//            
//        }
        System.out.println("Fim de jogo. Vencedor:"+ganhador);
        // TODO code application logic here
    }

    
    private static Tabuleiro montarTabuleiro() {
        Tabuleiro a = new Tabuleiro();
        return a;
    }

    private static void rodadaHumano() {
        System.out.println("Digite o xy da peça que vc quer mover e a posição final dela");
        Scanner ler = new Scanner(System.in);
        int x0 = ler.nextInt();
        int y0 = ler.nextInt();
     
        int x1 = ler.nextInt();
        int y1 = ler.nextInt();   
        
        if (tabuleiro.tabuleiro[x0][y0].) {
            
        }
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
