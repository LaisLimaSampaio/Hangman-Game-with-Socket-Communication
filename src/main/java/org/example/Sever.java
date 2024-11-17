package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Sever {
    public static void main(String[] args) throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(54321) ) { // Porta do servidor
            System.out.println("SERVIDOR: Porta 54321 aberta com sucesso ");
            System.out.println("SERVIDOR: Esperando connexao com cliente...");

            Socket socket = serverSocket.accept();
            System.out.println("SERVIDOR: Cliente " + socket.getInetAddress().getHostAddress() + " conectado");

            System.out.println("SERVIDOR: Estabelecendo fluxo de entrada");
            DataInputStream entrada  = new DataInputStream(socket.getInputStream());
            System.out.println("SERVIDOR: Estabelecendo fluxo de saida");
            DataOutputStream saida = new DataOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);
            int tentativasErradas;

            //*****************************************

            Jogo jogo = new Jogo(saida);
            tentativasErradas = jogo.atualizarQuadro();
            while (tentativasErradas < 6) {
                if (tentativasErradas == -2) {
                    entrada.close();
                    saida.close();
                    socket.close();
                    serverSocket.close();
                }
                String letraChutada = entrada.readUTF();
                System.out.println("SERVIDOR: Cliente chutou a letra: " + letraChutada);
                tentativasErradas= jogo.chutarLetra(letraChutada);
            }

            //*****************************************

            System.out.println("SERVIDOR: Fechando fluxos de entrada|saida e socket");
            entrada.close();
            saida.close();
            socket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
