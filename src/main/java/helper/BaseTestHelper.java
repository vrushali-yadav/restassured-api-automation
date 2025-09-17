package helper;

import java.io.File;
import java.util.Date;

public class BaseTestHelper {

    // Create a folder
    public static void createFolder(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();    // mkdir() is used to create a new folder/ directory
        } else {
            System.out.println("Folder already created");
        }
    }

    // Return current timestamp
    public static String timestamp() {
        Date current = new Date();
        return current.toString().replace(":", "-");
    }
}
