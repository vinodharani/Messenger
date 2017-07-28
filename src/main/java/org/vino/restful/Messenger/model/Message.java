package org.vino.restful.Messenger.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/* JAXB helps converting the response into XML */
/* JAXB is useful when your response produces MediaType.APPLICATION_XML */
@XmlRootElement /* This is to let JAXB know that this the root element */
public class Message {

	private long id;
	private String message;
	private Date created;
	private String author;
	private Map<Long, Comment> comments = new HashMap<Long, Comment>();
	private List<Link> links = new ArrayList<Link>();

	public Message() {
		super();
	}

	public Message(long id, String message, String author) {
		this();
		this.id = id;
		this.message = message;
		this.created = new Date();
		this.author = author;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@XmlTransient /*
					 * Will ignore Comment when a Message instance is converted
					 * to XML or JSON to be sent as a response. Even JSON
					 * conversion will ignore this Comments since it is marked
					 * as XmlTransient
					 */
	public Map<Long, Comment> getComments() {
		return comments;
	}

	public void setComments(Map<Long, Comment> comments) {
		this.comments = comments;
	}

	public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	public void addLink(String url, String rel) {
		Link link = new Link(url, rel);
		this.links.add(link);
	}
}
