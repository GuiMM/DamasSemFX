/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damas2;

/**
 *
 * @author MQGuilherme
 */
public class Damas2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Tabuleiro tabuleiro = montarTabuleiro();
        //tabuleiro.printTabuleiro();
        String ganhador =null;
        boolean fim_de_jogo=false;
        
//        while(!fim_de_jogo)
//        {
//            rodadaHumano();
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
    
}
