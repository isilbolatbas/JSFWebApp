package com.bolatbasisil.beans;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import javax.servlet.http.HttpServlet;


import com.bolatbasisil.util.DataConnect;


@ManagedBean(name="tatlilar")
@SessionScoped
public class Tatlilar extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tatliIsmi;
	private String tatliSekeri;
	private String tatliOnemli;
	private String tatliTuru;
	private String tatliYoresi;
	
	ResultSet rs;
	public String getTatliIsmi() {
		return tatliIsmi;
	}
	public void setTatliIsmi(String tatliIsmi) {
		this.tatliIsmi = tatliIsmi;
	}
	public String getTatliSekeri() {
		return tatliSekeri;
	}
	public void setTatliSekeri(String tatliSekeri) {
		this.tatliSekeri = tatliSekeri;
	}
	public String getTatliOnemli() {
		return tatliOnemli;
	}
	public void setTatliOnemli(String tatliOnemli) {
		this.tatliOnemli = tatliOnemli;
	}
	public String getTatliTuru() {
		return tatliTuru;
	}
	public void setTatliTuru(String tatliTuru) {
		this.tatliTuru = tatliTuru;
	}
	public String getTatliYoresi() {
		return tatliYoresi;
	}
	public void setTatliYoresi(String tatliYoresi) {
		this.tatliYoresi = tatliYoresi;
	}
	
	
	
	public String insert() throws SQLException {

		int i = 0;
		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = DataConnect.getConnection();
			System.out.println("baðlandý");
			String add = "INSERT INTO tatli (tIsmi, tSekeri, tOnemli, tYoresi, tTuru) VALUES(?,?,?,?,?)";
			ps = con.prepareStatement(add);

			ps.setString(1, tatliIsmi);
			ps.setString(2, tatliSekeri);
			ps.setString(3, tatliOnemli);
			ps.setString(4, tatliYoresi);
			ps.setString(5, tatliTuru);

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
	
	

}
