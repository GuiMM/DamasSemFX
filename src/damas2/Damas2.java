/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damas2;

import java.util.ArrayList;
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
            tabuleiro.printTabuleiro();
            //rodadaMaquina();
            System.out.println();
            tabuleiro.printTabuleiro();
        }
    }

    private static void rodadaMaquina() {
        alphaBetaSearch();
    }

    private static void alphaBetaSearch(){
        Tabuleiro copia = new Tabuleiro();
        copia = tabuleiro.copiaTabuleiro();
        if(!copia.toString().equals(tabuleiro.toString()))
            System.out.println("");
        Tabuleiro retorno;
        retorno = maxValue(copia, -100000, +100000,0);
        tabuleiro=retorno;
    }
    
    private static Tabuleiro maxValue(Tabuleiro copia, int alfa, int beta,int depth) {
        ArrayList <Tabuleiro> possibilidades = new ArrayList<>();
        Tabuleiro retorno=null;
        
        int v = Integer.MIN_VALUE;
        if (copia.getPretas().isEmpty()||copia.getBrancas().isEmpty() || depth==10) {
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
//                if(!testes.isEmpty()){
//                    if(!testes.contains(copia2.toString()))
//                        testes.add(copia2.toString());
//                }else
//                    testes.add(copia2.toString());
////                copia2.printTabuleiro();
//                for (int k = 0; k < testes.size(); k++) {
//                        System.out.println(testes.get(k));
//                }
            }
        }
        
        for (int i = 0; i < possibilidades.size(); i++) {
            Tabuleiro no_retornado = minValue(possibilidades.get(i),alfa,beta,depth+1);
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
    
    private static Tabuleiro minValue(Tabuleiro copia, int alfa, int beta, int depth) {
        ArrayList <Tabuleiro> possibilidades = new ArrayList<>();
        Tabuleiro retorno=null;
        int v = Integer.MAX_VALUE;
        if (copia.getPretas().isEmpty()||copia.getBrancas().isEmpty() || depth==10) {
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
//                if(!testes.isEmpty()){
//                    if(!testes.contains(copia2.toString()))
//                        testes.add(copia2.toString());
//                }else
//                    testes.add(copia2.toString());
//                copia2.printTabuleiro();              
            }
        }
        
        for (int i = 0; i < possibilidades.size(); i++) {
            Tabuleiro no_retornado = maxValue(possibilidades.get(i),alfa,beta,depth+1);
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
    //            int x = ler.nextInt();
    //            int y = ler.nextInt();

    //            Celula daPeca_a_Jogar = tabuleiro.getTabuleiro()[x][y];
                a_Jogar = tabuleiro.getPecaBranca(id);//daPeca_a_Jogar.getPeca();
                if(a_Jogar==null)
                    System.out.println("Esta peça não existe!!");
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
