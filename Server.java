/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ваня
 */
public class Server {
    public static final int PORT=12345;
    public static final int MAX_CLIENTS=2;
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket=null;
        List<ClientHandler> clients=new ArrayList<>();
        try{
            serverSocket=new ServerSocket(PORT);
            System.out.println("Servera e startiran i chaka igrachi...");
            while(true){
                if(clients.size()<MAX_CLIENTS){
                    Socket clientSocket=serverSocket.accept();
                    ClientHandler clientHandler=new ClientHandler(clientSocket, clients);
                    clients.add(clientHandler);
                    Thread thread=new Thread(clientHandler);
                    thread.start();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                if(serverSocket!=null){
                    serverSocket.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
