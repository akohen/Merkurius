package fr.kohen.alexandre.framework.engine.parsers;

import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.kohen.alexandre.framework.engine.ai.Reply;
import fr.kohen.alexandre.framework.engine.resources.ResourceManager;



public class RepliesParser {
	private HashMap<Integer, Reply> replies;
	
	public RepliesParser() {
		try{
		      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		      factory.setValidating(false);
		      factory.setNamespaceAware(false);
		      factory.setIgnoringElementContentWhitespace(true);
		      factory.setAttribute(
		    		    "http://java.sun.com/xml/jaxp/properties/schemaLanguage",
		    		    "http://www.w3.org/2001/XMLSchema");
		      DocumentBuilder builder = factory.newDocumentBuilder();
		      Document document = builder.parse( ResourceLoader.getResourceAsStream(ResourceManager.getFile("replies")) );
		      
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
			NodeList dialogList = root.getChildNodes();
			NodeList dialog;
			for(int i=0; i<dialogList.getLength(); i++)
			{
				
				if( dialogList.item(i).getNodeName() == "reply") {
					dialog = dialogList.item(i).getChildNodes();
					Reply reply = new Reply( Integer.decode(getNodeValue(dialog, "id")), getNodeValue(dialog, "text") );
					reply.setStopDialog(getNodeValue(dialog, "stopConversation"));
					reply.setScript(getNodeValue(dialog, "startScript"));
					for(int j=0; j<dialog.getLength(); j++)
					{
						if( dialog.item(j).getNodeName() == "requirements") {
							for(int k=0; k<dialog.item(j).getChildNodes().getLength(); k++) {
								NodeList requirements = dialog.item(j).getChildNodes();
								if( dialog.item(j).getChildNodes().item(k).getNodeName() != "#text" ) {
									String key = requirements.item(k).getNodeName();
									String value = getNodeValue(requirements, requirements.item(k).getNodeName());
									reply.addReq(key, value);
								}
							}
						}	
					}
					replies.put(reply.getId(), reply);
				}
			}			 
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace(System.err);
	     }
	 }
}