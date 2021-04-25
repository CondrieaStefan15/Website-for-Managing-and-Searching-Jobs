package com.psbd.psbd;

import java.sql.CallableStatement;
import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.psbd.psbd.Firme;
import com.psbd.psbd.Domenii;
import com.psbd.psbd.OracleConnection;

import oracle.jdbc.OracleTypes;

@Controller
public class FirmeController {
	
	@RequestMapping(value="/firme")
	public String firme(Model model)
	{
		ArrayList<Firme> listaFirme = selectRecordsFromFirme();
		model.addAttribute("listaFirme", listaFirme);
		
		ArrayList<Domenii> listaDomenii = selectRecordsFromDomenii();
		model.addAttribute("listaDomenii", listaDomenii);
		
		
		return "/firme";
	}
	
	@RequestMapping(value = "/daCeva")
	public String daCeva(){
		return "Ceva ceva";
	}
	
	////========================================================INSERT=====================================
	/*
	@RequestMapping(value = "/insertFirme", method = RequestMethod.POST)
	public String insertFirma(
			@RequestParam(name="numeFirma") String numeFirma, 
			@RequestParam(name="nrTelefon") String nrTelefon,
			@RequestParam(name="email") String email,
			@RequestParam(name="numeDomeniu") String idDomeniu,
			Model model)
	{
		
		
//		System.out.println("\nnumeAvion: "+numeAvion);
//		System.out.println("tipAvion: " + tipAvion);
//		System.out.println("nrLocuri: " + nrLocuri);
//		System.out.println("numeCompanie: " + idCompanie);
		

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		Statement statement = null;
		
		String insertInToFIRME = "INSERT INTO FIRME (id_firma, nume_firma, numar_telefon, email , firme_nume_domeniu, id_domeniu) VALUES (?, ?, ?, ?, (select nume_domeniu from domenii where id_domeniu = ?), ?)";
		String getIdMax = "SELECT NVL(MAX(id_firma),0) FROM FIRME";
			
		
		try {
			dbConnection = OracleConnection.getDBConnection();
			statement = dbConnection.createStatement();
			
			ResultSet resultSet = statement.executeQuery(getIdMax);
			int idMax = resultSet.next() ? resultSet.getInt(1) : 0;       //avem id uri in avioane? daca da, getInt ia coloana 1, daca nu, incepe cu index 0 pt id
			
			
			preparedStatement = dbConnection.prepareStatement(insertInToFIRME);
			
			preparedStatement.setInt(1, idMax+1);
			preparedStatement.setString(2, numeFirma);
			preparedStatement.setInt(3, Integer.parseInt(nrTelefon));
			preparedStatement.setString(4, email);
			preparedStatement.setInt(5, Integer.parseInt(idDomeniu));
			preparedStatement.setInt(6, Integer.parseInt(idDomeniu));
			
			preparedStatement.executeUpdate();
			
			resultSet.close();
			preparedStatement.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "redirect:/firme";
	}
	*/
	//=========================================================INSERT prin PACKAGE=================================================
	
