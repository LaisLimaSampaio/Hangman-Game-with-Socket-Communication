package org.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        DataInputStream entrada = null;
        DataOutputStream saida = null;
        try (Socket socket = new Socket(InetAddress.getLocalHost().getHostAddress(), 54321)) {
            System.out.println("CLIENTE: Conectado ao servidor.");

            System.out.println("CLIENTE: Estabelecendo fluxo de saida");
            saida = new DataOutputStream(socket.getOutputStream());

            System.out.println("CLIENTE: Estabelecendo fluxo de entrada");
            entrada = new DataInputStream(socket.getInputStream());

            //*************************************
            Scanner scanner = new Scanner(System.in);

            try {

                while (true) {
                    // Recebe mensagem do servidor
                    String mensagem = entrada.readUTF();
                    System.out.println(mensagem);

                    if (mensagem.contains("Chute uma letra aqui: ") || mensagem.contains("Escolha uma palavra")) {
                        String input = scanner.nextLine();
                        saida.writeUTF(input);
                    }

                }
            } catch (IOException ex) {
                System.out.printf("Conexão com o servidor encerrada: %s %n", ex.getMessage());
            }

        } catch (EOFException e) {
            System.out.println("Conexão com o servidor encerrada.");
        } catch (IOException e) {
            System.out.printf("Conexão com o servidor encerrada: %s %n", e.getMessage());
        }


        //************************************

        System.out.println("CLIENTE: Fechando fluxos de entrada|saida e socket");




    }

}
