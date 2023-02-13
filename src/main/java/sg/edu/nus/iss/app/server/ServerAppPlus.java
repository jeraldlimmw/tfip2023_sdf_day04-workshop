package sg.edu.nus.iss.app.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerAppPlus {
    
    public static void main (String[] args) {

        String serverPort = args[0];
        System.out.println(serverPort);
        String fileName = args[1];
        System.out.println(fileName);
        String cookieResultFile = args[2];
        System.out.println(cookieResultFile);
        String replaceTextFile = args[3];
        System.out.println(replaceTextFile);

        try {
            ServerSocket server = new ServerSocket(Integer.parseInt(serverPort));
            System.out.printf("Cookie server started at %s\n", serverPort);
            Socket sock = server.accept();

            InputStream is = sock.getInputStream();
            DataInputStream dis = new DataInputStream(is);
            
            OutputStream os = sock.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);

            String dataFromClient = "";
            Cookie c = new Cookie(fileName);

            while (!dataFromClient.equalsIgnoreCase("close")) {
                // Client connect to the server
                // The server accept a connection from client
                
                dataFromClient = dis.readUTF();
                                
                if (dataFromClient.equals("get-cookie")) {
                    String randomCookie = c.getRandomCookie();
                    dos.writeUTF("cookie-text_" + randomCookie);
                    // write cookie to cookie_result.txt
                    c.writeCookieResult(cookieResultFile);
                    // replace $count in lorem.txt
                    c.replaceCount(randomCookie, replaceTextFile);
                } else {
                    dos.writeUTF("Invalid Command !");
                }
            }
            // Release resources from input/output stream and socket connection
            is.close();
            os.close();
            sock.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
