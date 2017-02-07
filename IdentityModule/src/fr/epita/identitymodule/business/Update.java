package fr.epita.identitymodule.business;

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
	
		if(!permissionLoggedUser(request)){
			JdbcDAO conx;
			try {
				conx = new JdbcDAO();
				request.setAttribute("errors", "You don't have permissions to modify users!");
				request.setAttribute("identities", conx.readAllIdentities());
				request.getRequestDispatcher("/IdentityManager.jsp").forward(request, response);
				return;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
		
			uid = request.getParameter("uid").toString();
			JdbcDAO conx;
			try {
				conx = new JdbcDAO();
				Identity identity = conx.getIdentity(uid);
				
				request.setAttribute("displayname", identity.getDisplayname());
				request.setAttribute("email", identity.getEmail());
				request.setAttribute("uid", uid);
				request.setAttribute("birthdate", identity.getBirthDate());
				request.setAttribute("password", identity.getPassword());
				request.setAttribute("usertype",identity.getUserType());
				
				request.getRequestDispatcher("/Update.jsp").forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String user = request.getParameter("displayname").toString();
		String email = request.getParameter("email").toString();
		String birthdate = request.getParameter("birthdate").toString();
		String password = request.getParameter("password").toString();
		String usertype = request.getParameter("usertype").toString();
		
		user = user.trim();
		email = email.trim();
		birthdate = birthdate.trim();
		password = password.trim();
		
		Identity identity = new Identity(uid,user,email,birthdate,password,usertype);
		
		List<String> messages = validateFields(user,email,birthdate,password);
		
		if(!messages.isEmpty()){
			request.setAttribute("messages", messages);
			request.setAttribute("uid", uid);
			request.setAttribute("displayname", user);
			request.setAttribute("email", email);
			request.setAttribute("birthdate", birthdate);
			request.setAttribute("usertype", usertype);
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
	public List<String> validateFields(String user, String email, String birthdate, String password){
		List<String> errors = new ArrayList<>();
		if("".equals(user) || "".equals(email) || "".equals(birthdate) || "".equals(password)){
			errors.add("All fields must be filled.");
		}
		//validating date format
		if (!birthdate.matches("\\d{4}-\\d{2}-\\d{2}")) { 
		    errors.add("Wrong date format!");
		}
		return errors;
	}
	
	public boolean permissionLoggedUser(HttpServletRequest request){
		boolean resp = false;
		if("admin".equals(request.getSession().getAttribute("type").toString())){
			resp = true;
		}
		return resp;
	}
}
