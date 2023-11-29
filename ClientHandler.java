/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Ваня
 */
public class ClientHandler implements Runnable{
    private Socket clientSocket;
    private List<ClientHandler> clients;
    private int numberToGuess;
    private boolean isGuessing;
    public ClientHandler(Socket socket, List<ClientHandler> clients){
        this.clientSocket=socket;
        this.clients=clients;
        this.numberToGuess=new Random().nextInt(100)+1;
        this.isGuessing=true;
    }
    @Override
    public void run(){
        try{
            InputStream inputStream=clientSocket.getInputStream();
            OutputStream outputStream=clientSocket.getOutputStream();
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
            while(isGuessing){
                String clientGuess=reader.readLine();
                if(clientGuess==null){
                    break;
                }
                int guess=Integer.parseInt(clientGuess);
                String hint=processGuess(guess);
                outputStream.write(hint.getBytes());
                outputStream.write('\n');
            }
            announceWinner();
            startNewRound();
        }catch(IOException e){
            e.printStackTrace();
        }finally{
            try{
                clientSocket.close();
                clients.remove(this);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    private String processGuess(int clientGuess){
        if(clientGuess<numberToGuess){
            return "The number is greater.";
        }
        else if(clientGuess>numberToGuess){
            return "The number is smaller.";
        }
        else{
            isGuessing=false;
            return "You win!";
        }
    }
    private void announceWinner(){
        System.out.println("Player "+clientSocket.getInetAddress().getHostAddress()+" wins!");
    }
    private void startNewRound(){
        numberToGuess=new Random().nextInt(100)+1;
        isGuessing=true;
        System.out.println("New round started. Guess the new number!");
    }
}
