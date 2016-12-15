package me.infuzion.dota2.integration;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.infuzion.dota2.integration.nodes.Gamestate;
import me.infuzion.dota2.integration.nodes.Item;
import me.infuzion.dota2.integration.nodes.Node;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server implements Runnable {
    private Node node;
    private ServerSocket serverSocket;
    private String lastJSONContent;
    private List<PageLoadListener> listeners;

    public Server(ServerSocket socket) {
        serverSocket = socket;
        listeners = new ArrayList<>();
    }

    private static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    @Override
    public void run() {
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("Request recieved from: " + socket.getInetAddress() + ":" + socket.getPort());
                PrintWriter writer = new PrintWriter(socket.getOutputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                int contentLength = -1;
                String page = "";
                while (true) {
                    final String line = reader.readLine();

                    final String contentLengthStr = "Content-Length: ";
                    if (line.startsWith(contentLengthStr)) {
                        contentLength = Integer.parseInt(line.substring(contentLengthStr.length()));
                    }

                    if (line.startsWith("GET") || line.startsWith("POST")) {
                        page = line.split(" ")[1];
                        continue;
                    }

                    if (line.length() == 0) {
                        break;
                    }
                }

                String contentString = "";
                if (contentLength != -1 && contentLength >= 0) {
                    final char[] content = new char[contentLength];
                    if (reader.read(content) == -1) {
                        return;
                    }
                    contentString = new String(content);
                }

                try {
                    pageLoad(writer, page, contentString);
                } catch (NullPointerException e) {
                    addHeader(writer, 500, "text/html");
                    writer.println("Server Error");
                    e.printStackTrace();
                    for (StackTraceElement a : e.getStackTrace()) {
                        writer.println(a + "<br>");
                    }
                }
                writer.flush();
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void addHeader(PrintWriter writer, int status, String contentType) {
        writer.println("HTTP/1.0 " + status + "\r");
        writer.println("Content-Type: " + contentType + "; charset=UTF-8\r\n");
    }


    private void pageLoad(PrintWriter writer, String page, String content) throws InterruptedException {
        if (page.equalsIgnoreCase("/integration.html")) {
            addHeader(writer, 200, "text/html");
            lastJSONContent = content;
            writer.println("200 Success");
            Type type = new TypeToken<Map<String, Map<String, Object>>>() {
            }.getType();
            Map<String, Map<String, Object>> map = new Gson().fromJson(content, type);
            if (map != null) {
                node = new Node(map);
            }
        } else if (page.equalsIgnoreCase("/data.json")) {
            addHeader(writer, 200, "application/json");
            writer.println(lastJSONContent);
        } else if (page.equalsIgnoreCase("/") || page.equalsIgnoreCase("/index.html")) {

            if (node == null) {
                addHeader(writer, 200, "text/html");
                String notPlayingHtml = convertStreamToString(this.getClass().getResourceAsStream("/notplaying.html"));
                writer.write(notPlayingHtml);
                return;
            }

            if (node.map.game_state != Gamestate.DOTA_GAMERULES_STATE_DISCONNECT
                    && node.map.game_state != Gamestate.DOTA_GAMERULES_STATE_WAIT_FOR_PLAYERS_TO_LOAD
                    && node.map.game_state != Gamestate.DOTA_GAMERULES_STATE_STRATEGY_TIME
                    && node.hero.id != -1) {
                String indexHtml = convertStreamToString(this.getClass().getResourceAsStream("/index.html"));
                indexHtml = parseVariables(indexHtml);
                addHeader(writer, 200, "text/html");
                writer.write(indexHtml);

            } else if (node.map.game_state == Gamestate.DOTA_GAMERULES_STATE_HERO_SELECTION ||
                    node.map.game_state == Gamestate.DOTA_GAMERULES_STATE_STRATEGY_TIME ||
                    node.map.game_state != Gamestate.DOTA_GAMERULES_STATE_WAIT_FOR_PLAYERS_TO_LOAD) {
                String toPrint = convertStreamToString(this.getClass().getResourceAsStream("/notselected.html"));
                toPrint = parseVariables(toPrint, false);
                addHeader(writer, 200, "text/html");
                writer.write(toPrint);
            }
        } else {
            InputStream stream = this.getClass().getResourceAsStream(page);
            if (stream == null) {
                addHeader(writer, 404, "text/html");
                writer.println("<h1>404 File not found</h1>");
            } else {
                String[] extensions = page.split("\\.");
                String extension = extensions[extensions.length - 1];
                String contentType;
                switch (extension) {
                    case "css":
                        contentType = "text/css";
                        break;
                    case "html":
                        contentType = "text/html";
                        break;
                    case "js":
                        contentType = "application/javascript";
                        break;
                    default:
                        contentType = "text/plain";
                        break;

                }
                addHeader(writer, 200, contentType);
                String toSend = convertStreamToString(stream);
                writer.write(toSend);
            }
        }
    }

    private String parseVariables(String input) {
        return parseVariables(input, true);
    }

    @SuppressWarnings("ConstantConditions")
    private String parseVariables(String input, boolean ingame) {
        String toRet = input;
        Map<String, String> replaceMap = new HashMap<>();
        if (ingame) {
            replaceMap.put("!{last-hits}", String.valueOf(node.player.last_hits));
            replaceMap.put("!{denies}", String.valueOf(node.player.denies));
            replaceMap.put("!{kills}", String.valueOf(node.player.kills));
            replaceMap.put("!{deaths}", String.valueOf(node.player.deaths));
            replaceMap.put("!{assists}", String.valueOf(node.player.assists));
            replaceMap.put("!{kill-streak-count}", String.valueOf(node.player.kill_streak));
            replaceMap.put("!{hero-name-truncated}", node.hero.name.replace("npc_dota_hero_", ""));
            replaceMap.put("!{team-name}", node.player.team_name);
            replaceMap.put("!{clock-time}", String.valueOf(node.map.clock_time));
            replaceMap.put("!{custom-game-name}", node.map.custom_game_name);

            for (int i = 0; i < node.abilities.abilityList.size(); i++) {
                replaceMap.put("!{ability-name-" + i + "}", node.abilities.abilityList.get(i).name);
                replaceMap.put("!{ability-level-" + i + "}", String.valueOf(node.abilities.abilityList.get(i).level));
                replaceMap.put("!{ability-ultimate-" + i + "}", String.valueOf(node.abilities.abilityList.get(i).ultimate));
                replaceMap.put("!{ability-can-cast-" + i + "}", String.valueOf(node.abilities.abilityList.get(i).can_cast));
                replaceMap.put("!{ability-ability-active-" + i + "}",
                        String.valueOf(node.abilities.abilityList.get(i).ability_active));
            }
            int counter = 0;
            for (Item e : node.items) {
                replaceMap.put("!{item-name-" + counter + "}", e.name);
                replaceMap.put("!{item-name-truncated-" + counter + "}", e.name.replaceFirst("item_", ""));
                replaceMap.put("!{item-can-cast-" + counter + "}", String.valueOf(e.can_cast));
                replaceMap.put("!{item-charges-" + counter + "}", String.valueOf(e.charges));
                replaceMap.put("!{item-charges-" + counter + "}", String.valueOf(e.passive));
                replaceMap.put("!{item-cooldown-" + counter + "}", String.valueOf(e.cooldown));
                counter++;
            }
        }
        if (node.map.game_state_string != null) {
            replaceMap.put("!{game-state-unfriendly}", node.map.game_state_string);
        }
        replaceMap.put("!{game-state}", node.map.game_state.getFriendlyName());


        for (Map.Entry<String, String> e : replaceMap.entrySet()) {
            toRet = toRet.replace(e.getKey(), e.getValue());
        }
        return toRet;
    }
}
