package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Sever {
    public static void main(String[] args) throws IOException {

        try (ServerSocket serverSocket = new ServerSocket(54321) ) { // Porta do servidor
            System.out.println("SERVIDOR: Porta 54321 aberta com sucesso ");
            System.out.println("SERVIDOR: Esperando connexao com cliente...");

            List<Socket> sockets = new ArrayList<>();
            List<DataOutputStream> saidas = new ArrayList<>();
            List<DataInputStream> entradas = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                Socket socket = serverSocket.accept();
                System.out.println("SERVIDOR: Cliente " + socket.getInetAddress().getHostAddress() + " conectado");

                System.out.println("SERVIDOR: Estabelecendo fluxo de entrada");
                DataInputStream entrada  = new DataInputStream(socket.getInputStream());
                System.out.println("SERVIDOR: Estabelecendo fluxo de saida");
                DataOutputStream saida = new DataOutputStream(socket.getOutputStream());


                sockets.add(socket);
                entradas.add(entrada);
                saidas.add(saida);
            }


            //*****************************************


            Jogo jogo = new Jogo(entradas,saidas);
            jogo.atualizarQuadro();

            //*****************************************

            System.out.println("SERVIDOR: Fechando fluxos de entrada|saida e socket 1");
            entradas.get(0).close();
            saidas.get(0).close();
            sockets.get(0).close();

            System.out.println("SERVIDOR: Fechando fluxos de entrada|saida e socket 2");
            entradas.get(0).close();
            saidas.get(0).close();
            sockets.get(0).close();

            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
