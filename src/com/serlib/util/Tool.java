package com.serlib.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Tool {
	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
		'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	protected static MessageDigest messagedigest =  null;
	
	static {
		try {
			messagedigest =  MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException nsaex) {
			nsaex.printStackTrace();
		}
	}
	
	public static final String JAR_FILE_STATUS = "jarFileStatus";
	public static final String JAR_FILE_TEMPNAME = "jarFileTempName";
	public static final String JAR_FILE_NAME = "jarFileName";
	public static final String JAR_FILE_MD5 = "jarFileMd5";
	public static final String JAR_FILE_ID = "jarFileId";
	public static final String JAR_ANALYZE_STATUS = "jarAnalyzeStatus";
	public static final String JAR_ANALYZE_MESSAGE = "jarAnalyzeMessage";
	
	private static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}
	
	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}
	
	public static String getMD5String(byte[] bytes) {
		messagedigest.update(bytes);
		return bufferToHex(messagedigest.digest());
	}
	
	public static String getMD5String(String s) {
		return getMD5String(s.getBytes());
	}
	
	public static String getFileMD5String(File file) throws IOException, NoSuchAlgorithmException {
		FileInputStream in = new FileInputStream(file);
		FileChannel ch = in.getChannel();
		MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0,
				file.length());
		messagedigest.update(byteBuffer);
		return bufferToHex(messagedigest.digest());
	}
	
	public static int getTimestamp() {
		return (int) (System.currentTimeMillis() / 1000);
	}
	
	public static void writeListToFile(List<String> list, File file) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			for(String string: list){
				oos.writeObject(string);
			}
			byte[] data = baos.toByteArray();
			file.deleteOnExit();
			OutputStream os = new FileOutputStream(file.getAbsolutePath());
			os.write(data);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static List<String> readStringListFromFile(File file) {
		List<String> list = new ArrayList<String>();
		try {
			FileInputStream fis = new FileInputStream(file.getAbsolutePath());
			ObjectInputStream ois = new ObjectInputStream(fis);
			while (fis.available() > 0) {
				String string = ois.readObject().toString();
				list.add(string);
			}
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static boolean string2File(String string, File file) {
		boolean flag = true;
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		try {
			bufferedReader = new BufferedReader(new StringReader(string));
			bufferedWriter = new BufferedWriter(new FileWriter(file));
			char buf[] = new char[1024]; 
			int len;
			while ((len = bufferedReader.read(buf)) != -1) {
				bufferedWriter.write(buf, 0, len);
			}
			bufferedWriter.flush();
			bufferedReader.close();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
			return flag;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}
	
	@SuppressWarnings("unchecked")
	public static synchronized void decompress(File fileName, File outputDir) throws IOException {
		if (!outputDir.exists()) {
			outputDir.mkdirs();
		}
		String outputPath = outputDir.getPath();
		if (!outputPath.endsWith(File.separator)) {
			outputPath += File.separator;
		}
		JarFile jf = new JarFile(fileName);
		for (Enumeration e = jf.entries(); e.hasMoreElements();) {
			JarEntry je = (JarEntry) e.nextElement();
			String outFileName = outputPath + je.getName();
			File f = new File(outFileName);
			if (outFileName.endsWith("/") || outFileName.endsWith("//")
					|| outFileName.endsWith(File.separator)) {
				f.mkdirs();
			} else {
				f.getParentFile().mkdirs();
				InputStream in = jf.getInputStream(je);
				OutputStream out = new BufferedOutputStream(
						new FileOutputStream(f));
				byte[] buffer = new byte[2048];
				int nBytes = 0;
				while ((nBytes = in.read(buffer)) > 0) {
					out.write(buffer, 0, nBytes);
				}
				out.flush();
				out.close();
				in.close();
			}
		}
	}

	public static synchronized List<String> exec(String[] cmd) throws IOException {
		List<String> strings = new ArrayList<String>();
		try {
			Process ps = Runtime.getRuntime().exec(cmd);
			BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
			String line;
			while ((line = br.readLine()) != null) {
				strings.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strings;
	}
	
	public static void generateShowData(File jarDir, List<String> strings) throws IOException{
		if(strings == null || strings.size() == 0)
			return;
		String fileNameString = strings.get(0);
		String fileName = getClassNameFromString(fileNameString);
		File showDir = new File(jarDir, "show");
		if(!showDir.exists())
			showDir.mkdirs();
		File file = new File(showDir, fileName+".html");
		
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\"M_list\">\n\t<ul>\n");
		
		for (String string : strings) {
			sb.append("\t\t<li>" + string + "<br>\n");
		}
		sb.append("\t</ul>\n</div>\n");
		string2File(sb.toString(), file);
	}

	public static void generateSolrData(File jarDir, List<String> strings,
			Map<String, String> solrInfoMap,List<String> classIds) throws IOException{
		if(strings == null || strings.size() == 0)
			return;
		String fileNameString = strings.get(0);
		String fileName = getClassNameFromString(fileNameString);
		File solrDir = new File(jarDir, "solr");
		if(!solrDir.exists())
			solrDir.mkdirs();
		File file = new File(solrDir, fileName+".xml");
		StringBuffer sb = new StringBuffer();
		
		String classId = getMD5String(solrInfoMap.get(JAR_FILE_MD5)+fileName);
		classIds.add(classId);
		
		sb.append("<add>\n\t<doc>\n");
		sb.append("\t\t<field name=\"id\">"+classId+"</field>\n");
		sb.append("\t\t<field name=\"jar\">"+solrInfoMap.get(JAR_FILE_NAME)+"</field>\n");
		sb.append("\t\t<field name=\"jarid\">"+solrInfoMap.get(JAR_FILE_ID)+"</field>\n");
		sb.append("\t\t<field name=\"class\">"+fileName+"</field>\n");

		Set<String> nameList = new HashSet<String>();
		String temp;
		for (String string : strings) {
			temp = getMethodNameFromString(string);
			if(temp != null && !temp.equals("") && !temp.equals("}"))
				nameList.add(temp);
		}
		for(String string:nameList){
			sb.append("\t\t<field name=\"method\">"+fileName + "." + string+"</field>\n");
		}
		sb.append("\t</doc>\n</add>\n");
		string2File(sb.toString(), file);
	}
	
	public static String getClassNameFromString(String string) {
		String[] names = string.split(" ");
		String temp;
		for (int i = 0; i < names.length; i++) {
			temp = names[i].toLowerCase();
			if(!temp.equals(names[i]))
					return names[i].split("\\{")[0];
		}
		return null;
	}
	
	public static String getMethodNameFromString(String string) {
		String[] temps = string.split("\\(");
		if(temps.length <= 1)//remove properties
			return null;
		temps = temps[0].split(" ");
		String temp = temps[temps.length-1];
		if(temp.indexOf(".") < 0)//remove constructors
				return temp;
		return null;
	}
	
	public static void analyzeClass(File classDir, File outputDir,
			Map<String, String> solrInfoMap,List<String> classIds) throws IOException{
		String[] dirlist = classDir.list();
		File classFile;
		List<String> infoList;
		String fileExt;
		String fileName;
		for (int i = 0; i < dirlist.length; i++) {
			classFile = new File(classDir,dirlist[i]);
			if(classFile.isDirectory()){
				analyzeClass(classFile, outputDir, solrInfoMap ,classIds);
			}else { 
				fileExt = dirlist[i].substring(dirlist[i].lastIndexOf('.')+1).toLowerCase();
				if(new String("class").equals(fileExt)) {
					fileName = dirlist[i].substring(0,dirlist[i].lastIndexOf('.'));
					infoList = exec(new String[]{"javap", "-classpath",classFile.getParent(),fileName});
					infoList.remove(0);
					generateShowData(outputDir,infoList);
					generateSolrData(outputDir, infoList, solrInfoMap,classIds);
				}
			}
		}
	}
}
