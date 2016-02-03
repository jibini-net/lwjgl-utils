package net.jibini.glutils.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileLoader
{
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
