package com.gfike.SelfCheckoutSim.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="Item")
public class Item extends AbstractEntity {
	
	private String name;
	
	private float price;
	
	private boolean pLb;
	
	private String plu;
	
	private boolean fs;
	
//	private Date dateCreated;
	
	public Item () {}
	
	public Item(String name, float price, boolean pLb, String plu, boolean fs) {
		super();
		
		this.setName(name);
		this.setPrice(price);
		this.setpLb(pLb);
		this.setFs(fs);
		this.setPlu(plu);
	}
	

	@NotNull
	@Column(name="Name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull
	@Column(name="Price")
	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
	
	@NotNull
	@Column(name="PricePerPound")
	public boolean ispLb() {
		return pLb;
	}

	public void setpLb(boolean pLb) {
		this.pLb = pLb;
	}
	
	@NotNull
	@Column(name="PLU", length=12)
	public String getPlu() {
		return plu;
	}

	public void setPlu(String plu2) {
		this.plu = plu2;
	}
	
	@NotNull
	@Column(name="Foodstamps")
	public boolean isFs() {
		return fs;
	}

	public void setFs(boolean fs) {
		this.fs = fs;
	}
	
	public String toString(){
		return this.name + " " + this.plu + " " + this.price + " " + this.fs;
	}
//	@PrePersist
//	  public void setDateCreated() {
//	    dateCreated = new Date();
//	  }
//
//	public Date getDateCreated() {
//		return dateCreated;
//	}
//
//	public void setDateCreated(Date dateCreated) {
//		this.dateCreated = dateCreated;
//	}

}
