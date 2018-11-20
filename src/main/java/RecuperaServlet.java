

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.BaseDatastoreService;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;

/**
 * Servlet implementation class RecuperaServlet
 */
@WebServlet(
		 name = "RecuperaServlet",
		 urlPatterns = {"/consulta"}
		 )
public class RecuperaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecuperaServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		NamespaceManager.set("ClienteX");
		
		//String id = request.getParameter("id");
		
		
		DatastoreService datastoreService = 
		DatastoreServiceFactory.getDatastoreService();
		
		//com.google.appengine.api.datastore.Key k2 = KeyFactory.createKey("Postagem", id);
		
		//Entity postagens = null;
		
		Query q = new Query("Postagem"); 
		PreparedQuery pq = datastoreService.prepare(q);  
		
		int count = 1;
		
		for (Entity result : pq.asIterable())
		{   
		    String titulo = (String) result.getProperty("Titulo");   
			response.getWriter().write("Postagem " + count + ": " + titulo + "<br>");
		    count++;
		}
		    
		/*try 
		{
			postagens = datastoreService.get(k2);
		} 
		catch (EntityNotFoundException e) 
		{
			throw new ServletException(e);
		}
		
		response.getWriter().write(postagens.getProperty("Titulo").toString());*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		NamespaceManager.set("ClienteX");
		doGet(request, response);
	}

}
