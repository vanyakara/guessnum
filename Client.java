/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author Ваня
 */
public class Client {

    public static void main(String[] args) throws IOException {
        try{
            Socket socket=new Socket("localhost",12345);
            InputStream inputStream=socket.getInputStream();
            OutputStream outputStream=socket.getOutputStream();
            BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
            BufferedReader userInput=new BufferedReader(new InputStreamReader(System.in));
            while(true){
                String serverResponse=reader.readLine();
                System.out.println(serverResponse);
                if(serverResponse.equals("You win!")){
                    break;
                }
                if(serverResponse.equals("Your turn to guess: ")){
                    String guess=userInput.readLine();
                    outputStream.write(guess.getBytes());
                    outputStream.write('\n');
                }
            }
            socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
