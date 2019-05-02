package org.cmpe202.drifter.starbucks.model;

import java.util.Date;

import starbucks.* ;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class StarbucksModel {
  private long id;
  private String message;
  private Date created;
  private String author;
  
  public StarbucksModel()
  {
	  
  }
  
  public StarbucksModel(long id, String message, String author) {
	  this.id = id;
	  this.message = message;
	  this.author = author;
	  this.created = new Date();
	  
  }
  
public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getMessag() {
	return message;
}
public void setMessag(String message) {
	this.message = message;
}
public Date getCreated() {
	return created;
}
public void setCreated(Date created1) {
	created = created1;
}
public String getAuthor() {
	return author;
}
public void setAuthor(String author) {
	this.author = author;
}
  
}
