

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Transaction;

/**
 * Servlet implementation class CadastraServlet
 */
@WebServlet(
	    name = "CadastraServlet",
	    urlPatterns = {"/cadastro"}
	)
public class CadastraServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CadastraServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		NamespaceManager.set("ClienteX");
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		/*response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		
		String post = request.getParameter("postagem");
		Entity postagem = new Entity("Postagem");
		postagem.setProperty("Titulo",post);
		postagem.setProperty("hireDate", new Date());
		postagem.setProperty("IP", request.getRemoteAddr());
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(postagem); 
		response.getWriter().print("Postagem realizada com sucesso!\r\n");*/
		
		NamespaceManager.set("ClienteX"); 
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Transaction txn = (Transaction) datastore.beginTransaction();
		
		try 
		{
			String post = request.getParameter("postagem");
			String id = request.getParameter("id");
			Entity postagem = new Entity("Postagem",id);
			postagem.setProperty("Titulo",post);
			postagem.setProperty("hireDate", new Date());
			postagem.setProperty("IP", request.getRemoteAddr());
			datastore.put(postagem);
			((com.google.appengine.api.datastore.Transaction) txn).commit();
			response.getWriter().print("Postagem realizada com sucesso!\r\n");
		}
		finally 
		{
			if (((com.google.appengine.api.datastore.Transaction) txn).isActive())
			{
				((com.google.appengine.api.datastore.Transaction) txn).rollback();
				response.getWriter().print("Postagem nao realizada com sucesso!\r\n");
			}
		}
		
	}

}
