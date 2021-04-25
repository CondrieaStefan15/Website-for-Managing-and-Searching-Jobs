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


import com.psbd.psbd.OracleConnection;

import oracle.jdbc.OracleTypes;

import com.psbd.psbd.Locatii;


@Controller
public class LocatiiController {
	
	@RequestMapping(value="/locatii")
	public String locatii(Model model)
	{
		ArrayList<Locatii> listaLocatii = selectRecordsFromLocatii();
		model.addAttribute("listaLocatii", listaLocatii);
		
		
		return "/locatii";
	}
	

	//========================================INSERARE=====================================
	/*
	@RequestMapping(value = "/insertLocation", method = RequestMethod.POST)
	public String insertLocatie(
			@RequestParam(name="judet") String judet, 
			@RequestParam(name="numeLocatie") String numeLocatie,
			@RequestParam(name="adresa") String adresa,
			Model model)
	{
		
		
//		System.out.println("\nnumeAvion: "+numeAvion);
//		System.out.println("tipAvion: " + tipAvion);
//		System.out.println("nrLocuri: " + nrLocuri);
//		System.out.println("numeCompanie: " + idCompanie);
		

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		Statement statement = null;
		
		String insertInToLOCATII = "INSERT INTO LOCATII (id_locatie, judet, nume_locatie, adresa) VALUES (?, ?, ?, ?)";
		String getIdMax = "SELECT NVL(MAX(id_locatie),0) FROM LOCATII";
			
		
		try {
			dbConnection = OracleConnection.getDBConnection();
			statement = dbConnection.createStatement();
			
			ResultSet resultSet = statement.executeQuery(getIdMax);
			int idMax = resultSet.next() ? resultSet.getInt(1) : 0;       //avem id uri in avioane? daca da, getInt ia coloana 1, daca nu, incepe cu index 0 pt id
			
			
			preparedStatement = dbConnection.prepareStatement(insertInToLOCATII);
			
			preparedStatement.setInt(1, idMax+1);
			preparedStatement.setString(2, judet);
			preparedStatement.setString(3, numeLocatie);
			preparedStatement.setString(4, adresa);
			
			preparedStatement.executeUpdate();
			
			resultSet.close();
			preparedStatement.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "redirect:/locatii";
	}
	*/
	//===================================================INSERARE CU PACKAGE===================================
	@RequestMapping(value = "/insertLocation", method = RequestMethod.POST)
	public String insertLocatie(
			@RequestParam(name="judet") String judet, 
			@RequestParam(name="numeLocatie") String numeLocatie,
			@RequestParam(name="adresa") String adresa,
			Model model)
	{
		
		
//		System.out.println("\nnumeAvion: "+numeAvion);
//		System.out.println("tipAvion: " + tipAvion);
//		System.out.println("nrLocuri: " + nrLocuri);
//		System.out.println("numeCompanie: " + idCompanie);
		

		Connection dbConnection = null;
		Statement statement = null;
		
		String getIdMax = "SELECT NVL(MAX(id_locatie),0) FROM LOCATII";
			
		
		try {
			dbConnection = OracleConnection.getDBConnection();
			statement = dbConnection.createStatement();
			
			ResultSet resultSet = statement.executeQuery(getIdMax);
			int idMax = resultSet.next() ? resultSet.getInt(1) : 0;       //avem id uri in avioane? daca da, getInt ia coloana 1, daca nu, incepe cu index 0 pt id
			
			CallableStatement  cStmt = dbConnection.prepareCall("{call packageinserari.insereazalocatii(?,?,?,?)}");	//isDomainAlready is Oracle Function
			cStmt.setInt(1, idMax+1);
			cStmt.setString(2, judet);
			cStmt.setString(3, numeLocatie);
			cStmt.setString(4, adresa);
			cStmt.executeUpdate();
			
			
			
			resultSet.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "redirect:/locatii";
	}
	
	//=========================================================DELETE=========================
	/*
	@RequestMapping(value="/deleteLocatie")
	public String deleteLocatie(
			@RequestParam(name="idLocatie") int idLocatie
			)
	{
		
		System.out.println("idLocatie:" + idLocatie);

		
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		
		String deleteFromLocatii = "delete from locatii where id_locatie=?";

			
		
		try {
			dbConnection = OracleConnection.getDBConnection();

			
			preparedStatement = dbConnection.prepareStatement(deleteFromLocatii);
			preparedStatement.setInt(1, idLocatie);

			
			preparedStatement.executeUpdate();
			preparedStatement.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return "redirect:/locatii";
	}
	*/
	//////============================================DELETE cu Package=======================
	@RequestMapping(value="/deleteLocatie")
	public String deleteLocatie(
			@RequestParam(name="idLocatie") int idLocatie
			)
	{
		Connection dbConnection = null;

			
		
		try {
			dbConnection = OracleConnection.getDBConnection();
			CallableStatement  cStmt2 = dbConnection.prepareCall("{call packagedelete.deleteLocatii(?)}");	//isDomainAlready is Oracle Function
			cStmt2.setInt(1, idLocatie);
			cStmt2.executeUpdate();
			

			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return "redirect:/locatii";
	}
	
	
	//===========================================================UPDATE-PACKAGE========================================
	@RequestMapping(value = "/updateLocatie", method = RequestMethod.GET)
	public String updateLocatie(
			@RequestParam(name="idLocatie") String idLocatie,
			@RequestParam(name="judet") String judet, 
			@RequestParam(name="numeLocatie") String numeLocatie,
			@RequestParam(name="adresa") String adresa,
			Model model)
	{
		Connection dbConnection = null;		
		
		try {
			dbConnection = OracleConnection.getDBConnection();
			
			CallableStatement  cStmt = dbConnection.prepareCall("{call packageupdate.updateLocatii(?,?,?,?)}");	//isDomainAlready is Oracle Function
			cStmt.setInt(1, Integer.parseInt(idLocatie));
			cStmt.setString(2, judet);
			cStmt.setString(3, numeLocatie);
			cStmt.setString(4, adresa);
			cStmt.executeUpdate();

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "redirect:/locatii";
	}
	
	
	
	
	
	
	
	/*
	
	
	private static ArrayList<Locatii> selectRecordsFromLocatii()
	{
		Connection dbConnection = null;
		Statement statement = null;
		String selectSQL = "SELECT * FROM LOCATII";
		ArrayList<Locatii> listaLocatii = new ArrayList<Locatii>();
		
		try {
			dbConnection = OracleConnection.getDBConnection();
			statement = dbConnection.createStatement();
			
			// execute select SQL stetement
			ResultSet resultSet = statement.executeQuery(selectSQL);
			
		
			while(resultSet.next())
			{
				Locatii locatie = new Locatii();
						locatie.setIdLocatie(resultSet.getInt("id_locatie"));
						locatie.setJudet(resultSet.getString("judet"));
						locatie.setNumeLocatie(resultSet.getString("nume_locatie"));
						locatie.setAdresa(resultSet.getString("adresa"));
						
				listaLocatii.add(locatie);
				
			}	
			
			return listaLocatii;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaLocatii;
	}
	*/
	
	////////////////////=============================luam inregistarrile din tabel cu Package============================
	
	private static ArrayList<Locatii> selectRecordsFromLocatii()
	{
		Connection dbConnection = null;
	
		ArrayList<Locatii> listaLocatii = new ArrayList<Locatii>();
		
		try {
			dbConnection = OracleConnection.getDBConnection();
			CallableStatement  cStmt = dbConnection.prepareCall("{call intoarcedate.intoarcetoatelocatiile(?)}");	//isDomainAlready is Oracle Function
			cStmt.registerOutParameter(1, OracleTypes.CURSOR);
			cStmt.executeUpdate();
			
			ResultSet resultSet= (ResultSet) cStmt.getObject(1) ;
			
		
			while(resultSet.next())
			{
				Locatii locatie = new Locatii();
						locatie.setIdLocatie(resultSet.getInt("id_locatie"));
						locatie.setJudet(resultSet.getString("judet"));
						locatie.setNumeLocatie(resultSet.getString("nume_locatie"));
						locatie.setAdresa(resultSet.getString("adresa"));
						
				listaLocatii.add(locatie);
				
			}	
			
			return listaLocatii;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaLocatii;
	}
	
	
}
