package sg.edu.nus.iss.app.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Cookie {
    
    private List<String> cookies;
    private List<String> cookieResultList;

    // open file, store cookies as an ArrayList
    public Cookie(String fileName) {
        File f = new File(fileName); // "cookies_file.txt");
        try {
            Scanner input = new Scanner(f);
            cookies = new ArrayList<>();
            cookieResultList = new ArrayList<>();
            
            while (input.hasNextLine()) {
                cookies.add(input.nextLine());
                cookieResultList.add("");
            }
            System.out.println(cookies.toString());
            System.out.println(cookieResultList.toString());
            input.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getCookieIndex(String name) {
        return cookies.indexOf(name);
    }

    public String getCookieName(int index) {
        return cookies.get(index);
    }

    public String getRandomCookie() {

        String randomCookie = "";

        Random rand = new Random();
        int index = rand.nextInt(cookies.size());
        randomCookie = cookies.get(index);
        System.out.println("Random Cookie >> " + randomCookie);
        
        if (!cookieResultList.get(index).equals(randomCookie)) {
            cookieResultList.set(index, randomCookie);
        }

        return randomCookie;
    }
    
    // When randomCookie is called, write the name of the random cookie in a file cookie_result.txt
    public void writeCookieResult(String fileName) throws IOException {
        File fileCookieResults = fileCreated(fileName);
        FileWriter writer = new FileWriter(fileCookieResults);
        for (String r : cookieResultList) {
            writer.write(r + "\n");
        }
        writer.close();
    }

    // From the cookie called, count its number of char and change the $count in lorem_text.txt to that number
    public void replaceCount(String randomCookie, String fileName) throws IOException {
        // Get char count of random cookie
        int charCount = randomCookie.length();
        String charCountStr = String.valueOf(charCount);
        
        // Make sure file exists
        File fileReplaceText = fileCreated(fileName);
        
        // Read the text from the file
        InputStream is = new FileInputStream(fileReplaceText);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        // Store in a temp list
        List<String> temp = new ArrayList<>();
        String line = "";
        while((line = br.readLine()) != null) {
            temp.add(line);
        }
        
        FileWriter writer = new FileWriter(fileReplaceText, false);
        for(String s : temp) {
            writer.write(s.replace("$count", charCountStr) + "\n");
        }

        writer.close();
        br.close();
        isr.close();
        is.close();
    }

    public static File fileCreated(String fileName) {
        File f = new File(fileName);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }
}
