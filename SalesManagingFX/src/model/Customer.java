package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {
	private SimpleIntegerProperty id=new SimpleIntegerProperty();
	private SimpleStringProperty name=new SimpleStringProperty();
	private SimpleStringProperty address=new SimpleStringProperty();
	private SimpleIntegerProperty conno=new SimpleIntegerProperty();
	public int getId() {
		return id.get();
	}
	public String getName() {
		return name.get();
	}
	public String getAddress() {
		return address.get();
	}
	public int getConno(){
		return conno.get();
	}
	public Customer(int id, String name, String address, int conno) {
		this.id.setValue(id);
		this.name.setValue(name);
		this.address.setValue(address);
		this.conno.setValue(conno);
	}
}
