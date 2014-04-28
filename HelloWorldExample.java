import java.io.*;
import java.net.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import org.jdom.*;
import org.json.*;
import org.jdom.input.SAXBuilder;

public class HelloWorldExample extends HttpServlet{

    public void doGet(HttpServletRequest request, HttpServletResponse response) 
    		throws IOException, ServletException
        {response.setContentType("text/json");
        PrintWriter out = response.getWriter();
        String content = request.getParameter("content");
        String type = request.getParameter("type");
		
		String newcontent=content.replace(' ', '+');
		
        String link = "http://cs-server.usc.edu:10434/get_discography1.php?content="+newcontent+"&type="+type;
        URL url = new URL (link);
        URLConnection urlConnection = url.openConnection();
        urlConnection.setAllowUserInteraction(false);
        InputStream urlStream = url.openStream();
        
        String str1 = "artists";
        String str2 = "albums";
        String str3 = "songs";
        SAXBuilder builder = new SAXBuilder(false);
        try{
        	Document doc = (Document) builder.build(urlStream);
        	JSONArray ResultArray = new JSONArray();
        	Element root=doc.getRootElement();
        	List results = root.getChildren();
        	for (int i=0;i<results.size();i++){
        		Element result = (Element) results.get(i);
        		JSONObject JSONResult = new JSONObject();
        		if (type.equals(str1)){
        			String cover = result.getAttributeValue("cover");
        			String name = result.getAttributeValue("name");
        			String genre = result.getAttributeValue("genre");
        			String year = result.getAttributeValue("year");
        			String details = result.getAttributeValue("details");
        			JSONResult.put("cover", cover);
        			JSONResult.put("name", name);
        			JSONResult.put("genre", genre);
        			JSONResult.put("year", year);
        			JSONResult.put("details", details);  
        			ResultArray.put(JSONResult);
        		}
        		if (type.equals(str2)){
        			String cover = result.getAttributeValue("cover");
        			String title = result.getAttributeValue("title");
        			String artist = result.getAttributeValue("artist");
        			String genre = result.getAttributeValue("genre");
        			String year = result.getAttributeValue("year");
        			String details = result.getAttributeValue("details");
        			JSONResult.put("cover", cover);
        			JSONResult.put("title",title);
        			JSONResult.put("artist", artist);
        			JSONResult.put("genre", genre);
        			JSONResult.put("year", year);
        			JSONResult.put("details",details);
        			ResultArray.put(JSONResult);
        		}
        		if (type.equals(str3)){
        			String sample = result.getAttributeValue("sample");
        			String title = result.getAttributeValue("title");
        			String performer = result.getAttributeValue("performer");
        			String composer = result.getAttributeValue("composer");
        			String details = result.getAttributeValue("details");
        			JSONResult.put("sample", sample);
        			JSONResult.put("title",title);
        			JSONResult.put("performer", performer);
        			JSONResult.put("composer", composer);
        			JSONResult.put("details",details);
        			ResultArray.put(JSONResult);
        		}
        		       		
        	}
        	JSONObject JSONResults = new JSONObject(); 
    		JSONResults.put("result", ResultArray);
    		JSONObject JSONOutput = new JSONObject();
    		JSONOutput.put("results", JSONResults);
    		out.println(JSONOutput.toString());
    		out.close();
        }catch (MalformedURLException e) {
   			System.err.println(e);
		} 
        catch (IOException io) {
			System.out.println(io.getMessage());}
        catch (JDOMException jdomerr) {
			String output = "{\"results\":{\"error\":\""+jdomerr.toString()+"\"}}";
			out.println(output);
			out.close();
			return;
	  	}   
        }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException
			{
				doGet(request, response);
			}

}