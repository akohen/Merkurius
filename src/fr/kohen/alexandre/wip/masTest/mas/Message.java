package fr.kohen.alexandre.wip.masTest.mas;

public class Message {
	private String performative;
	private String sender;
	private String receiver;
	private String replyTo;
	private String content;
	private String ontology;
	private String encoding;
	private String protocol;
	private String conversationId;
	private String replyWith;
	private String inReplyTo;
	private String replyBy;
	
	public String getPerformative() {
		return performative;
	}
	public void setPerformative(String performative) {
		this.performative = performative;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getReplyTo() {
		return replyTo;
	}
	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getOntology() {
		return ontology;
	}
	public void setOntology(String ontology) {
		this.ontology = ontology;
	}
	public String getEncoding() {
		return encoding;
	}
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getConversationId() {
		return conversationId;
	}
	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}
	public String getReplyWith() {
		return replyWith;
	}
	public void setReplyWith(String replyWith) {
		this.replyWith = replyWith;
	}
	public String getInReplyTo() {
		return inReplyTo;
	}
	public void setInReplyTo(String inReplyTo) {
		this.inReplyTo = inReplyTo;
	}
	public String getReplyBy() {
		return replyBy;
	}
	public void setReplyBy(String replyBy) {
		this.replyBy = replyBy;
	}
	
	/**
	 * Creates a new Message from a Json file
	 * @param json
	 * @return a new message from the Json file
	 */
	static public Message fromJson(String json) {
		return new Message();
	}
	
	/**
	 * Returns the Json string corresponding to this message
	 * @return Json
	 */
	public String toJson() {
		return new String();
	}
}
