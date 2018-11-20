

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.urlfetch.FetchOptions.Builder;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

/**
 * Servlet implementation class SyncUrlFetchServlet
 */
@WebServlet(
		 name = "SyncUrlFetchServlet",
		 urlPatterns = {"/news"}
		 )
public class SyncUrlFetchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SyncUrlFetchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// Basics
		/*URLFetchService urlfetch = 
		URLFetchServiceFactory.getURLFetchService();
		URL url = new URL("http://servicos.cptec.inpe.br/XML/cidade/235/previsao.xml");
		
		//HTTPResponse httpResponse = urlfetch.fetch(url);
		//response.getOutputStream().write(httpResponse.getContent());
		
		HTTPRequest httpRequest = new HTTPRequest(url, HTTPMethod.GET, Builder.withDeadline(0.5).doNotFollowRedirects().allowTruncate());
		
		HTTPResponse httpResponse = urlfetch.fetch(httpRequest);
		
		response.getOutputStream().	write(httpResponse.getContent());*/
		
		// Usando Serviço
		/*URLFetchService urlfetch = URLFetchServiceFactory.getURLFetchService();
		URL url = new URL("http://tinyurl.com/create.php");
		HTTPRequest httpRequest = new HTTPRequest(url, HTTPMethod.POST);
		
		httpRequest.setPayload(("url=" + URLEncoder.encode("http://servicos.cptec.inpe.br/XML/cidade/235/previsao.xml", "UTF-8")).getBytes());
		
		//No caso de vários parâmetros use “par=val1&par2=val2”
		HTTPResponse httpResponse = urlfetch.fetch(httpRequest);
		response.getOutputStream().write(httpResponse.getContent());*/
		
		// Usando Memory Cache
		MemcacheService cache = MemcacheServiceFactory.getMemcacheService();
		String situacao = (String) cache.get("situacaoNatal");
		
		if (situacao == null)
		{
			URLFetchService urlfetch = URLFetchServiceFactory.getURLFetchService();
			URL url = new URL("http://servicos.cptec.inpe.br/XML/estacao/SBNT/condicoesAtuais.xml ");
			
			HTTPRequest httpRequest = new HTTPRequest(url, HTTPMethod.GET);
			HTTPResponse httpResponse = urlfetch.fetch(httpRequest);
			
			situacao = new String(httpResponse.getContent(), "UTF-8");
			cache.put("situacaoNatal", situacao, 
			Expiration.byDeltaSeconds(3600));
		} 
		
		response.getWriter().write("Retorno do tempo: "+situacao);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
