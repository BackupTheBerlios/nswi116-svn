package nswi116.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nswi116.data.Integration;

//how to run: org.mortbay.jetty.Main 80 -webapp webapps/nswi116
//how to access: http://localhost/MeexJsonData?musicstyle=British%20Invasion


@SuppressWarnings("serial")
public class MeexJsonData extends HttpServlet
{
	protected Integration integration;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		
		Integration.convertAndWriteModelToJson(
			Integration.constructModelForPresentation(
				integration.integrateDataForMusicStyle(req.getParameter("musicstyle"))),
				resp.getWriter());
		
	}

	@Override
	public void init() throws ServletException
	{		
		super.init();

		try {
			integration = new Integration();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		org.mortbay.jetty.Main.main(new String [] {"80", "-webapp", "webapps/nswi116"});		
	}
}
