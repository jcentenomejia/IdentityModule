package fr.epita.identitymodule.services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.epita.identitymodule.models.Identity;
import fr.epita.identitymodule.services.JdbcDAO;

@WebServlet("/AddIdentity")
public class AddIdentity extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AddIdentity() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JdbcDAO conx;
		
		List<String> messages;
		
		String displayname = request.getParameter("displayname");
		String email = request.getParameter("email");
		String bdate = request.getParameter("birthdate");
		
		displayname=displayname.trim();
		email=email.trim();
		bdate=bdate.trim();
		
		messages = validateFields(displayname,email,bdate);
		
		if(!messages.isEmpty()){
			request.setAttribute("displayname", displayname);
			request.setAttribute("email", email);
			request.setAttribute("birthdate", bdate);
			request.setAttribute("messages", messages);
			request.getRequestDispatcher("/NewIdentity.jsp").forward(request, response);
		}else{
		
			Identity identity = new Identity("1",displayname,email,bdate);
			
			try {
				conx = new JdbcDAO();
				conx.write(identity);
				
				request.setAttribute("messages", "User created successfuly!");
				request.setAttribute("identities", conx.readAllIdentities());
				request.getRequestDispatcher("/IdentityManager.jsp").forward(request, response);
				
			} catch (SQLException e) {
				
				e.printStackTrace();
				
			}
		}
	}

	public List<String> validateFields(String displayname, String email, String birthdate){
		List<String> messages = new ArrayList<>();
		
		if("".equals(displayname)){
			messages.add("Display name cannot be empty!");
		}
		if("".equals(email)){
			messages.add("Email cannot be empty!");
		}
		if("".equals(birthdate)){
			messages.add("Birth date cannot be empty!");
		}else{
			//validating date format
			if (!birthdate.matches("\\d{4}-\\d{2}-\\d{2}")) { 
			    messages.add("Wrong date format!");
			}
		}
		
		return messages;
	}
}
