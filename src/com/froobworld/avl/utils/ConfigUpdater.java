package com.froobworld.avl.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ConfigUpdater {
	public static boolean update(File configFile, InputStream patchInputStream, int versionFrom) {
		List<String> lines = new ArrayList<>();

		try (BufferedReader configReader = new BufferedReader(new FileReader(configFile))) {
			String line = configReader.readLine();
			while (line != null) {
				if (line.startsWith("version:")) {
					line = line.replaceFirst("version: " + versionFrom, "version: " + (versionFrom + 1));
				}
				lines.add(line);
				line = configReader.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		try (PrintWriter writer = new PrintWriter(new FileWriter(configFile))) {
			for (String string : lines) {
				writer.println(string);
			}

			try (BufferedReader patchReader = new BufferedReader(new InputStreamReader(patchInputStream))) {
				writer.println("");
				writer.println("");
				writer.println("###### Added in config update " + versionFrom + " to " + (versionFrom + 1) + " ######");
				String line = patchReader.readLine();
				while (line != null) {
					writer.println(line);
					line = patchReader.readLine();
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
}