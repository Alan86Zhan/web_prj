package cn.twinkling.stream.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import cn.twinkling.stream.config.Configurations;
import cn.twinkling.stream.util.TokenUtil;

/**
 * According the file name and its size, generate a unique token. And this
 * token will be refer to user's file.
 */
public class TokenServlet extends HttpServlet {
	private static final long serialVersionUID = 2650340991003623753L;
	static final String FILE_NAME_FIELD = "name";
	static final String FILE_SIZE_FIELD = "size";
	static final String TOKEN_FIELD = "token";
	static final String SERVER_FIELD = "server";
	static final String SUCCESS = "success";
	static final String MESSAGE = "message";
	
	@Override
	public void init() throws ServletException {
		System.out.println("running1 ----- alan");
		String tempPath = this.getServletContext().getRealPath("/WEB-INF/tempalan");
		File tmpFile = new File(tempPath);
		                 if (!tmpFile.exists()) {
		                     //创建临时目录
		                     tmpFile.mkdir();
		                 }
		                 System.out.println("tempPath ----- alan==="+tempPath);

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		System.out.println("running2 ----- alan");
		String name = req.getParameter(FILE_NAME_FIELD);
		System.out.println("name ----- alan====="+name);
		String size = req.getParameter(FILE_SIZE_FIELD);
		System.out.println("size ----- alan====="+size);
		String token = TokenUtil.generateToken(name, size);
		System.out.println("token ----- alan===="+token);
		PrintWriter writer = resp.getWriter();
		
		JSONObject json = new JSONObject();
		System.out.println("trybegin ----- alan");
		try {
			json.put(TOKEN_FIELD, token);
			
			if (Configurations.isCrossed())
			{
				System.out.println("trybegin --if--- alan");
				json.put(SERVER_FIELD, Configurations.getCrossServer());
				System.out.println("Configurations.isCrossed() alan");
			}
			System.out.println("trybegin --if-not-json.put- alan");
				json.put(SUCCESS, true);
			json.put(MESSAGE, "");
		} catch (JSONException e) {
			System.out.println("erro token alan");
		}
		/** TODO: save the token. */
		
		writer.write(json.toString());
		System.out.println("tryend -ok-writer- alan");
		
	}

	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		super.doHead(req, resp);
	}

	@Override
	public void destroy() {
		super.destroy();
	}

}
