package net.jibini.glutils.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Utility for loading text files.
 */
public class FileLoader
{
	/**
	 * Loads the given file into a string.
	 * 
	 * @param path Path in JAR to text file.
	 * @return Contents of given file as a string.
	 * @throws IOException if a reading error occurs.
	 */
	public static String loadFile(String path) throws IOException
	{
		InputStream stream = FileLoader.class.getResourceAsStream(path);
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder result = new StringBuilder();
		String line = "";
		while ((line = reader.readLine()) != null)
			result.append("\n" + line);
		reader.close();
		return result.toString().substring(1);
	}
}
