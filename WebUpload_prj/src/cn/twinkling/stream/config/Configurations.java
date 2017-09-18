package cn.twinkling.stream.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import me.gacl.web.controller.UploadHandleServlet;

/**
 * read the configurations from file `config.properties`.
 */
public class Configurations {
	
	// UploadHandleServlet ups=new UploadHandleServlet();
	// String username= ups.username1();
	//static String username=ups.username1();
	static String username=UploadHandleServlet.usernamevalue;
	
	
	static final String CONFIG_FILE = "stream-config.properties";
	
	//String usernametest=UploadHandleServlet.getusername1();
	
	private static Properties properties = null;
	private static final String REPOSITORY = System.getProperty(
			"java.io.tmpdir", File.separator + "tmp" + File.separator
					+ "upload-repository");
	private  String REPOSITORY1 = System.getProperty(
			"java.io.tmpdir", File.separator + "tmp" + File.separator
					);
	//得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
	//String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
	
	
	
	static {
		new Configurations();
	}

	private Configurations() {
		init();
		System.out.println("[NOTICE] File Repository Path"+ getFileRepository());
	}

	void init() {
		try {
			ClassLoader loader = Configurations.class.getClassLoader();
			System.out.println("Configurations!!!!!classloder!!!CONFIG_FILE!!"+CONFIG_FILE+"####REPOSITORY###"+REPOSITORY);
			InputStream in = loader.getResourceAsStream(CONFIG_FILE);
			System.out.println("Configurations!!!!!InputStream in!!!!!"+in);
			properties = new Properties();
			properties.load(in);
			System.out.println("username!!!!!!!!!!is="+username);
			System.out.println("REPOSITORY1 is &&&&&&&"+REPOSITORY1);
			//System.out.println("usernametest____is"+usernametest);
			
		} catch (IOException e) {
			System.err.println("reading `" + CONFIG_FILE + "` error!" + e);
		}
	}

	public static String getConfig(String key) {
		return getConfig(key, null);
	}

	public static String getConfig(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	public static int getConfig(String key, int defaultValue) {
		String val = getConfig(key);
		int setting = 0;
		try {
			setting = Integer.parseInt(val);
		} catch (NumberFormatException e) {
			setting = defaultValue;
		}
		return setting;
	}

	public static String getFileRepository() {
		System.out.println("****Configurations.java*String val = getConfig*");
		String val = getConfig("STREAM_FILE_REPOSITORY")+File.separator+setUploadusername();
		System.out.println("****Configurations.java*val*"+val);
		System.out.println("****String setUploadusername*");
		System.out.println("username*******val**is="+setUploadusername());
		
		if (val == null || val.isEmpty())
		{
			val = REPOSITORY;
			System.out.println("****Configurations.java*val = REPOSITORY;*"+val);
		}
			return val;
	}

	
	
	public static String getCrossServer() {
		return getConfig("STREAM_CROSS_SERVER");
	}
	
	public static String getCrossOrigins() {
		return getConfig("STREAM_CROSS_ORIGIN");
	}
	
	public static boolean getBoolean(String key) {
		return Boolean.parseBoolean(getConfig(key));
	}
	
	public static boolean isDeleteFinished() {
		return getBoolean("STREAM_DELETE_FINISH");
	}
	
	public static boolean isCrossed() {
		return getBoolean("STREAM_IS_CROSS");
	}
	

	public static String setUploadusername() {
		// TODO Auto-generated method stub
		 System.out.println("类A中的变量修改前"+UploadHandleServlet.usernamevalue);
		return UploadHandleServlet.usernamevalue;
		
		
	}
	
}
