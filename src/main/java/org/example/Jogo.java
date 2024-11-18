package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jogo {
    private String chuteLetra = "";
    private String palavraSorteada;
    private Random random = new Random();
    private String palavraAtual;
    private int tentativas = 0;
    private List<DataInputStream> in = new ArrayList<>();
    private List<DataOutputStream> out = new ArrayList<>();


    public Jogo(List<DataInputStream> entrada, List<DataOutputStream> saida) {
        this.in.addAll(entrada);
        this.out.addAll(saida);

    }

    public void instrucoes() throws IOException {
        for (int i = 0; i < 2; i++) {
            out.get(i).writeUTF("------------------------------------------------");
            out.get(i).writeUTF("|             I N S T R U Ç Õ E S              |");
            out.get(i).writeUTF("|                                              |");
            out.get(i).writeUTF("|    1 - O jogador 1 escolhe a palavra         |");
            out.get(i).writeUTF("|                                              |");
            out.get(i).writeUTF("|    2 - O jogador 2 tenta advinhar a palavra  |");
            out.get(i).writeUTF("|                                              |");
            out.get(i).writeUTF("|              VOCÊ É O JOGADOR "+(i+1)+"             |");
            out.get(i).writeUTF("------------------------------------------------");
        }
    }

    public void escolherPalavra() throws IOException {
        out.get(1).writeUTF("O jogador 1 está escolhendo uma palavra ");
        out.get(0).writeUTF("Você é o jogador 1 - Escolha uma palavra para o jogador 2 advinhar: ");
        palavraSorteada = in.get(0).readUTF();
        out.get(0).writeUTF("A palavra escolhida por voce é esta: " + palavraSorteada);


        palavraAtual = "_".repeat(palavraSorteada.length());

    }

    public void atualizarQuadro(){

        try{
            instrucoes();
            escolherPalavra();
            while (tentativas <= 6){


                StringBuilder sb = new StringBuilder(palavraAtual);

                for(int i =0; i < palavraSorteada.length(); i++){

                    if( chuteLetra.equals(String.valueOf(palavraSorteada.charAt(i)))) {

                        sb.setCharAt(i, chuteLetra.charAt(0));

                    }
                }
                palavraAtual = sb.toString();

                for (int x = 0; x < 2; x++) {
                    out.get(x).writeUTF("\n--------------------   FORCA  -------------------------\n");
                    out.get(x).writeUTF("\t\t\t\t\t\tTentavivas Erradas | " + tentativas);
                    out.get(x).writeUTF("\t\t\t\t\t\tTentavivas Máxima  | 6");
                    exibindoBoneco(x);
                    out.get(x).writeUTF("\n\n\t\t\t" + palavraAtual);
                    out.get(x).writeUTF("\n--------------------------------------------------------\n");
                }


                if (tentativas < 6 && !(palavraSorteada.equals(palavraAtual))) {
                    out.get(0).writeUTF("O jogador 1 irá chutar uma letra...\n");
                    out.get(1).writeUTF("Chute uma letra aqui: ");
                    chuteLetra = in.get(1).readUTF();
                    if (!(palavraSorteada.contains(chuteLetra))) {
                        tentativas++;
                        out.get(0).writeUTF("Comemore, ele chutou uma letra errada!");
                    }

                }else if (tentativas == 6){
                    out.get(1).writeUTF("Suas tentavivas acabaram\nVOCÊ PERDEU O JOGO!\n A resposta era: "+palavraSorteada);
                    out.get(0).writeUTF("VOCÊ GANHOU O JOGO \nSua palavra foi muito difícil para ele, não é? :)");
                    tentativas++;

                }else if(palavraSorteada.equals(palavraAtual)){
                    out.get(1).writeUTF("VOCÊ GANHOU O JOGO \nParabéns! :)");
                    out.get(0).writeUTF("VOCÊ PERDEU O JOGO!\n O jogador 2 conseguiu advinhar a palavra :C");
                    tentativas = 7;
                }

            }


        }catch (IOException e){
            System.out.println("Erro ao enviar mensagem para o cliente: " + e.getMessage());
        }

    }

   
    public void exibindoBoneco(int x){
        try{

            out.get(x).writeUTF("  _______     ");
            out.get(x).writeUTF(" |       |    ");

            // Cabeça do boneco
            if (tentativas >= 1) out.get(x).writeUTF(" |       O    ");
            else out.get(x).writeUTF(" |            ");

            // Corpo e braços
            if (tentativas >= 4) out.get(x).writeUTF(" |      /|\\  ");
            else if (tentativas == 3) out.get(x).writeUTF(" |      /|    ");
            else if (tentativas == 2) out.get(x).writeUTF(" |       |    ");
            else out.get(x).writeUTF(" |            ");

            // Pernas
            if (tentativas >= 6) out.get(x).writeUTF(" |      / \\  ");
            else if (tentativas == 5) out.get(x).writeUTF(" |      /     ");
            else out.get(x).writeUTF(" |            ");

            // Base
            out.get(x).writeUTF("_|_           ");
            out.get(x).writeUTF("|   |______   ");
            out.get(x).writeUTF("|          |  ");
            out.get(x).writeUTF("|__________|  ");



        } catch (IOException e) {
            System.out.println("Erro ao enviar mensagem para o cliente: " + e.getMessage());
        }
    }


}
