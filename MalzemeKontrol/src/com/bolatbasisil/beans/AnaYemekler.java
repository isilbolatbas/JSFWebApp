package com.bolatbasisil.beans;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.bolatbasisil.util.DataConnect;

@ManagedBean(name = "anayemekler")
@SessionScoped

public class AnaYemekler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String anaIsmi;
	private String anaOnemli;
	private String anaTuru;
	private String anaYoresi;
	private Long anaId;

	public String getAnaIsmi() {
		return anaIsmi;
	}

	public void setAnaIsmi(String anaIsmi) {
		this.anaIsmi = anaIsmi;
	}

	public String getAnaOnemli() {
		return anaOnemli;
	}

	public void setAnaOnemli(String anaOnemli) {
		this.anaOnemli = anaOnemli;
	}

	public String getAnaTuru() {
		return anaTuru;
	}

	public void setAnaTuru(String anaTuru) {
		this.anaTuru = anaTuru;
	}

	public String getAnaYoresi() {
		return anaYoresi;
	}

	public void setAnaYoresi(String anaYoresi) {
		this.anaYoresi = anaYoresi;
	}

	public Long getAnaId() {
		return anaId;
	}

	public void setAnaId(Long anaId) {
		this.anaId = anaId;
	}

	public String insert() throws SQLException {

		int i = 0;
		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = DataConnect.getConnection();
			System.out.println("baðlandý");
			String add = "INSERT INTO AnaYemek (anaismi, anaonemli, anaturu, anayoresi) VALUES(?,?,?,?)";
			ps = con.prepareStatement(add);

			ps.setString(1, anaIsmi);
			ps.setString(2, anaOnemli);
			ps.setString(3, anaTuru);
			ps.setString(4, anaYoresi);

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

	public List<AnaYemekler> getAnaYemekler() throws ClassNotFoundException, SQLException {

		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = DataConnect.getConnection();
			ps = con.prepareStatement("select * from AnaYemek");

		} catch (SQLException ex) {
			System.out.println("in exec");
			System.out.println(ex.getMessage());
		}
		List<AnaYemekler> anaYemekler = new ArrayList<AnaYemekler>();

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {

			AnaYemekler ana = new AnaYemekler();

			ana.setAnaId(rs.getLong("anaid"));
			ana.setAnaIsmi(rs.getString("anaismi"));
			ana.setAnaOnemli(rs.getString("anaonemli"));
			ana.setAnaYoresi(rs.getString("anayoresi"));
			ana.setAnaTuru(rs.getString("anaturu"));

			anaYemekler.add(ana);

		}
		con.close();
		ps.close();
		rs.close();

		return anaYemekler;

	}

	public void delete(Long anaId) throws SQLException {

		PreparedStatement ps = null;
		Connection con = null;
		try {

			con = DataConnect.getConnection();

			ps = con.prepareStatement("DELETE FROM AnaYemek WHERE anaid=" + anaId);
			int i = ps.executeUpdate();
			if (i > 0) {
				System.out.println("Row deleted successfully");
			}
		} catch (SQLException ex) {
			System.out.println("Delete error -->" + ex.getMessage());

		} finally {
			if (con != null) {
				con.close();
			}
			if (ps != null) {
				ps.close();
			}
		}
	}

	private Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();

	public String duzenle() {
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String field_sl_no = params.get("action");

		PreparedStatement ps;
		Connection con;

		try {
			con = DataConnect.getConnection();
			ps = con.prepareStatement("select * from AnaYemek where anaid=" + field_sl_no);
			ResultSet rs = ps.executeQuery();
			AnaYemekler anaYemekler = new AnaYemekler();
			rs.next();
			anaYemekler.setAnaIsmi(rs.getString("anaismi"));
			anaYemekler.setAnaOnemli(rs.getString("anaonemli"));
			anaYemekler.setAnaYoresi(rs.getString("anayoresi"));
			anaYemekler.setAnaTuru(rs.getString("anaturu"));
			anaYemekler.setAnaId(rs.getLong("anaid"));

			sessionMap.put("editx", anaYemekler);

		} catch (Exception e) {
			System.out.println(e);
		}
		return "/duzenleAnaYemek.xhtml?faces-redirect=true";
	}

	public String update() {

		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		String update_sl_no = params.get("update_sl_no");

		PreparedStatement ps =null;
		Connection con= null;

		try {

			con = DataConnect.getConnection();
			ps = con.prepareStatement(
					"update AnaYemek set anaismi=?, anaonemli=?, anayoresi=?, anaturu=? where Cast(anaid As varchar) =?");
			
			ps.setString(1, anaIsmi);
			ps.setString(2, anaOnemli);
			ps.setString(3, anaYoresi);
			ps.setString(4, anaTuru);
			ps.setString(5, update_sl_no);
			
			ps.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
		return "/anayemekler.xhtml?faces-redirect=true";
	}

}
