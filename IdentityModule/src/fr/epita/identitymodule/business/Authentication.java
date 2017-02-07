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

@WebServlet("/Authentication")
public class Authentication extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Authentication() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JdbcDAO conx = null;
		
		String user = request.getParameter("username");
		String pass = request.getParameter("password");
		
		user=user.trim();
		pass=pass.trim();
		
		String messages = "";
		
		try {
			conx = new JdbcDAO();
			messages = conx.authenticate(user, pass);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
		if(messages.isEmpty()){
			List<Identity> identities = new ArrayList<>();
			try {
				identities = conx.readAllIdentities();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			request.setAttribute("user", user);
			request.setAttribute("identities", identities);
			request.getRequestDispatcher("/IdentityManager.jsp").forward(request, response);
				
		}else{
			request.setAttribute("messages", messages);
			request.getRequestDispatcher("/").forward(request, response);
		}
	}

}
