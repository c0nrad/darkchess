package com.c0nrad.darkchess.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

    public final static String PORT="port";
    public final static String MONGOURI="mongouri";

    public static Properties properties;

    public static void LoadProperties() throws FileNotFoundException, IOException {
        properties = new Properties();
        
        InputStream input = new FileInputStream("./config/config.properties");
        properties.load(input);
    }
}