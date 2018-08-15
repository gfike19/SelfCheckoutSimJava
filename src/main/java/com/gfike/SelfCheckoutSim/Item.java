package com.gfike.SelfCheckoutSim;

import java.util.Date;
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
	
	private int plu;
	
	private boolean fs;
	
	private Date created;

	public Item(String name, float price, boolean pLb, int plu, boolean fs) {
		super();
		
		this.setName(name);
		this.setPrice(price);
		this.setpLb(pLb);
		this.setFs(fs);
		this.created = new Date();
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
	@Column(name="PLU")
	public int getPlu() {
		return plu;
	}

	public void setPlu(int plu) {
		this.plu = plu;
	}
	
	@NotNull
	@Column(name="Foodstamps")
	public boolean isFs() {
		return fs;
	}

	public void setFs(boolean fs) {
		this.fs = fs;
	}
	
	
	@NotNull
	@Column(name="DateAdded")
	public Date getDateCreated() {
		return this.created;
	}
	
	protected void setDateCreated(Date created) {
		this.created = created;
	}
	

}
