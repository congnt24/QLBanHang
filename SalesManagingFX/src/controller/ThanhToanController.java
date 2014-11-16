package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ThanhToanController implements Initializable {

	@FXML ComboBox<String> loai, doituong;
	@FXML ComboBox<Integer> id;
	@FXML TextField sotien;
	@FXML Label conno;
	ObservableList<String> obs, obs2;
	int flag=1;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		obs=FXCollections.observableArrayList();
		obs.add("Nha Cung Cap");
		obs.add("Khach Hang");
		loai.setItems(obs);
		loai.getSelectionModel().selectedItemProperty().addListener(new loaiListener(doituong));
		doituong.getSelectionModel().selectedItemProperty().addListener(new doituongListener(id));
		id.getSelectionModel().selectedItemProperty().addListener(new idListener());
	}
	@FXML public void thanhtoan(){
		
	}
	private class loaiListener implements ChangeListener<String>{
		ComboBox<String> iBox;
		public loaiListener(ComboBox<String> iBox) {
			this.iBox=iBox;
		}
		@Override
		public void changed(ObservableValue<? extends String> arg0,
				String arg1, String arg2) {
			if (arg2.equals("Nha Cung Cap")) {
				this.iBox.setItems(Main.mysql.getComboboxNhaCungCap());
				 flag=1;
			}else {
				flag=2;
				this.iBox.setItems(Main.mysql.getKhachHangFX(FXCollections.observableArrayList()));
			}
		}
		
	}
	private class doituongListener implements ChangeListener<String>{
		ComboBox<Integer> iBox;
		public doituongListener(ComboBox<Integer> iBox) {
			this.iBox=iBox;
		}
		@Override
		public void changed(ObservableValue<? extends String> arg0,
				String arg1, String arg2) {
			if (flag==2) {
				this.iBox.setItems(Main.mysql.getIDCustomerFX(arg2));
			}
		}
		
	}
	public class idListener implements ChangeListener<Integer>{

		@Override
		public void changed(ObservableValue<? extends Integer> arg0,
				Integer arg1, Integer arg2) {
			try {
				conno.setText(Main.mysql.conno(flag, arg2)+"");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
