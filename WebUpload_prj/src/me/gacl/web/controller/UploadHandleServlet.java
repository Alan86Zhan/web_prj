package me.gacl.web.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.ProgressListener;

import cn.twinkling.stream.config.Configurations;
 
public class UploadHandleServlet extends HttpServlet {
 
 public static String usernamevalue="";

public void doGet(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
//�õ��ϴ��ļ��ı���Ŀ¼�����ϴ����ļ������WEB-INFĿ¼�£����������ֱ�ӷ��ʣ���֤�ϴ��ļ��İ�ȫ
String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
//�ϴ�ʱ���ɵ���ʱ�ļ�����Ŀ¼
String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
File tmpFile = new File(tempPath);
                 if (!tmpFile.exists()) {
                     //������ʱĿ¼
                     tmpFile.mkdir();
                 }


File file = new File(savePath);
//�ж��ϴ��ļ��ı���Ŀ¼�Ƿ����
if (!file.exists() && !file.isDirectory()) {
System.out.println(savePath+"!!!!!!!!/WEB-INF/upload-dir doesn't exist,!!!!!!!!!!!");
//����Ŀ¼
file.mkdirs();

}
String usertagPath = this.getServletContext().getRealPath("/usertag");
File usertagfile = new File(usertagPath);
//�ж��ϴ��ļ��ı���Ŀ¼�Ƿ����
if (!usertagfile.exists() && !usertagfile.isDirectory()) {
System.out.println(usertagfile+"!!!!/usertag!!!!dir doesn't exist,!!!!!!!!!!!");
//����Ŀ¼
usertagfile.mkdirs();

}
//��Ϣ��ʾ
String message = "";
String usermessage = "";

try{
//ʹ��Apache�ļ��ϴ���������ļ��ϴ����裺
//1������һ��DiskFileItemFactory����
DiskFileItemFactory factory = new DiskFileItemFactory();
//���ù����Ļ������Ĵ�С�����ϴ����ļ���С�����������Ĵ�Сʱ���ͻ�����һ����ʱ�ļ���ŵ�ָ������ʱĿ¼���С�
factory.setSizeThreshold(1024*10000);//���û������Ĵ�СΪ100KB�������ָ������ô�������Ĵ�СĬ����10KB
//�����ϴ�ʱ���ɵ���ʱ�ļ��ı���Ŀ¼
factory.setRepository(tmpFile);
//2������һ���ļ��ϴ�������
ServletFileUpload upload = new ServletFileUpload(factory);
//�����ļ��ϴ�����
upload.setProgressListener(new ProgressListener(){
                         public void update(long pBytesRead, long pContentLength, int arg2) {
                             System.out.println("�ļ���СΪ��" + pContentLength + ",��ǰ�Ѵ���" + pBytesRead);
                             /**
                              * �ļ���СΪ��14608,��ǰ�Ѵ���4096
                                 �ļ���СΪ��14608,��ǰ�Ѵ���7367
                                 �ļ���СΪ��14608,��ǰ�Ѵ���11419
                                 �ļ���СΪ��14608,��ǰ�Ѵ���14608
                              */
                         }
                     });
//����ϴ��ļ�������������
upload.setHeaderEncoding("UTF-8"); 
//3���ж��ύ�����������Ƿ����ϴ���������
if(!ServletFileUpload.isMultipartContent(request)){
//���մ�ͳ��ʽ��ȡ����
	
	System.out.println("Error 3::::!ServletFileUpload.isMultipartContent(request)");
return;
}
//4��ʹ��ServletFileUpload�����������ϴ����ݣ�����������ص���һ��List<FileItem>���ϣ�ÿһ��FileItem��Ӧһ��Form����������
List<FileItem> list = upload.parseRequest(request);
String userPath = "";
String userPathupload = "";

String userpemPath="";
int i=0;
for(FileItem item : list){
	i++;
//���fileitem�з�װ������ͨ�����������
if(item.isFormField()){
String name = item.getFieldName();
//�����ͨ����������ݵ�������������
String value = item.getString("UTF-8");
usernamevalue =value;
System.out.println("username***value**is"+value);
System.out.println("username***usernamevalue**is"+usernamevalue);
//value = new String(value.getBytes("iso8859-1"),"UTF-8");
/**����û�Ŀ¼**/
String useruploadPath = this.getServletContext().getRealPath("/WEB-INF/upload")+File.separator+value;
userPathupload = useruploadPath;
File useruploadfile = new File(useruploadPath);
if (!useruploadfile.exists() && !useruploadfile.isDirectory()) {
System.out.println(useruploadPath+"!!!!!!!!dir doesn't exist,!!!!!!!!!!!");
//����Ŀ¼
useruploadfile.mkdirs();

}
else
{
	//useruploadfile.delete();
	useruploadfile.mkdirs();
 System.out.println(useruploadPath+"~~~~~~dir is existed~~~~~");
}
/**����û�Ŀ¼**/


System.out.println(name + "=" + value);
if(value==null || value.trim().equals("")){
	System.out.println("you need to input a name::::::~~");
	message= "�ļ��ϴ�ʧ�ܣ����벻��Ϊ��";
	request.setAttribute("message",message);
	request.getRequestDispatcher("/wrongmessage.jsp").forward(request, response);
	return;
//continue;
}

}else{//���fileitem�з�װ�����ϴ��ļ�
//�õ��ϴ����ļ����ƣ�
String filename = item.getName();
//System.out.println("upload file name is::::::~~"+filename);
if(filename==null || filename.trim().equals("")){
	System.out.println("you need to input a pemfile and file::::::~~");
	message= "�ļ��ϴ�ʧ�ܣ����벻��Ϊ��";
	request.setAttribute("message",message);
	request.getRequestDispatcher("/wrongmessage.jsp").forward(request, response);
	return;
//continue;
}
//ע�⣺��ͬ��������ύ���ļ����ǲ�һ���ģ���Щ������ύ�������ļ����Ǵ���·���ģ��磺 c:\a\b\1.txt������Щֻ�ǵ������ļ������磺1.txt
//�����ȡ�����ϴ��ļ����ļ�����·�����֣�ֻ�����ļ�������

System.out.println("upload file name 1 is::::::~~"+filename);
filename = filename.substring(filename.lastIndexOf("\\")+1);
//filename = filename.substring(filename.lastIndexOf("\\")+1);
System.out.println("upload file name 2 is::::::~~"+filename);
//��ȡitem�е��ϴ��ļ���������
InputStream in = item.getInputStream();
//�õ��ļ����������
                           //  String saveFilename = makeFileName(filename);
                             //�õ��ļ��ı���Ŀ¼
                          //   String realSavePath = makePath(saveFilename, savePath);



//����һ���ļ������
//FileOutputStream out = new FileOutputStream(savePath + "/" + filename);

//FileOutputStream out = new FileOutputStream(savePath + "\\" + filename);
System.out.println(userPath+"!!!!!!!!userpath as you can see!!!!!!!!!!!");


/**����û�pemĿ¼**/
if(i==2){
	
	
String usernamePath = this.getServletContext().getRealPath("/WEB-INF/userpem")+File.separator+usernamevalue;

userpemPath = usernamePath;
File userpemfile = new File(usernamePath);
if (!userpemfile.exists() && !userpemfile.isDirectory()) {
//����Ŀ¼
	
userpemfile.mkdirs();
userPath =userpemPath;
System.out.println("userPath is "+userpemPath+"i =="+i+"!!!!!!!!!!!!!!!!!!!");
}
else
{
 //userpemfile.delete();
 userpemfile.mkdirs();
 userPath =userpemPath;
 System.out.println("userpemPath is "+userPath+"i =="+i+"!!!!!!!!!!dir is existed!!!!!!!!!");
}
/**����û�pemĿ¼**/
}
else 
{
	userPath = userPathupload;
System.out.println("userPath is "+userPath+"i =="+i+"!!!!!!!!!!!!!!!!!!!");
}

FileOutputStream out = new FileOutputStream(userPath + File.separator + filename);
System.out.println("~~~~~~userPath dir is ~~~~~"+userPath);

//����һ��������
byte buffer[] = new byte[1024];
//�ж��������е������Ƿ��Ѿ�����ı�ʶ
int len = 0;
//ѭ�������������뵽���������У�(len=in.read(buffer))>0�ͱ�ʾin���滹������
while((len=in.read(buffer))>0){
//ʹ��FileOutputStream�������������������д�뵽ָ����Ŀ¼(savePath + "\\" + filename)����
out.write(buffer, 0, len);
}
//�ر�������
in.close();
//�ر������
out.close();
//ɾ�������ļ��ϴ�ʱ���ɵ���ʱ�ļ�
item.delete();
message = "�ļ��ϴ��ɹ���";
usermessage=usernamevalue;
}
}
}catch (Exception e) {
message= "�ļ��ϴ�ʧ�ܣ�";
e.printStackTrace();
 
}

request.setAttribute("message",message);
request.setAttribute("usermessage",usermessage);
request.getRequestDispatcher("/message.jsp").forward(request, response);

System.out.println("endusername***usernamevalue**is"+usernamevalue);
//Configurations.setUploadusername();


}
private String makeFileName(String filename){  //2.jpg
         //Ϊ��ֹ�ļ����ǵ���������ҪΪ�ϴ��ļ�����һ��Ψһ���ļ���
         return UUID.randomUUID().toString() + "_" + filename;
     }
     
private String makePath(String filename,String savePath){
         //�õ��ļ�����hashCode��ֵ���õ��ľ���filename����ַ����������ڴ��еĵ�ַ
         int hashcode = filename.hashCode();
         int dir1 = hashcode&0xf;  //0--15
         int dir2 = (hashcode&0xf0)>>4;  //0-15
         //�����µı���Ŀ¼
         String dir = savePath + "\\" + dir1 + "\\" + dir2;  //upload\2\3  upload\3\5
         //File�ȿ��Դ����ļ�Ҳ���Դ���Ŀ¼
         File file = new File(dir);
         //���Ŀ¼������
         if(!file.exists()){
             //����Ŀ¼
             file.mkdirs();
         }
         return dir;
     }
 
public void doPost(HttpServletRequest request, HttpServletResponse response)
throws ServletException, IOException {
doGet(request, response);
}
/**/
public   String getusername1()
{
	
	System.out.println("uploadhandleservlet==username==="+usernamevalue);
	return usernamevalue;
}
/**/
}
