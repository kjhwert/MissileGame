

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class FileManager {
	private static FileManager curr = null;
	
	public static FileManager getInstance() {
		if (curr == null) {
			curr = new FileManager();
		}
		return curr;
	}
	
	private FileManager() {
		
	}
	
	public static String readFile(String path) {
		byte[] by = null;
		String resultStr = "";
		InputStream input = null;
		
		try {
			input = new FileInputStream(path);
			by = new byte[input.available()];
			input.read(by);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			resultStr = new String(by,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return resultStr;
	}
	
	public static void writeFile(String path,String str) {
		OutputStream out = null;
		try {
			byte[] by = str.getBytes("UTF-8");
			
			out = new FileOutputStream(path);
			out.write(by);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
	}
}
