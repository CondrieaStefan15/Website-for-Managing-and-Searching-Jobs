package com.psbd.psbd;


import java.sql.CallableStatement;
import java.sql.Connection;
//import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.psbd.psbd.Domenii;
import com.psbd.psbd.OracleConnection;

import oracle.jdbc.OracleTypes;

@Controller
public class DomeniiController {
	
	@RequestMapping(value="/domenii", method = RequestMethod.GET)
	public String companii_aeriene(Model model)
	{
	
		//insert(100);
		System.out.println("\nse apeleaza domenii \n");
		ArrayList<Domenii> listaDomenii = selectRecordsFromDomenii();
		model.addAttribute("listaDomenii", listaDomenii);

		return "/domenii";
	}
	
	
	public static void insert(int n)	//functie care apeleaza o procedura oracle, creata in scopul de a testa baza de date
										// la introducerea unuo numar mare de inregistrari in tabela
	{
		Connection dbConnection = null;
		
		dbConnection = OracleConnection.getDBConnection();
		try {
			CallableStatement  cStmt = dbConnection.prepareCall("{call insert_n_records("+ n +")}");
			cStmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//=================================================================================INSERT cu INSERT==========================================================
	/*@RequestMapping(value = "/insertDomain", method = RequestMethod.POST)
	public String insertDomeniu(                                                               //insertCompanie ------------------------------
			@RequestParam(name="numeDomeniu") String numeDomeniu, 
			Model model)
	{
		
	
		
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		Statement statement = null;
		
		String insertInToDOMENII = "INSERT INTO DOMENII (id_domeniu, nume_domeniu) VALUES (?, ?)";
		String getIdMax = "SELECT NVL(MAX(id_domeniu),0) FROM DOMENII";
		//insert(2); ---------------> merge inserarea mai multor domenii
			
			
		try {
			dbConnection = OracleConnection.getDBConnection();
			
			CallableStatement  cStmt = dbConnection.prepareCall("{? = call isDomainAlready(?)}");	//isDomainAlready is Oracle Function
			cStmt.registerOutParameter(1, Types.NUMERIC);
			cStmt.setString(2, numeDomeniu);
			
			cStmt.executeUpdate();
			int nrDeDomenii = cStmt.getInt(1);
			
			if(nrDeDomenii == 0)	//daca nu mai este nici un domeniu cu acelasi nume
			{
				statement = dbConnection.createStatement();
				preparedStatement = dbConnection.prepareStatement(insertInToDOMENII);
				
				ResultSet resultSet = statement.executeQuery(getIdMax);
				int idMax = resultSet.next() ? resultSet.getInt(1) : 0;
				
				preparedStatement.setInt(1, idMax+1);
				preparedStatement.setString(2, numeDomeniu);
				
				preparedStatement.executeUpdate();
				
				resultSet.close();
				preparedStatement.close();
			}
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "redirect:/domenii";
	}
	*/
	//=====================================================================INSERT cu Procedura din packet==========================================
	
	@RequestMapping(value = "/insertDomain", method = RequestMethod.POST)
	public String insertDomeniu(                                                               //insertCompanie ------------------------------
			@RequestParam(name="numeDomeniu") String numeDomeniu, 
			Model model)
	{
		
		
		Connection dbConnection = null;
		Statement statement = null;	
		String getIdMax = "SELECT NVL(MAX(id_domeniu),0) FROM DOMENII";
		
			
			
		try {
			dbConnection = OracleConnection.getDBConnection();
			
			
			CallableStatement  cStmt = dbConnection.prepareCall("{? = call isDomainAlready(?)}");	//isDomainAlready functie
			cStmt.registerOutParameter(1, Types.NUMERIC);
			cStmt.setString(2, numeDomeniu);
			cStmt.executeUpdate();
			int nrDeDomenii = cStmt.getInt(1);
			
			if(nrDeDomenii == 0)	//daca nu mai este nici un domeniu cu acelasi nume
			{
				System.out.println(nrDeDomenii);

				
				statement = dbConnection.createStatement();
				
				ResultSet resultSet = statement.executeQuery(getIdMax);
				int idMax = resultSet.next() ? resultSet.getInt(1) : 0;
				System.out.println(idMax);
				
				CallableStatement  cStmt2 = dbConnection.prepareCall("{call packageinserari.insereazadomenii(?,?)}");	//isDomainAlready is Oracle Function
				cStmt2.setInt(1, idMax+1);
				System.out.println(idMax+1);
				cStmt2.setString(2, numeDomeniu);
				cStmt2.executeUpdate();
				

				
				resultSet.close();

			}
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "redirect:/domenii";
	}
	
	
	
	
	
	
	
	
	
	
	//===========================================================Delete==========================================
	/*
	@RequestMapping(value="/deleteDomain")
	public String deleteDomeniu(
			@RequestParam(name="idDomeniu") int idDomeniu
			)
	{
		
		System.out.println("Id Meserie:" + idDomeniu);

		
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		
		String deleteFromDomenii = "delete from domenii where id_domeniu=?";

			
		
		try {
			dbConnection = OracleConnection.getDBConnection();

			
			preparedStatement = dbConnection.prepareStatement(deleteFromDomenii);
			preparedStatement.setInt(1, idDomeniu);

			
			preparedStatement.executeUpdate();
			preparedStatement.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return "redirect:/domenii";
	}
	
	*/

	
	//================================================================================sf delete=========================================
	
	
	
	
	//==================================================================Delete cu procedura===============================================
	
	@RequestMapping(value="/deleteDomain")
	public String deleteDomeniu(
			@RequestParam(name="idDomeniu") int idDomeniu
			)
	{
			
		Connection dbConnection = null;			
		
		try {
			dbConnection = OracleConnection.getDBConnection();

			
			CallableStatement  cStmt2 = dbConnection.prepareCall("{call packagedelete.deletedomenii(?)}");	//isDomainAlready is Oracle Function
			cStmt2.setInt(1, idDomeniu);
			cStmt2.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return "redirect:/domenii";
	}
	
	//==========================================================UPDATE_PACKAGE===================================
	@RequestMapping(value = "/updateDomain", method = RequestMethod.GET)
	public String updateDomeniu(
			@RequestParam(name="idDomeniu") String idDomeniu, 
			@RequestParam(name="numeDomeniu") String numeDomeniu,
			Model model)
	{
		
		

		

		Connection dbConnection = null;
	
		try {
			dbConnection = OracleConnection.getDBConnection();
			
			CallableStatement  cStmt = dbConnection.prepareCall("{call packageupdate.updatedomenii(?,?)}");	//isDomainAlready is Oracle Function
			cStmt.setInt(1, Integer.parseInt(idDomeniu));
			cStmt.setString(2, numeDomeniu);
			cStmt.executeUpdate();
	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "redirect:/domenii";
	}
	
	
	
	
	
	
	

	
	@RequestMapping(value = "/deleteAllDomains", method = RequestMethod.POST)
	public String deleteAllCompanies()
	{
		Connection dbConnection = null;
		Statement statement = null;
		
		String sqlDELETE = "DELETE FROM DOMENII";
		
		
		try {
			dbConnection = OracleConnection.getDBConnection();
			statement = dbConnection.createStatement();
			
			statement.executeUpdate(sqlDELETE);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "redirect:/domenii";
	}
	
	
	/*
	private static ArrayList<Domenii> selectRecordsFromDomenii()
	{
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		String selectSQL = "SELECT * FROM DOMENII ORDER BY id_domeniu";
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
	
	//====================================================Intoarce datele din Domenii - PACKAGE============================
	private static ArrayList<Domenii> selectRecordsFromDomenii()
	{
		Connection dbConnection = null;
		
		ArrayList<Domenii> listaDomenii = new ArrayList<Domenii>();
		
		try {
			dbConnection = OracleConnection.getDBConnection();
			CallableStatement  cStmt = dbConnection.prepareCall("{call intoarcedate.intoarcetoatedomeniile(?)}");	//isDomainAlready is Oracle Function
			cStmt.registerOutParameter(1, OracleTypes.CURSOR);               //-> deoarece procedura foloseste un cursor
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
	
}




























