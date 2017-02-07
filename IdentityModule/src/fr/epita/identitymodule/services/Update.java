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

@WebServlet("/Update")
public class Update extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String uid; 
	
    public Update() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		uid = request.getParameter("uid").toString();
		JdbcDAO conx;
		try {
			conx = new JdbcDAO();
			Identity identity = conx.getIdentity(uid);
			
			request.setAttribute("displayname", identity.getDisplayname());
			request.setAttribute("email", identity.getEmail());
			request.setAttribute("uid", uid);
			request.setAttribute("birthdate", identity.getBirthDate());
			
			request.getRequestDispatcher("/Update.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("displayname").toString();
		String email = request.getParameter("email").toString();
		String birthdate = request.getParameter("birthdate").toString();
		
		user = user.trim();
		email = email.trim();
		birthdate = birthdate.trim();
		
		Identity identity = new Identity(uid,user,email,birthdate);
		
		List<String> messages = validateFields(user,email,birthdate);
		
		if(!messages.isEmpty()){
			request.setAttribute("messages", messages);
			request.setAttribute("uid", uid);
			request.setAttribute("displayname", user);
			request.setAttribute("email", email);
			request.setAttribute("birthdate", birthdate);
			request.getRequestDispatcher("/Update.jsp").forward(request, response);
			
		}else{
		
			try {
				JdbcDAO conx = new JdbcDAO();
				conx.update(identity);
				
				request.setAttribute("messages", "User with uid: " + uid + " was successfuly updated!");
				request.setAttribute("identities", conx.readAllIdentities());
				request.getRequestDispatcher("/IdentityManager.jsp").forward(request, response);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	//Validates the format of the String to match yyyy-mm-dd
	public List<String> validateFields(String user, String email, String birthdate){
		List<String> errors = new ArrayList<>();
		if("".equals(user) || "".equals(email) || "".equals(birthdate)){
			errors.add("All fields must be filled.");
		}
		//validating date format
		if (!birthdate.matches("\\d{4}-\\d{2}-\\d{2}")) { 
		    errors.add("Wrong date format!");
		}
		return errors;
	}
}
