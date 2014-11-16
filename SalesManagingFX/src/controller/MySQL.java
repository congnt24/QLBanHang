package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import model.Customer;
import model.Nhap;
import model.Producer;
import model.ThanhToan;
import model.Sales;
import model.Xuat;
import controller.QLController.QLKItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import javafx.scene.control.Label;


public class MySQL {
	
	public static final int NHACUNGCAP=1;
	public static final int KHACHHANG=2;
	Date d=new Date();
	SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
	Connection conn;
	static Statement st;
	Date date=new Date(new java.util.Date().getTime());
	public boolean connect(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn=DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/salemanaging?user=root&password=24199412");
			if (conn!=null) {
				System.out.println("Connection Successfully");
				st=conn.createStatement();
				return true;
			}else {
				System.out.println("Connection failed!!!");
				return false;
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("Class.forName Error!!!!");
			return false;
		} catch (SQLException e) {
			System.out.println("SQL Connection Error!!!!");
			return false;
		}
	}
	public void close(){
		
		try {
			if (!conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ObservableList<String> getHangHoaFX(ObservableList<Sales> obsList){
		obsList.removeAll(obsList);
		ObservableList<String> nameList=FXCollections.observableArrayList();
		String sql="select * from hang_hoa;";
		try {
			ResultSet rs=st.executeQuery(sql);
			while (rs.next()) {
				obsList.add(new Sales(rs.getInt("mahanghoa"), rs.getString("tenhanghoa"), rs.getInt("gia"), rs.getString("hangsanxuat")));
				nameList.add(rs.getString("tenhanghoa"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nameList;
	}
	
	
	
	public void getNhaCungCapFX(ObservableList<Producer> list){
		list.removeAll(list);
		try {
			ResultSet rs=st.executeQuery("SELECT * FROM nhacungcap;");
			while (rs.next()) {
				list.add(new Producer(rs.getInt("idnhacungcap"), rs.getString("tennhacungcap"), rs.getInt("conno")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void getKhoFX(ObservableList<QLKItem> list){
		list.removeAll(list);
		String sql="SELECT * FROM kho, hang_hoa WHERE kho.mahanghoa=hang_hoa.mahanghoa ;";
		try {
			ResultSet rs=st.executeQuery(sql);
			while (rs.next()) {
				list.add(new QLKItem(rs.getInt("mahanghoa"), rs.getInt("soluong"), rs.getInt("gia"),
						rs.getString("ngaycapnhat"), rs.getString("tenhanghoa"), rs.getString("hangsanxuat")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void getHoaDonXuat(ObservableList<QLKItem> list){
		String sql="SELECT * FROM kho inner join hang_hoa on"
				+ " kho.mahanghoa=hang_hoa.mahanghoa inner join nhacungcap"
				+ " on hang_hoa.idnhacungcap=nhacungcap.idnhacungcap;";
		try {
			ResultSet rs=st.executeQuery(sql);
			while (rs.next()) {
				list.add(new QLKItem(rs.getInt("maxuatnhap"), rs.getInt("soluong"), rs.getInt("gia"),
						rs.getString("ngaycapnhat"), rs.getString("tenhanghoa"), rs.getString("tennhacungcap")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ObservableList<String> getComboboxNhaCungCap() {
		ObservableList<String> list=FXCollections.observableArrayList();
		try {
			ResultSet rs=st.executeQuery("SELECT * FROM nhacungcap;");
			while (rs.next()) {
				list.add(rs.getString("tennhacungcap"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	
	public ObservableList<Integer> getIDSaleFX(String name) {
		ObservableList<Integer> list=FXCollections.observableArrayList();
		for (Sales sale : QLController.list) {
			if (sale.getName().equals(name)) {
				list.add(sale.getId());
			}
		}
		return list;
	}
	public String storageSale(int id, int amount,String ncc, int sum, int datra) throws SQLException {
		int idncc=0;
		//Tim id cua nha cung cap
		ResultSet rs=st.executeQuery("SELECT idnhacungcap FROM nhacungcap WHERE tennhacungcap=\""+ncc+"\"");
		if (rs.next()) {
			idncc=rs.getInt("idnhacungcap");
		}
		String dt=dateFormat.format(d);
		//Insert vao bang hoa don nhap
		String sql="INSERT INTO hoadonnhap (ngaytra, tongsotien, datra, soluong, mahanghoa, idnhacungcap) VALUES (\""+dt+"\", "+sum+", "+datra+", "+amount+", "+id+", "+idncc+")";
		try {
			st.execute(sql);
			sql="UPDATE nhacungcap SET conno=conno+"+(sum-datra)+" WHERE idnhacungcap="+idncc;
			st.execute(sql);
//			sql="INSERT INTO quyettoan (idnhacungcap, sotien, date) VALUES("+idncc+", "+(sum-datra)+", \""+dt+"\")";
//			st.execute(sql);
			sql="UPDATE kho SET soluong=soluong+"+amount+" WHERE mahanghoa="+id;
			st.execute(sql);
			return "1";
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error Insert to mysql!!!!";
		}
	}
	public int getSaleFX(Label pricesale, Label producersale, int selectedItem) {
		int price=0;
		for (Sales sale : QLController.list) {
			if (sale.getId()==selectedItem) {
				price=sale.getPrice();
				pricesale.setText(price+"");
				producersale.setText(""+sale.getProducer());
				break;
			}
		}
		return price;
	}
	
	public String addSale(String text, int parseInt, String selectedItem) {
		
		String sql="INSERT INTO hang_hoa (hang_hoa.tenhanghoa, hang_hoa.gia, hang_hoa.hangsanxuat) VALUES (\""+text+"\", "+parseInt+", \""+selectedItem+"\");";
		try {
			st.execute(sql);
			sql="SELECT mahanghoa FROM hang_hoa ORDER BY mahanghoa DESC LIMIT 0,1";
			ResultSet rs=st.executeQuery(sql);
			if (rs.next()) {
				sql="INSERT INTO kho (mahanghoa, soluong, ngaycapnhat) VALUES ("+rs.getInt("mahanghoa")+", 0, 2014)";
				st.execute(sql);
			}
		} catch (SQLException e) {
			return "Error insert to mysql!!!!";
		}
		return "1";
	}
	
	public boolean deleteSale(int id){
		String sql="DELETE FROM hang_hoa WHERE mahanghoa="+id;
		try {
			st.execute(sql);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	public String updateSale(int id, String name, int prices, String producer){
		
		String sql="UPDATE hang_hoa SET tenhanghoa=\""+name+"\", gia="+prices+", hangsanxuat=\""+producer+"\" WHERE mahanghoa="+id+";";
		try {
			st.execute(sql);
			return "1";
		} catch (SQLException e) {
			return "Error update to mysql!!!!";
		}
	}
	
	public String insertXuat(int idkh, int idhh,int sl, int sum,  int datra) throws SQLException{
		String dt=dateFormat.format(d);
		String sql="INSERT INTO hoadonxuat (ngaytra, tongsotien, datra, soluong, mahanghoa, idkhachhang) VALUES (\""+dt+"\", "+sum+", "+datra+", "+sl+", "+idhh+", "+idkh+")";
		
		try {
			ResultSet rs=st.executeQuery("SELECT soluong FROM kho WHERE mahanghoa="+idhh);
			rs.next();
			int s=rs.getInt("soluong");
			if (sl>s) {
				return "Kho khong con du hang ("+s+").";
			}
			st.execute(sql);
			sql="UPDATE khachhang SET conno=conno+"+(sum-datra)+" WHERE idkhachhang="+idkh;
			st.execute(sql);
//			sql="INSERT INTO thanhtoan (idnhakhachhang, sotien, date) VALUES("+idkh+", "+(sum-datra)+", \""+dt+"\")";
//			st.execute(sql);
			sql="UPDATE kho SET soluong=soluong-"+sl+" WHERE mahanghoa="+idhh;
			st.execute(sql);
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			//System.out.println("error");
			return "Error Insert to mysql!!!!";
		}
	}
	
	//Custommer
	public String addCustomer(String name, String address){
		String sql="INSERT INTO khachhang (tenkhachhang, address, conno) VALUES (\""+name+"\", \""+address+"\", 0);";
		try{
			st.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error insert to mysql";
		}
		return "1";
	}
	public ObservableList<String> getKhachHangFX(ObservableList<Customer> obsList){
		obsList.removeAll(obsList);
		ObservableList<String> list=FXCollections.observableArrayList();
		String sql="SELECT * FROM khachhang;";
		try {
			ResultSet rs=st.executeQuery(sql);
			while (rs.next()) {
				obsList.add(new Customer(rs.getInt("idkhachhang"), rs.getString("tenkhachhang"), rs.getString("address"), rs.getInt("conno")));
				list.add(rs.getString("tenkhachhang"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public ObservableList<Integer> getIDCustomerFX(String name) {
		ObservableList<Integer> list=FXCollections.observableArrayList();
		for (Customer customer : QLController.qlkhList) {
			if (customer.getName().equals(name)) {
				list.add(customer.getId());
			}
		}
		return list;
	}
	public String getCustomerFX(int value) {
		for (Customer item : QLController.qlkhList) {
			if (item.getId()==value) {
				return item.getAddress();
			}
		}
		return "";
	}
	public String updateCustomer(int id, String name, String add) {
		String sql="UPDATE khachhang SET tenkhachhang=\""+name+"\", address=\""+add+"\"  WHERE idkhachhang="+id+";";
		System.out.println(sql);
		try {
			st.execute(sql);
			return "1";
		} catch (SQLException e) {
			return "Error update Customer to mysql!!!!";
		}
	}
	public boolean deleteCustomer(int id) {
		String sql="DELETE FROM khachhang WHERE idkhachhang="+id;
		try {
			st.execute(sql);
			return true;
		} catch (SQLException e) {
			System.out.println("Forein Key");
			return false;
		}
		
	}
	public String addProducer(String name) {
		String sql="INSERT INTO nhacungcap (tennhacungcap) VALUES (\""+name+"\");";
		try{
			st.execute(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error insert to mysql";
		}
		return "1";
	}
	public String updateProducer(int id, String name) {
		String sql="UPDATE nhacungcap SET tennhacungcap=\""+name+"\" WHERE idnhacungcap="+id+";";
		System.out.println(sql);
		try {
			st.execute(sql);
			return "1";
		} catch (SQLException e) {
			return "Error update Producer to mysql!!!!";
		}
	}
	//Xuat nhap
	public void getXuat(ObservableList<Xuat> obsList) {
		obsList.removeAll(obsList);
		String sql="select * from hoadonxuat,kho, hang_hoa,khachhang WHERE hoadonxuat.idkhachhang=khachhang.idkhachhang AND hoadonxuat.mahanghoa=kho.mahanghoa AND kho.mahanghoa=hang_hoa.mahanghoa;";
		try {
			ResultSet rs=st.executeQuery(sql);
			while (rs.next()) {
				obsList.add(new Xuat(rs.getString("tenkhachhang"), rs.getString("tenhanghoa"), rs.getInt("mahoadonxuat"), rs.getInt("soluong"), rs.getInt("gia"), rs.getInt("tongsotien"), rs.getInt("datra"), rs.getInt("tongsotien")-rs.getInt("datra"), rs.getString("ngaytra")));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void getNhap(ObservableList<Nhap> obsList) {
		obsList.removeAll(obsList);
		String sql="select * from hoadonnhap,kho, hang_hoa,nhacungcap WHERE hoadonnhap.idnhacungcap=nhacungcap.idnhacungcap AND hoadonnhap.mahanghoa=kho.mahanghoa AND kho.mahanghoa=hang_hoa.mahanghoa;";
		try {
			ResultSet rs=st.executeQuery(sql);
			while (rs.next()) {
				obsList.add(new Nhap(rs.getString("tennhacungcap"), rs.getString("tenhanghoa"), rs.getInt("mahoadonnhap"), rs.getInt("soluong"), rs.getInt("gia"), rs.getInt("tongsotien"), rs.getInt("datra"), rs.getInt("tongsotien")-rs.getInt("datra"), rs.getString("ngaytra")));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			
		}
	}
	public void getQuyetToan(ObservableList<ThanhToan> obsList) {
		obsList.removeAll(obsList);
		String sql="select * from nhacungcap, quyettoan WHERE nhacungcap.idnhacungcap=quyettoan.idnhacungcap;";
		try {
			ResultSet rs=st.executeQuery(sql);
			int conno, datra;
			while (rs.next()) {
				conno=rs.getInt("conno");
				datra=rs.getInt("sotien");
				obsList.add(new ThanhToan(rs.getInt("id"), "Nha Cung Cap", rs.getString("tennhacungcap"), conno, datra, rs.getString("date"), conno>datra?conno-datra:0));
			}
			sql="SELECT * FROM khachhang, thanhtoan WHERE khachhang.idkhachhang=thanhtoan.idkhachhang";
			rs=st.executeQuery(sql);
			while (rs.next()) {
				conno=rs.getInt("conno");
				datra=rs.getInt("sotien");
				obsList.add(new ThanhToan(rs.getInt("id"), "Khách hàng", rs.getString("tenkhachhang"), conno, datra, rs.getString("date"), conno>datra?conno-datra:0));
			}
			
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int conno(int i, int id) throws SQLException{
		if (i==NHACUNGCAP) {
			ResultSet rs=st.executeQuery("SELECT conno FROM nhacungcap WHERE id="+id+" LIMIT 1");
			rs.next();
			return rs.getInt("conno");
		}else{
			ResultSet rs=st.executeQuery("SELECT conno FROM khachhang WHERE idkhachhang="+id+" LIMIT 1");
			rs.next();
			return rs.getInt("conno");
		}
	}
	// Khác hàng trả nợ, Trừ nợ vào bảng khách hàng, thêm vào bảng quyết toán
	public void kHTraNo(int tien, int id) {
		String dt=dateFormat.format(d);
		try {
			String sql="UPDATE khachhang SET conno=conno-"+tien+" WHERE idkhachhang="+id;
			st.execute(sql);
			String sql2="INSERT INTO thanhtoan (idkhachhang, sotien, date) VALUES ("+id+", "+tien+", \""+dt+"\")";
			st.execute(sql2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public String getSoLuongTrongKho(int id) {
		try {
			ResultSet rs=st.executeQuery("SELECT soluong FROM kho WHERE mahanghoa="+id);
			rs.next();
			return rs.getInt("soluong")+"";
		} catch (SQLException e) {
		}
		return null;
	}
	public void quyetToan(int tien, int id) {
		String dt=dateFormat.format(d);
		try {
			String sql="UPDATE nhacungcap SET conno=conno-"+tien+" WHERE idnhacungcap="+id;
			st.execute(sql);
			String sql2="INSERT INTO quyettoan (idnhacungcap, sotien, date) VALUES ("+id+", "+tien+", \""+dt+"\")";
			st.execute(sql2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
