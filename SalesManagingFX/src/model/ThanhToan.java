package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ThanhToan {
	private SimpleIntegerProperty id=new SimpleIntegerProperty(); 
	private SimpleStringProperty type=new SimpleStringProperty(); 
	private SimpleStringProperty name=new SimpleStringProperty();
	private SimpleIntegerProperty no=new SimpleIntegerProperty(); 
	private SimpleIntegerProperty datra=new SimpleIntegerProperty(); 
	private SimpleStringProperty date=new SimpleStringProperty(); 
	private SimpleIntegerProperty conno=new SimpleIntegerProperty();
	public int getId() {
		return id.get();
	}
	public String getType() {
		return type.get();
	}
	public String getName() {
		return name.get();
	}
	public int getNo() {
		return no.get();
	}
	public int getDatra() {
		return datra.get();
	}
	public String getDate() {
		return date.get();
	}
	public int getConno() {
		return conno.get();
	}
	public ThanhToan(int id, String type, String name, int no, int datra, String date, int conno) {
		super();
		this.id.setValue(id);
		this.type.setValue(type);
		this.name.setValue(name);
		this.no.setValue(no);
		this.datra.setValue(datra);
		this.date.setValue(date);
		this.conno.setValue(conno);
	} 
	
	
}
