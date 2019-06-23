package com.froobworld.avl.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConfigUpdater {
    public static boolean update(File configFile, InputStream patchInputStream, int versionFrom) {
        try {
            BufferedReader configReader = new BufferedReader(new FileReader(configFile));

            List<String> lines = new ArrayList<String>();
            String line = configReader.readLine();
            while(line != null) {
                if(line.startsWith("version:")) {
                    line = line.replaceFirst("version: " + versionFrom, "version: " + (versionFrom + 1));
                }
                lines.add(line);
                line = configReader.readLine();
            }
            configReader.close();

            PrintWriter writer = new PrintWriter(new FileWriter(configFile));

            for(String string : lines) {
                writer.println(string);
            }

            BufferedReader patchReader = new BufferedReader(new InputStreamReader(patchInputStream));

            writer.println("");
            writer.println("");
            writer.println("###### Added in config update " + versionFrom + " to " + (versionFrom + 1) +  " ######");
            line = patchReader.readLine();
            while(line != null) {
                writer.println(line);
                line = patchReader.readLine();
            }

            patchReader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}