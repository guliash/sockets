package com.guliash.sockets.server;

import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Main {

    private enum Command {
        HELLO("hello", "hello"),
        PRIME("prime", "");

        private final String command;
        private final String response;

        Command(String command, String response) {
            this.command = command;
            this.response = response;
        }

        public String getCommand() {
            return command;
        }

        public String getResponse() {
            return response;
        }
    }

    public static void main(String[] args) {

        try (ServerSocket socket = new ServerSocket(10000)) {
            while (true) {
                new RequestHandlerThread(socket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class RequestHandlerThread extends Thread {

        private final Socket socket;

        private RequestHandlerThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                String command;
                while ((command = in.readLine()) != null) {
                    if (command.equalsIgnoreCase(Command.HELLO.getCommand())) {
                        out.println(Command.HELLO.getResponse());
                    } else if(command.equalsIgnoreCase(Command.PRIME.getCommand())) {
                        out.println(BigInteger.probablePrime(32, new Random()));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
