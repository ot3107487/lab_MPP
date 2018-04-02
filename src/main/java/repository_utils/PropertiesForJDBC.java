package repository_utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesForJDBC {
    private static Properties serverProps = null;

    public static Properties getProperties() {
        if (serverProps == null) {
            serverProps = new Properties();
            try {
                serverProps.load(new FileReader("bd.config"));
                //System.setProperties(serverProps);

                System.out.println("Properties set. ");
                //System.getProperties().list(System.out);
                serverProps.list(System.out);
                return serverProps;
            } catch (IOException e) {
                System.out.println("Cannot find bd.config " + e);
            }

        }
        return serverProps;
    }
}