	@RequestMapping(value = "/insertFirme", method = RequestMethod.POST)
	public String insertFirma(
			@RequestParam(name="numeFirma") String numeFirma, 
			@RequestParam(name="nrTelefon") String nrTelefon,
			@RequestParam(name="email") String email,
			@RequestParam(name="numeDomeniu") String idDomeniu,
			Model model)
	{
		
		
//		System.out.println("\nnumeAvion: "+numeAvion);
//		System.out.println("tipAvion: " + tipAvion);
//		System.out.println("nrLocuri: " + nrLocuri);
//		System.out.println("numeCompanie: " + idCompanie);
		

		Connection dbConnection = null;
		Statement statement = null;
		
		String getIdMax = "SELECT NVL(MAX(id_firma),0) FROM FIRME";
			
		
		try {
			dbConnection = OracleConnection.getDBConnection();
			statement = dbConnection.createStatement();
			
			ResultSet resultSet = statement.executeQuery(getIdMax);
			int idMax = resultSet.next() ? resultSet.getInt(1) : 0;       //avem id uri in avioane? daca da, getInt ia coloana 1, daca nu, incepe cu index 0 pt id
			
			
			CallableStatement  cStmt = dbConnection.prepareCall("{call packageinserari.insereazafirme(?,?,?,?,?)}");	//isDomainAlready is Oracle Function
			cStmt.setInt(1, idMax+1);
			cStmt.setString(2, numeFirma);
			cStmt.setInt(3, Integer.parseInt(nrTelefon));
			cStmt.setString(4, email);
			cStmt.setInt(5, Integer.parseInt(idDomeniu));
			cStmt.executeUpdate();
			
		
			resultSet.close();

			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "redirect:/firme";
	}
	
	
	
	////==================================================================DELETE============================================
/*	
	@RequestMapping(value="/deleteFirma")
	public String deleteFirma(
			@RequestParam(name="idFirma") int idFirma
			)
	{
		
		System.out.println("Id firma:" + idFirma);

		
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		
		String deleteFromFirme = "delete from firme where id_firma=?";

			
		
		try {
			dbConnection = OracleConnection.getDBConnection();

			
			preparedStatement = dbConnection.prepareStatement(deleteFromFirme);
			preparedStatement.setInt(1, idFirma);

			
			preparedStatement.executeUpdate();
			preparedStatement.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return "redirect:/firme";
	}
	
	
	*/
	////====================================================DELETE cu PACKAGE=================================
	@RequestMapping(value="/deleteFirma")
	public String deleteFirma(
			@RequestParam(name="idFirma") int idFirma
			)
	{
		
		Connection dbConnection = null;

		try {
			dbConnection = OracleConnection.getDBConnection();
			CallableStatement  cStmt2 = dbConnection.prepareCall("{call packagedelete.deleteFirme(?)}");	//isDomainAlready is Oracle Function
			cStmt2.setInt(1, idFirma);
			cStmt2.executeUpdate();
			

			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return "redirect:/firme";
	}
	
	
	//==============================================================UPDATE-PACKAGE========================================================
	@RequestMapping(value = "/updateFirma", method = RequestMethod.GET)
	public String updateFirma(
			@RequestParam(name="idFirma") String idFirma,
			@RequestParam(name="numeFirma") String numeFirma, 
			@RequestParam(name="nrTelefon") String nrTelefon,
			@RequestParam(name="email") String email,
			@RequestParam(name="numeDomeniu") String idDomeniu,
			Model model)
	{
		
		Connection dbConnection = null;

		try {
			dbConnection = OracleConnection.getDBConnection();
			
			
			CallableStatement  cStmt = dbConnection.prepareCall("{call packageupdate.updateFirme(?,?,?,?,?)}");	//isDomainAlready is Oracle Function
			cStmt.setInt(1, Integer.parseInt(idFirma));
			cStmt.setString(2, numeFirma);
			cStmt.setInt(3, Integer.parseInt(nrTelefon));
			cStmt.setString(4, email);
			cStmt.setInt(5, Integer.parseInt(idDomeniu));
			cStmt.executeUpdate();
	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "redirect:/firme";
	}
	
	
	
	
	
	/*
	private static ArrayList<Firme> selectRecordsFromFirme()
	{
		Connection dbConnection = null;
		Statement statement = null;
		String selectSQL = "SELECT * FROM FIRME";
		ArrayList<Firme> listaFirme = new ArrayList<Firme>();
		
		try {
			dbConnection = OracleConnection.getDBConnection();
			statement = dbConnection.createStatement();
			
			// execute select SQL stetement
			ResultSet resultSet = statement.executeQuery(selectSQL);
			
		
			while(resultSet.next())
			{
				Firme firma = new Firme();
						firma.setIdFirma(resultSet.getInt("id_firma"));
						firma.setNumeFirma(resultSet.getString("nume_firma"));
						firma.setNumarTelefon(resultSet.getInt("numar_telefon"));
						firma.setEmail(resultSet.getString("email"));
						firma.setFirmeNumeDomeniu(resultSet.getString("firme_nume_domeniu"));
						firma.setIdDomeniu(resultSet.getInt("id_domeniu"));
						
				listaFirme.add(firma);
				
			}	
			
			return listaFirme;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaFirme;
	}
	
	
	private static ArrayList<Domenii> selectRecordsFromDomenii()
	{
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		String selectSQL = "SELECT * FROM DOMENII";
		ArrayList<Domenii> listaDomenii = new ArrayList<Domenii>();
		
		try {
			dbConnection = OracleConnection.getDBConnection();
			preparedStatement = dbConnection.prepareStatement(selectSQL);
			
			// execute select SQL stetement
			ResultSet resultSet = preparedStatement.executeQuery();
			
			
			while(resultSet.next())
			{
				Domenii domeniu  = new Domenii();
						 domeniu.setIdDomeniu(resultSet.getInt("id_domeniu"));
						 domeniu.setNumeDomeniu(resultSet.getString("nume_domeniu"));
				listaDomenii.add(domeniu);
			}		

			
			return listaDomenii;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaDomenii;
	}
	*/
	
	//==============================================================LUAM INREGISTRARILE DIN TABELE - PACKAGE-cursori===================================
	private static ArrayList<Domenii> selectRecordsFromDomenii()
	{
		Connection dbConnection = null;
		
		ArrayList<Domenii> listaDomenii = new ArrayList<Domenii>();
		
		try {
			dbConnection = OracleConnection.getDBConnection();
			CallableStatement  cStmt = dbConnection.prepareCall("{call intoarcedate.intoarcetoatedomeniile(?)}");	
			cStmt.registerOutParameter(1, OracleTypes.CURSOR);
			cStmt.executeUpdate();

			ResultSet resultSet= (ResultSet) cStmt.getObject(1) ;

			
			while(resultSet.next())
			{
				
				Domenii domeniu  = new Domenii();
						 domeniu.setIdDomeniu(resultSet.getInt("id_domeniu"));
						 domeniu.setNumeDomeniu(resultSet.getString("nume_domeniu"));
				listaDomenii.add(domeniu);
				
			}		
				

			return listaDomenii;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaDomenii;
	}
	
	
	private static ArrayList<Firme> selectRecordsFromFirme()
	{
		Connection dbConnection = null;
		
		ArrayList<Firme> listaFirme = new ArrayList<Firme>();
		
		try {
			dbConnection = OracleConnection.getDBConnection();
			CallableStatement  cStmt = dbConnection.prepareCall("{call intoarcedate.intoarcetoatefirmele(?)}");	//isDomainAlready is Oracle Function
			cStmt.registerOutParameter(1, OracleTypes.CURSOR);
			cStmt.executeUpdate();
			
			ResultSet resultSet= (ResultSet) cStmt.getObject(1) ;
		
			while(resultSet.next())
			{
				Firme firma = new Firme();
						firma.setIdFirma(resultSet.getInt("id_firma"));
						firma.setNumeFirma(resultSet.getString("nume_firma"));
						firma.setNumarTelefon(resultSet.getInt("numar_telefon"));
						firma.setEmail(resultSet.getString("email"));
						firma.setFirmeNumeDomeniu(resultSet.getString("firme_nume_domeniu"));
						firma.setIdDomeniu(resultSet.getInt("id_domeniu"));
						
				listaFirme.add(firma);
				
			}	
			
			return listaFirme;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaFirme;
	}
	

	

}
