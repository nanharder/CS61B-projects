package byow.Core;

import java.io.*;

public class IOtest {
    public static void main(String[] args) {
        File f = new File("./save_data.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                System.out.println(os.readObject());
            } catch (FileNotFoundException e) {
                //StdDraw.text(WIDTH / 2, HEIGHT / 4, "Sorry, you don't have save.");
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }
    }
}
