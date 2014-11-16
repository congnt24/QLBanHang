package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class QuyetToan extends Stage implements Initializable{

	@FXML TextField soTien;
	@FXML Label name, conno, alert;
	int tien=0, id;
	public QuyetToan(String id, String name, String conno) {
		FXMLLoader loader=new FXMLLoader();
		loader.setLocation(ClassLoader.getSystemResource("view/quyettoan.fxml"));
		try {
			loader.setController(this);
			setScene(new Scene((Parent) loader.load(), 400, 600));
			setTitle("Quyết toán tiền còn nự nhà cung cấp");
			this.id=Integer.parseInt(id);
			this.name.setText(name);
			this.conno.setText(conno);
			this.soTien.setText(conno);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	@FXML public void keyType(KeyEvent event){
		/*try {
			tien=Integer.parseInt(soTien.getText());
		} catch (Exception e) {
			tien=0;
		}*/
	}
	@FXML public void quyetToan(ActionEvent event){
		if (Integer.parseInt(soTien.getText())!=0 && Integer.parseInt(soTien.getText().trim())<=Integer.parseInt(conno.getText().trim())) {
			Main.mysql.quyetToan(Integer.parseInt(soTien.getText()), id);
			Main.mysql.getNhaCungCapFX(QLController.qlnccList);
			Main.mysql.getQuyetToan(QLController.qlqtList);
			close();
		}else{
			alert.setText("Vui lòng nhập lại số tiền!!");
		}
	}

}
