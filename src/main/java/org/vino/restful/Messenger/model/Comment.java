package org.vino.restful.Messenger.model;

import java.util.Date;

public class Comment {

	private long id;
	private String commentContent;
	private Date created;
	private String author;

	public Comment() {
	}

	public Comment(long id, String commentContent, String author) {
		this.id = id;
		this.commentContent = commentContent;
		this.created = new Date();
		this.author = author;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
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
}
