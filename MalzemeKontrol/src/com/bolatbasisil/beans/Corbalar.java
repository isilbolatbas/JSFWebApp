package com.bolatbasisil.beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.bolatbasisil.util.DataConnect;

@ManagedBean(name="corbalar")
@SessionScoped

public class Corbalar implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String corbaId;
	public String getCorbaId() {
		return corbaId;
	}
	public void setCorbaId(String corbaId) {
		this.corbaId = corbaId;
	}

	private String corbaIsmi;
	private String corbaTuru;
	private String corbaMalzemesi;
	private String corbaYoresi;
	public String getCorbaIsmi() {
		return corbaIsmi;
	}
	public void setCorbaIsmi(String corbaIsmi) {
		this.corbaIsmi = corbaIsmi;
	}
	public String getCorbaTuru() {
		return corbaTuru;
	}
	public void setCorbaTuru(String corbaTuru) {
		this.corbaTuru = corbaTuru;
	}
	public String getCorbaMalzemesi() {
		return corbaMalzemesi;
	}
	public void setCorbaMalzemesi(String corbaMalzemesi) {
		this.corbaMalzemesi = corbaMalzemesi;
	}
	public String getCorbaYoresi() {
		return corbaYoresi;
	}
	public void setCorbaYoresi(String corbaYoresi) {
		this.corbaYoresi = corbaYoresi;
	}
	
	public List<Corbalar> getCorbalar() throws ClassNotFoundException, SQLException {

		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = DataConnect.getConnection();
			ps = con.prepareStatement("select * from corba");

		} catch (SQLException ex) {
			System.out.println("in exec");
			System.out.println(ex.getMessage());
		}
		List<Corbalar> corbalar = new ArrayList<Corbalar>();

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {

			Corbalar ana = new Corbalar();

			ana.setCorbaId(rs.getString("cid"));
			ana.setCorbaIsmi(rs.getString("cIsmi"));
			ana.setCorbaMalzemesi(rs.getString("cMalzemesi"));
			ana.setCorbaYoresi(rs.getString("cYoresi"));
			ana.setCorbaTuru(rs.getString("cTuru"));

			corbalar.add(ana);

		}
		con.close();
		ps.close();
		rs.close();

		return corbalar;

	}
	
	
	public String insert() throws SQLException {

		int i = 0;
		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = DataConnect.getConnection();
			System.out.println("baðlandý");
			String add = "INSERT INTO corba (cIsmi, cMalzemesi, cYoresi, cTuru) VALUES(?,?,?,?)";
			ps = con.prepareStatement(add);

			ps.setString(1, corbaIsmi);
			ps.setString(2, corbaMalzemesi);
			ps.setString(3, corbaYoresi);
			ps.setString(4, corbaTuru);

			i = ps.executeUpdate();
			if (i > 0)
				System.out.println("succes");
			else
				System.out.println("not");

			System.out.println("data added successfully");

		} catch (SQLException ex) {
			System.out.println("Insert error -->" + ex.getMessage());
			return "Eklenemedi";
		} finally {
			if (con != null) {
				con.close();
			}
			if (ps != null) {
				ps.close();
			}

			if (i > 0) {
				System.out.println("ekleme basarili");
			} else {
				System.out.println("kayit yollamasi basarisiz");
			}
		}
		return "Eklendi";

	}
	public String listele() {
		return "/corbalar.xhtml?faces-redirect=true";
	}
	
	

}
