package me.infuzion.dota2.integration;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.infuzion.dota2.integration.nodes.Node;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class Server implements Runnable {
    private Node node;
    private ServerSocket serverSocket;

    public Server(ServerSocket socket){
        serverSocket = socket;
    }

    @Override
    public void run() {
        top:
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                writer.println("HTTP/1.0 200 OK\r");
                writer.println("Content-Type: text/html; charset=UTF-8\r\n");
                writer.println();
                if(node != null){
                    writer.println("<img src=\"http://cdn.dota2.com/apps/dota2/images/heroes/" +
                            node.hero.name.replace("npc_dota_hero_", "") + "_lg.png\"/>");
                    writer.println("<b>Last Hits: </b> " + node.player.last_hits);
                }
                writer.flush();
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                int contentLength = -1;
                int counter = 0;
                while(true) {
                    final String line = reader.readLine();
                    System.out.println(line);

                    final String requestStr = "POST /integration.html";
                    final String contentLengthStr = "Content-Length: ";
                    if (line.startsWith(contentLengthStr)) {
                        contentLength = Integer.parseInt(line.substring(contentLengthStr.length()));
                    }
                    if(!line.startsWith(requestStr) && counter == 0){
                        socket.close();
                        continue top;
                    }

                    if (line.length() == 0) {
                        break;
                    }
                    counter++;
                }
                final char[] content = new char[contentLength];
                if(reader.read(content) == -1){
                    return;
                }
                String contentString = new String(content);

                Type type = new TypeToken<Map<String, Map<String, Object>>>(){}.getType();
                Map<String, Map<String, Object>> map = new Gson().fromJson(contentString, type);

                node = new Node(map);
                System.out.println(node.player.steamid);
                System.out.println("Lasthits: " + node.player.last_hits);
                System.out.println("Denies: " + node.player.denies);

                socket.close();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
