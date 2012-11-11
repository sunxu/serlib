<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.io.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.json.simple.*" %>
<%@page import="com.serlib.util.Tool"%>
<%

if(session.getAttribute("adminid") == null){
	out.println(getError("您还未登录，请先登录。"));
	return;
}

Object status = session.getAttribute(Tool.JAR_FILE_STATUS);

if(status != null && !status.toString().equals("none")){
	out.println(getError("类库还未处理完成，请稍后再试！"));
	return;
}

String date = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

//文件保存目录路径
String savePath = pageContext.getServletContext().getRealPath("/") + "data/temp" ;

response.setContentType("text/html; charset=UTF-8");

if(!ServletFileUpload.isMultipartContent(request)){
	out.println(getError("请选择文件。"));
	return;
}

//检查目录
File uploadDir = new File(savePath);
if(!uploadDir.exists()){
	uploadDir.mkdirs();
}

//检查目录写权限
if(!uploadDir.canWrite()){
	out.println(getError("上传目录没有写权限。"));
	return;
}

FileItemFactory factory = new DiskFileItemFactory();
ServletFileUpload upload = new ServletFileUpload(factory);
upload.setHeaderEncoding("UTF-8");
List items = upload.parseRequest(request);
Iterator itr = items.iterator();
while (itr.hasNext()) {
	FileItem item = (FileItem) itr.next();
	String fileName = item.getName();
	long fileSize = item.getSize();
	if (!item.isFormField()) {
		//检查扩展名
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		if(!fileExt.equals("jar"))
		{
			out.println(getError("请上传JAVA类库文件！"));
			return;
		}
		Random random = new Random();
		String newFileName = date + "_" 
							+ random.nextInt(10) + random.nextInt(10) 
							+ random.nextInt(10) + random.nextInt(10) 
							+ random.nextInt(10) + random.nextInt(10) 
							+ "." + fileExt;
		try{
			File uploadedFile = new File(savePath, newFileName);
			item.write(uploadedFile);
		}catch(Exception e){
			out.println(getError("上传文件失败。"));
			return;
		}

		session.setAttribute(Tool.JAR_FILE_STATUS,"uploaded");
		session.setAttribute(Tool.JAR_FILE_TEMPNAME,newFileName);
		session.setAttribute(Tool.JAR_FILE_NAME,fileName);
		JSONObject obj = new JSONObject();
		obj.put("error", 0);
		out.println(obj.toJSONString());
	}
}
%>
<%!
private String getError(String message) {
	JSONObject obj = new JSONObject();
	obj.put("error", 1);
	obj.put("message", message);
	return obj.toJSONString();
}
%>