package nswi116.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nswi116.helloworld.SQLSelectMBrainz;

public class HalloWorld extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		PrintWriter out = resp.getWriter();
		out.println("Hello!");		
		out.println(req.getRemoteHost());
		out.println("This is nswi116...");		
		out.println(req.getQueryString());
		
		
		try {
			SQLSelectMBrainz.printHallo(out);
		} catch (SQLException e) {
			e.printStackTrace(out);
		}
		out.println("... done");		
	}
	
	
}
