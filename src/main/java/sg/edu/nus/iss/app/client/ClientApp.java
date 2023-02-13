package sg.edu.nus.iss.app.client;

import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientApp {
    public static void main( String[] args ) {

        String[] arrSplit = args[0].split(":");
        System.out.println(arrSplit[0]);
        System.out.println(arrSplit[1]);

        try {
            Socket sock = new Socket(arrSplit[0], Integer.parseInt(arrSplit[1])); 
            //localhost and port number as int

            InputStream is = sock.getInputStream();
            DataInputStream dis = new DataInputStream(is);

            OutputStream os = sock.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);

            Console cons = System.console();
            String input = "";
            String serverResponse = "";

            while (!input.equalsIgnoreCase("close")) {                
                input = cons.readLine("Send command to the random cookie server?");
            
                dos.writeUTF(input);
                dos.flush(); // cannot close the output

                serverResponse = dis.readUTF();
                if (serverResponse.contains("cookie-text")); {
                    String[] cookieValArr = serverResponse.split("_");
                    System.out.printf("Cookie from the server: %s\n",cookieValArr[1]);
                }
            }
            is.close();
            os.close();
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}