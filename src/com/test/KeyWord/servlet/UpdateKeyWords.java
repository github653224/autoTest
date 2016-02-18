package com.test.KeyWord.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.test.statics.Mysql;

public class UpdateKeyWords extends HttpServlet {
	/**
	 * 更新关键字
	 */
	private static final long serialVersionUID = 101;

	public void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		System.out.println("log::UpdateKeyWords:"
				+ request.getRequestURL().toString());
		PrintWriter out = resp.getWriter();
		String id = "";
		String sqli = "";
		String sqlu = "";
		String type = "NULL";
		int ret = 0;
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			id = request.getParameter("id");
			if (id.equals("NULL") || id.equals(""))
				id = null;
		} catch (Exception e) {
		}

		try {
			type = request.getParameter("type");
			if (type.equals("NULL") || type.equals(""))
				type = null;
		} catch (Exception e) {
		}
		if (id != null && type != null) {
			sqlu += "update KeyWords set id=" + id + ", type=" + type
					+ ", keyName='" + request.getParameter("keyName")
					+ "', describes='" + request.getParameter("describes")
					+ "', useCase='" + request.getParameter("useCase")
					+ "' where id='" + id + "'";
			sqli += "insert into KeyWords values(" + id + "," + type + ",'"
					+ request.getParameter("keyName") + "','"
					+ request.getParameter("describes") + "','"
					+ request.getParameter("useCase") + "')";
			ret = updateKeyWords(sqli, sqlu, id);
		} else
			System.out.println("关键字信息有误，请检查。");
		out.print(ret);
		resp.sendRedirect("index.jsp");
	}

	private int updateKeyWords(String sqli, String sqlu, String id) {
		String str = "";
		int rs1;
		// System.out.println(sqli);
		// System.out.println(sqlu);
		try {
			Statement sm = Mysql.ct.createStatement();
			String sql = "select id from KeyWords where id='" + id + "'";
			ResultSet rs = sm.executeQuery(sql);
			while (rs.next()) {
				str = rs.getString(1);
			}
			if (str == "") {
				rs1 = sm.executeUpdate(sqli);
			} else {
				rs1 = sm.executeUpdate(sqlu);
			}
			rs = null;
			sm = null;
		} catch (Exception e) {
			rs1 = 0;
			System.out.println(sqlu);
			e.printStackTrace();
		}
		return rs1;
	}

}
