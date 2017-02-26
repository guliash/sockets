package com.guliash.sockets.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) {
        try(Socket socket = new Socket("127.0.0.1", 10000);
            BufferedReader userIn = new BufferedReader(new InputStreamReader(System.in));
            BufferedReader serverIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), true)) {
            String command;
            while((command = userIn.readLine()) != null) {
                serverOut.println(command);
                System.out.println("Server: " + serverIn.readLine());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
