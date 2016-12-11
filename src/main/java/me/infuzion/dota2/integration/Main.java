package me.infuzion.dota2.integration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        File file = new File("log.txt");
        file.createNewFile();

//        System.setOut(new PrintStream(new FileOutputStream(file)));
        ServerSocket serverSocket = new ServerSocket(90);
        new Thread(new Server(serverSocket)).start();
    }
}
