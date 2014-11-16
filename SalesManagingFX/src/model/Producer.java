package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Producer {
	private SimpleIntegerProperty id=new SimpleIntegerProperty();
	private SimpleStringProperty name=new SimpleStringProperty();
	private SimpleIntegerProperty conno=new SimpleIntegerProperty();
	public int getId() {
		return id.get();
	}
	public String getName() {
		return name.get();
	}
	public int getConno(){
		return conno.get();
	}
	public Producer(int id, String name, int conno) {
		this.id.setValue(id);
		this.name.setValue(name);
		this.conno.setValue(conno);
	}
}
