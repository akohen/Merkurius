package fr.kohen.alexandre.framework.engine.parsers;

import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.kohen.alexandre.framework.engine.ai.Reply;




public class EventsParser {
	private HashMap<Integer, Reply> replies;
	
	public EventsParser() {
		try{
		      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		      factory.setValidating(false);
		      factory.setNamespaceAware(false);
		      factory.setIgnoringElementContentWhitespace(true);
		      factory.setAttribute(
		    		    "http://java.sun.com/xml/jaxp/properties/schemaLanguage",
		    		    "http://www.w3.org/2001/XMLSchema");
		      DocumentBuilder builder = factory.newDocumentBuilder();
		      Document document = builder.parse( ResourceLoader.getResourceAsStream("data/events.xml") );
		      
		      replies = new HashMap<Integer, Reply>();
              processTranscript(document.getFirstChild());
		} catch(Exception e){ e.printStackTrace(System.err); }//end catch
	}
	
	public HashMap<Integer, Reply> getReplies() {
		return replies;
	}
	
	Node findNode(NodeList nodes, String nodeName)
	 {
		 for(int i=0; i<nodes.getLength(); i++)
		 {
			 if(nodes.item(i).getNodeName().equals(nodeName))
			 {
				 return nodes.item(i);
			 }
		 }
		 return null;
	 }
	 
	 String getNodeValue(NodeList nodes, String nodeName)
	 {
		 Node result = findNode(nodes, nodeName);
		 if(result != null)
		 {
			 return result.getFirstChild().getNodeValue();
		 }
		 return null;
	 }
	 
	void processTranscript(Node root)
	{
		try
		{
			NodeList eventList = root.getChildNodes();
			//NodeList event;
			NamedNodeMap attributes;
			
			for(int i=0; i<eventList.getLength(); i++)
			{
				
				attributes = eventList.item(i).getAttributes();
				if( eventList.item(i).getNodeName().equalsIgnoreCase("event") )
					System.out.println("test: " + attributes.getNamedItem("name").getNodeValue() );

			}			 
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace(System.err);
	     }
	 }
	
	
	public static void main(String[] argv) {
		new EventsParser();

	}
}