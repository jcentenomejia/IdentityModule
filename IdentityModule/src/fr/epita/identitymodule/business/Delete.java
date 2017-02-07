package fr.epita.identitymodule.business;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.epita.identitymodule.services.JdbcDAO;

@WebServlet("/Delete")
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Delete() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uid = request.getParameter("uid").toString();
		JdbcDAO conx;
		
		if(!permissionLoggedUser(request)){
			try {
				conx = new JdbcDAO();
				request.setAttribute("errors", "You don't have permissions to delete users!");
				request.setAttribute("identities", conx.readAllIdentities());
				request.getRequestDispatcher("/IdentityManager.jsp").forward(request, response);
				return;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			try {
				conx = new JdbcDAO();
				conx.delete(uid);
				
				request.setAttribute("messages", "User with uid: " + uid + " was deleted!");
				request.setAttribute("identities", conx.readAllIdentities());
				request.getRequestDispatcher("/IdentityManager.jsp").forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean permissionLoggedUser(HttpServletRequest request){
		boolean resp = false;
		if("admin".equals(request.getSession().getAttribute("type").toString())){
			resp = true;
		}
		return resp;
	}
}
