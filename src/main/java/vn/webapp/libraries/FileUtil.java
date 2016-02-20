package vn.webapp.libraries;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {
	
	public static final String EOL = System.getProperty("line.separator");
	
	/**
	 * Get content from a file
	 * @param sFilePath
	 * @return
	 * @throws IOException
	 */
	public static StringBuilder sGetFileContent(String sFilePath) throws IOException 
	{
       BufferedReader br = null;
       FileReader fr = null;

		try {
			fr = new FileReader(sFilePath);
			br = new BufferedReader(fr);
			String nextLine = "";
			StringBuilder sb = new StringBuilder();
			while ((nextLine = br.readLine()) != null) {
				sb.append(nextLine); // note: BufferedReader strips the EOL character so we add a new one!
				sb.append(EOL);
			}
			return sb;
		} finally {
			if (br != null)
				br.close();
			if (fr != null)
				fr.close();
		}
	 }
	
	/**
	 * Replace a substring by a substring
	 * @param sb
	 * @param pattern
	 * @param replacement
	 * @return
	 */
	public static StringBuilder sReplaceAll(StringBuilder sb, String pattern, String replacement) {
		Pattern p = Pattern.compile(pattern);
	    Matcher m = p.matcher(sb);
	    int start = 0;
	    while(m.find(start)) {
	        sb.replace(m.start(), m.end(), replacement);
	        start = m.start() + replacement.length();
	    }
	    return sb;
	}
	
	   
    /**
     * Writing content into a file
     * @param file
     * @param content
     */
    public static void v_fWriteContentIntoAFile(File file, StringBuilder content) {
		try {
			// Ff file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content.toString());
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
