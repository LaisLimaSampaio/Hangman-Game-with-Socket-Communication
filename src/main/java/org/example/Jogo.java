package org.example;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class Jogo {
    private String chuteLetra = "";
    private String[] palavras = {"bola", "faca", "teste", "maurilio"};
    private String palavraSorteada;
    private Random random = new Random();
    private DataOutputStream out;
    private String palavraAtual;
    private int tentativas = 0;

    public Jogo(DataOutputStream out) {
        palavraSorteada = palavras[random.nextInt(palavras.length)];
        this.out = out;
        palavraAtual = "_".repeat(palavraSorteada.length());
    }

    public int chutarLetra(String letra){
        chuteLetra = letra;
        if (!(palavraSorteada.contains(chuteLetra))) {
            tentativas++;
        }
        atualizarQuadro();
        return tentativas;

    }


    public int atualizarQuadro(){
        try{
            StringBuilder sb = new StringBuilder(palavraAtual);

            for(int i =0; i < palavraSorteada.length(); i++){

                if( chuteLetra.equals(String.valueOf(palavraSorteada.charAt(i)))) {

                    sb.setCharAt(i, chuteLetra.charAt(0));

                }
            }


            palavraAtual = sb.toString();
            out.writeUTF("\n--------------------   FORCA  -------------------------\n");
            out.writeUTF("\t\t\t\t\t\tTentavivas Erradas | " + tentativas);
            out.writeUTF("\t\t\t\t\t\tTentavivas Máxima  | 6");
            exibindoBoneco();
            out.writeUTF("\n\n\t\t\t" + palavraAtual);
            out.writeUTF("\n--------------------------------------------------------\n");

            if (tentativas < 6 && !(palavraSorteada.equals(palavraAtual))) {
                out.writeUTF("Chute uma letra aqui: ");
            }else if (tentativas == 6){
                out.writeUTF("Suas tentavivas acabaram\nVOCÊ PERDEU O JOGO!\n A resposta era: "+palavraSorteada);
            }else if(palavraSorteada.equals(palavraAtual)){
                out.writeUTF("Você ganhou o jogo parabéns!");
                tentativas = -2;
                return tentativas;
            }

        }catch (IOException e){
            System.out.println("Erro ao enviar mensagem para o cliente: " + e.getMessage());
        }

        return tentativas;
    }

   
    public void exibindoBoneco(){
        try{
            out.writeUTF("  _______     ");
            out.writeUTF(" |       |    ");

            // Cabeça do boneco
            if (tentativas >= 1) out.writeUTF(" |       O    ");
            else out.writeUTF(" |            ");

            // Corpo e braços
            if (tentativas >= 4) out.writeUTF(" |      /|\\  ");
            else if (tentativas == 3) out.writeUTF(" |      /|    ");
            else if (tentativas == 2) out.writeUTF(" |       |    ");
            else out.writeUTF(" |            ");

            // Pernas
            if (tentativas >= 6) out.writeUTF(" |      / \\  ");
            else if (tentativas == 5) out.writeUTF(" |      /     ");
            else out.writeUTF(" |            ");

            // Base
            out.writeUTF("_|_           ");
            out.writeUTF("|   |______   ");
            out.writeUTF("|          |  ");
            out.writeUTF("|__________|  ");

        } catch (IOException e) {
            System.out.println("Erro ao enviar mensagem para o cliente: " + e.getMessage());
        }
    }


}
