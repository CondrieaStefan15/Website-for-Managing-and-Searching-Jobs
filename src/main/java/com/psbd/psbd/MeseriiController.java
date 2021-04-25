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

import com.psbd.psbd.Firme;
import com.psbd.psbd.Meserii;
import com.psbd.psbd.OracleConnection;

import oracle.jdbc.OracleTypes;

import com.psbd.psbd.Locatii;

@Controller
public class MeseriiController {


	@RequestMapping(value="/meserii")
	public String meserie(Model model)
	{

		ArrayList<Meserii> listaMeserii = selectRecordsFromMeserii("meserii"); //afisare meserii dupa id_meserie : default.Ordinea ascendent e pusa doar sa sa intre in functia respectiva, sa nu mai fac o alta functie fara acest parametru, deoarece in acest caz nu e necesar parametrul abc
		model.addAttribute("listaMeserii", listaMeserii);
		
	
		System.out.println("\nse apeleaza meserii \n");
		
		ArrayList<Firme> listaFirme = selectRecordsFromFirme();
		ArrayList<Locatii> listaLocatii = selectRecordsFromLocatii();
		
		model.addAttribute("listaFirme", listaFirme);
		model.addAttribute("listaLocatii", listaLocatii);
		
		ArrayList<String> tipuriMeserii = new ArrayList<String>();
						tipuriMeserii.add("Full-Time");
						tipuriMeserii.add("Part-Time");
		model.addAttribute("tipuriMeserii", tipuriMeserii);
		
		ArrayList<String> listaCriterii = new ArrayList<String>();
		listaCriterii.add("salariu");
		listaCriterii.add("nume_meserie");
		listaCriterii.add("nume_locatie");
		listaCriterii.add("nume_firma");
		listaCriterii.add("id_meserie");
		model.addAttribute("listaCriterii", listaCriterii);	
		
		ArrayList<String> listaOrdine = new ArrayList<String>();
		listaOrdine.add("crescator");
		listaOrdine.add("descrescator");
		model.addAttribute("listaOrdine", listaOrdine);
		
		ArrayList<String> lista_sal_min_or_max = new ArrayList<String>();
		lista_sal_min_or_max.add("maxim");
		lista_sal_min_or_max.add("minim");
		model.addAttribute("lista_sal_min_or_max", lista_sal_min_or_max);
		
		ArrayList<Domenii> listaDomenii = selectRecordsFromDomenii();
		model.addAttribute("listaDomenii", listaDomenii);

		
		return "/meserii";
	}

	

	@RequestMapping(value="/sortareMeserii")
	public String sortare_meserii(
			@RequestParam(name="criteriu") String criteriu,
			@RequestParam(name="ordinea") String ordinea,
			Model model)
	{
		//===========================criteriu
		System.out.println("Criteriul " + criteriu);
		System.out.println("\nse apeleaza meserii pentru afisare cu sortare\n");
		if(criteriu.equals("salariu") || criteriu.equals("nume_meserie") || criteriu.equals("nume_locatie") || criteriu.equals("nume_firma") || criteriu.equals("id_meserie"))
		{
			ArrayList<Meserii> listaMeserii = selectRecordsFromMeseriiCustom(criteriu, ordinea);
			model.addAttribute("listaMeserii", listaMeserii);
			System.out.println("Custom sort");
		}
		else
		{
			System.out.println("Eroare privind tipul criteriului de sortare a meseriilor");
		}

		
		//===========================meserii interes=======================
		
		//ArrayList<Meserii> listaMeseriiInteres = selectRecordsFromMeseriiInteres();
		//model.addAttribute("listaMeseriiInteres", listaMeseriiInteres);
		
		//===================================================
		ArrayList<Firme> listaFirme = selectRecordsFromFirme();
		ArrayList<Locatii> listaLocatii = selectRecordsFromLocatii();
		
		model.addAttribute("listaFirme", listaFirme);
		model.addAttribute("listaLocatii", listaLocatii);
		
		ArrayList<String> tipuriMeserii = new ArrayList<String>();
						tipuriMeserii.add("Full-Time");
						tipuriMeserii.add("Part-Time");
		model.addAttribute("tipuriMeserii", tipuriMeserii);	
		
		ArrayList<String> listaCriterii = new ArrayList<String>();
		listaCriterii.add("salariu");
		listaCriterii.add("nume_meserie");
		listaCriterii.add("nume_locatie");
		listaCriterii.add("nume_firma");
		listaCriterii.add("id_meserie");
		model.addAttribute("listaCriterii", listaCriterii);	
		
		ArrayList<String> listaOrdine = new ArrayList<String>();
		listaOrdine.add("crescator");
		listaOrdine.add("descrescator");
		model.addAttribute("listaOrdine", listaOrdine);
		
		System.out.println("Se apeleaza /sortareMeserii ");
		
		
		return "/sortareMeserii";
	}
	
	
	
	
	
	@RequestMapping(value="/maxSalaryFromCounty")
	public String salariulMaximDinJudet(            
			@RequestParam(name="numeJudet") String numeJudet,
			@RequestParam(name="minMaxSal") String minMaxSal,
			Model model)
	{
		//===========================criteriu
		Connection dbConnection = null;
		CallableStatement  cStmt=null;
		ArrayList<Meserii> listaMeserii= new ArrayList<Meserii>();
		
		ArrayList<Locatii> listaLocatii = selectRecordsFromLocatii();
		model.addAttribute("listaLocatii", listaLocatii);
		ArrayList<String> lista_sal_min_or_max = new ArrayList<String>();
		lista_sal_min_or_max.add("maxim");
		lista_sal_min_or_max.add("minim");
		model.addAttribute("lista_sal_min_or_max", lista_sal_min_or_max);
		
		try {
			dbConnection = OracleConnection.getDBConnection();
			
			
			if(minMaxSal.equals("maxim"))
			{
				cStmt = dbConnection.prepareCall("{? = call get_max_sal_from_judet(?)}");	//functie ce returneaza salariul maxim din tabelul meserii
			}
			else if(minMaxSal.equals("minim"))
			{
				cStmt = dbConnection.prepareCall("{? = call get_min_sal_from_judet(?)}");
			}
			cStmt.registerOutParameter(1, Types.NUMERIC);
			cStmt.setString(2, numeJudet);
			cStmt.executeUpdate();
			
			int salariuMaximOrMinim = cStmt.getInt(1);
			
			
			if(salariuMaximOrMinim != 0)	
			{
				System.out.println(salariuMaximOrMinim);
				System.out.println(numeJudet);

				
				CallableStatement  cStmt2 = dbConnection.prepareCall("{call max_salary_from_county.intoarcemMeseriaCeaMaiPlatita(?,?,?)}");	//chemama procedura din pachet care va returna inregistrarile ce contin salariul maxim
				cStmt2.registerOutParameter(1, OracleTypes.CURSOR);
				cStmt2.setInt(2, salariuMaximOrMinim);
				cStmt2.setString(3, numeJudet);
				cStmt2.executeUpdate();
				ResultSet resultSet= (ResultSet) cStmt2.getObject(1) ;
				while(resultSet.next())
				{
					System.out.println("Intra in while?");
					Meserii meserie = new Meserii();
							meserie.setIdMeserie(resultSet.getInt("id_meserie"));
							meserie.setNumeMeserie(resultSet.getString("nume_meserie"));
							meserie.setTipMeserie(resultSet.getString("tip_meserie"));
							meserie.setSalariu(resultSet.getInt("salariu"));
							meserie.setNivelStudii(resultSet.getString("nivel_studii"));
							meserie.setParticularitati(resultSet.getString("particularitati"));
							meserie.setMeseriiNumeFirma(resultSet.getString("meserii_nume_firma"));
							meserie.setMeseriiLocatie(resultSet.getString("meserii_locatie"));
							meserie.setIdFirma(resultSet.getInt("id_firma"));
							meserie.setIdLocatie(resultSet.getInt("id_locatie"));
							
							
							System.out.println("id_meserie:" + meserie.getIdMeserie());
							System.out.println("nume_meserie:" + meserie.getNumeMeserie());
							System.out.println("salariu:" + meserie.getSalariu());

							
					listaMeserii.add(meserie);
				}	
			}
				
			model.addAttribute("listaMeserii", listaMeserii);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "/maxSalaryFromCounty";
	}
	
	
	
	@RequestMapping(value="/searchInParticularitati")
	public String searchInParticularitati(
			@RequestParam(name="cheie") String cheie,            //la noi nume judet e de fapt id ul sau
			Model model)
	{
		//===========================criteriu
		Connection dbConnection = null;
		ArrayList<Meserii> listaMeserii= new ArrayList<Meserii>();
		
		
		try {
			dbConnection = OracleConnection.getDBConnection();
			CallableStatement  cStmt = dbConnection.prepareCall("{call searchinparticularitati.intoarcemMeseriileDupaCheie(?,?)}");	//chemama procedura din pachet care va returna inregistrarile ce contin salariul maxim
			cStmt.registerOutParameter(1, OracleTypes.CURSOR);
			cStmt.setString(2, cheie);
			cStmt.executeUpdate();

			ResultSet resultSet= (ResultSet) cStmt.getObject(1) ;

			while(resultSet.next())
			{

				Meserii meserie = new Meserii();
						meserie.setIdMeserie(resultSet.getInt("id_meserie"));
						meserie.setNumeMeserie(resultSet.getString("nume_meserie"));
						meserie.setTipMeserie(resultSet.getString("tip_meserie"));
						meserie.setSalariu(resultSet.getInt("salariu"));
						meserie.setNivelStudii(resultSet.getString("nivel_studii"));
						meserie.setParticularitati(resultSet.getString("particularitati"));
						meserie.setMeseriiNumeFirma(resultSet.getString("meserii_nume_firma"));
						meserie.setMeseriiLocatie(resultSet.getString("meserii_locatie"));
						meserie.setIdFirma(resultSet.getInt("id_firma"));
						meserie.setIdLocatie(resultSet.getInt("id_locatie"));
							
							
						System.out.println("id_meserie:" + meserie.getIdMeserie());
						System.out.println("nume_meserie:" + meserie.getNumeMeserie());
						System.out.println("salariu:" + meserie.getSalariu());

							
				listaMeserii.add(meserie);
			}		
				
		model.addAttribute("listaMeserii", listaMeserii);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "/searchInParticularitati";
	}
	
	
	@RequestMapping(value="/afisareMeseriiDinDomeniu")
	public String afisaremeseriiDintrunAnumitDomeniu(
			@RequestParam(name="numeDomeniu") String numeDomeniu,            //aici de fapt iti da idul(trebuie sa convertim id ul in numele domeniului) am foloit functia pentru asta
			Model model)
	{
		//===========================criteriu
		Connection dbConnection = null;
		ArrayList<Meserii> listaMeserii= new ArrayList<Meserii>();
		ArrayList<Domenii> listaDomenii = selectRecordsFromDomenii();
		model.addAttribute("listaDomenii", listaDomenii);
		System.out.println(numeDomeniu);
		
		try {
			dbConnection = OracleConnection.getDBConnection();
			
			

			CallableStatement cStmt2 = dbConnection.prepareCall("{? = call returndomainname(?)}");	//functie ce returneaza salariul maxim din tabelul meserii
			
			cStmt2.registerOutParameter(1, Types.VARCHAR);
			cStmt2.setString(2, numeDomeniu);
			cStmt2.executeUpdate();
			
			String id_to_name =cStmt2.getString(1);
			System.out.println(id_to_name);
			
			
			CallableStatement  cStmt = dbConnection.prepareCall("{call intoarcedate.intoarcemeseriidupaundomeniu(?,?)}");	//chemama procedura din pachet care va returna inregistrarile ce contin salariul maxim
			cStmt.registerOutParameter(1, OracleTypes.CURSOR);
			cStmt.setString(2, id_to_name);
			cStmt.executeUpdate();
			
			System.out.println("Termina executia procedurii?");

			ResultSet resultSet= (ResultSet) cStmt.getObject(1) ;

			while(resultSet.next())
			{
				
				System.out.println("Intra in setarea meserii in lista?");
				Meserii meserie = new Meserii();
						meserie.setIdMeserie(resultSet.getInt("id_meserie"));
						meserie.setNumeMeserie(resultSet.getString("nume_meserie"));
						meserie.setTipMeserie(resultSet.getString("tip_meserie"));
						meserie.setSalariu(resultSet.getInt("salariu"));
						meserie.setNivelStudii(resultSet.getString("nivel_studii"));
						meserie.setParticularitati(resultSet.getString("particularitati"));
						meserie.setMeseriiNumeFirma(resultSet.getString("meserii_nume_firma"));
						meserie.setMeseriiLocatie(resultSet.getString("meserii_locatie"));
						meserie.setIdFirma(resultSet.getInt("id_firma"));
						meserie.setIdLocatie(resultSet.getInt("id_locatie"));
							
							
						System.out.println("id_meserie:" + meserie.getIdMeserie());
						System.out.println("nume_meserie:" + meserie.getNumeMeserie());
						System.out.println("salariu:" + meserie.getSalariu());

							
				listaMeserii.add(meserie);
			}		
				
		model.addAttribute("listaMeserii", listaMeserii);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "/afisareMeseriiDinDomeniu";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(value="/cos_meserii")
	public String cos_meserie(Model model)
	{

		ArrayList<Meserii> listaCosMeserii = selectRecordsFromMeserii("cos_meserii"); //afisare meserii dupa id_meserie : default.Ordinea ascendent e pusa doar sa sa intre in functia respectiva, sa nu mai fac o alta functie fara acest parametru, deoarece in acest caz nu e necesar parametrul abc
		model.addAttribute("listaCosMeserii", listaCosMeserii);
		
		
		//Pentru a lua informatiile de la firme pentru meseriile ce le avem in cos
		Connection dbConnection = null;	
		Meserii m=new Meserii();
		ArrayList<Firme> listaContacteFirmePentruCos = new ArrayList<Firme>();
		for(int i=0; i<listaCosMeserii.size(); i++)
		{
			m=listaCosMeserii.get(i);
			try {
				dbConnection = OracleConnection.getDBConnection();
				CallableStatement  cStmt = dbConnection.prepareCall("{call intoarcedate.intoarcecontacteledelaofirma(?,?)}");	
				cStmt.registerOutParameter(1, OracleTypes.CURSOR);
				cStmt.setInt(2, m.getIdMeserie());
				cStmt.executeUpdate();	
				ResultSet resultSet= (ResultSet) cStmt.getObject(1) ;

				while(resultSet.next())
				{
					Firme firma = new Firme();
					
							firma.setIdFirma(resultSet.getInt("id_firma"));
							firma.setNumeFirma(resultSet.getString("nume_firma"));
							firma.setEmail(resultSet.getString("email"));
							firma.setNumarTelefon(resultSet.getInt("numar_telefon"));
							firma.setIdDomeniu(resultSet.getInt("id_domeniu"));
							firma.setFirmeNumeDomeniu(resultSet.getString("firme_nume_domeniu"));
							
								
								
							System.out.println("id_firma:" + firma.getIdFirma());
							System.out.println("nume_firma:" + firma.getNumeFirma());
							System.out.println("email" + firma.getEmail());

								
							listaContacteFirmePentruCos.add(firma);
				}	
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		model.addAttribute("listaContacteFirmePentruCos", listaContacteFirmePentruCos);
		
		System.out.println("Intra in aflarea locatiilor?");
		
		//Pentru a lua informatiile locatiilor pentru meseriile introduse in cos

		ArrayList<Locatii> listaLocatiiPentruMeseriiCos = new ArrayList<Locatii>();
		for(int i=0; i<listaCosMeserii.size(); i++)
		{
			m=listaCosMeserii.get(i);
			try {
				System.out.println("Intra in try ul de la locatii?");
				dbConnection = OracleConnection.getDBConnection();
				CallableStatement  cStmt2 = dbConnection.prepareCall("{call intoarcedate.intoarcelocmeseriecos(?,?)}");	
				cStmt2.registerOutParameter(1, OracleTypes.CURSOR);
				cStmt2.setInt(2, m.getIdLocatie());
				cStmt2.executeUpdate();
				System.out.println("se executa packageul de la locatii?");
				ResultSet resultSet= (ResultSet) cStmt2.getObject(1) ;

				while(resultSet.next())
				{
					System.out.println("Intra in while ul de la locatii unde citeste info?");
					Locatii locatie = new Locatii();
					
							locatie.setIdLocatie(resultSet.getInt("id_locatie"));
							locatie.setJudet(resultSet.getString("judet"));
							locatie.setNumeLocatie(resultSet.getString("nume_locatie"));
							locatie.setAdresa(resultSet.getString("adresa"));
							
								
								
							System.out.println("id_locatie:" + locatie.getIdLocatie());
							System.out.println("judet:" + locatie.getJudet());
							System.out.println("nume locatie" + locatie.getNumeLocatie());

								
							listaLocatiiPentruMeseriiCos.add(locatie);
				}	
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		model.addAttribute("listaLocatiiPentruMeseriiCos", listaLocatiiPentruMeseriiCos);
			
			
		return "/cos_meserii";
	}
	
	
	
	@RequestMapping(value="/insertInCosMeserii", method = RequestMethod.POST)
	public String insertMeserieInCos(
			@RequestParam(name="idMeserie") String idMeserie
			)
	{
		
		System.out.println("idMeseriei introdus in cos:" + idMeserie);

		
		Connection dbConnection = null;			
		try {
			dbConnection = OracleConnection.getDBConnection();
			CallableStatement  cStmt = dbConnection.prepareCall("{call packageinserari.insereazaInCosMeserii(?)}");	//isDomainAlready is Oracle Function
			cStmt.setInt(1, Integer.parseInt(idMeserie));
			cStmt.executeUpdate();	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return "redirect:/cos_meserii";
	}
	
	
	
	
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	///////=========================================================INSERARE=======================================
	/*
	@RequestMapping(value="/insertMeserie", method = RequestMethod.POST)
	public String insereazaMeseria(
			@RequestParam(name="numeMeserie") String numeMeserie,
			@RequestParam(name="tipMeserie") String tipMeserie,
			@RequestParam(name="salariu") String salariu,
			@RequestParam(name="nivelStudii") String nivelStudii,
			@RequestParam(name="numeFirma") String idFirma,
			@RequestParam(name="numeLocatie") String idLocatie,
			@RequestParam(name="particularitati") String particularitati
			)
	{
		
		System.out.println("Meserie:" + numeMeserie);
		System.out.println("tip:" + tipMeserie);
		System.out.println("sal:" + salariu);
		System.out.println("studii:" + nivelStudii);
		System.out.println("firma:" + idFirma);
		System.out.println("locatie:" + idLocatie);
		System.out.println("particularitati" + particularitati);
		
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		Statement statement = null;
		
		String insertInToMESERII = "INSERT INTO MESERII (id_meserie, nume_meserie, tip_meserie, salariu, nivel_studii, particularitati, meserii_nume_firma, meserii_locatie, id_firma, id_locatie) VALUES (?, ?, ?, ?, ?, ?,(select nume_firma from firme where id_firma = ?), (select judet from locatii where id_locatie = ?), ?, ?)";
		String getIdMax = "SELECT NVL(MAX(id_meserie),0) FROM MESERII";
			
		
		try {
			dbConnection = OracleConnection.getDBConnection();
			statement = dbConnection.createStatement();
			
			ResultSet resultSet = statement.executeQuery(getIdMax);
			int idMax = resultSet.next() ? resultSet.getInt(1) : 0;       //avem id uri in avioane? daca da, getInt ia coloana 1, daca nu, incepe cu index 0 pt id
			
			
			preparedStatement = dbConnection.prepareStatement(insertInToMESERII);
			
			preparedStatement.setInt(1, idMax+1);
			preparedStatement.setString(2, numeMeserie);
			preparedStatement.setString(3, tipMeserie);
			if(salariu != "")
			{
				preparedStatement.setInt(4, Integer.parseInt(salariu));
			}
			else
			{
				preparedStatement.setInt(4, 0);
			}
			preparedStatement.setString(5, nivelStudii);
			preparedStatement.setString(6, particularitati);
			preparedStatement.setInt(7, Integer.parseInt(idFirma));
			preparedStatement.setInt(8, Integer.parseInt(idLocatie));
			preparedStatement.setInt(9, Integer.parseInt(idFirma));
			preparedStatement.setInt(10, Integer.parseInt(idLocatie));
			
			
			preparedStatement.executeUpdate();
			
			resultSet.close();
			preparedStatement.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return "redirect:/meserii";
	}
	*/
	
	//==========================================================INSERT WITH PACKAGE====================================
	@RequestMapping(value="/insertMeserie", method = RequestMethod.POST)
	public String insereazaMeseria(
			@RequestParam(name="numeMeserie") String numeMeserie,
			@RequestParam(name="tipMeserie") String tipMeserie,
			@RequestParam(name="salariu") String salariu,
			@RequestParam(name="nivelStudii") String nivelStudii,
			@RequestParam(name="numeFirma") String idFirma,
			@RequestParam(name="numeLocatie") String idLocatie,
			@RequestParam(name="particularitati") String particularitati
			)
	{
		
		System.out.println("Meserie:" + numeMeserie);
		System.out.println("tip:" + tipMeserie);
		System.out.println("sal:" + salariu);
		System.out.println("studii:" + nivelStudii);
		System.out.println("firma:" + idFirma);
		System.out.println("locatie:" + idLocatie);
		System.out.println("particularitati" + particularitati);
		
		Connection dbConnection = null;
		Statement statement = null;
		
		String getIdMax = "SELECT NVL(MAX(id_meserie),0) FROM MESERII";
			
		
		try {
			dbConnection = OracleConnection.getDBConnection();
			statement = dbConnection.createStatement();
			
			ResultSet resultSet = statement.executeQuery(getIdMax);
			int idMax = resultSet.next() ? resultSet.getInt(1) : 0;       //avem id uri in avioane? daca da, getInt ia coloana 1, daca nu, incepe cu index 0 pt id
			
			CallableStatement  cStmt = dbConnection.prepareCall("{call packageinserari.insereazaMeserii(?,?,?,?,?,?,?,?)}");	//isDomainAlready is Oracle Function
			cStmt.setInt(1, idMax+1);
			cStmt.setString(2, numeMeserie);
			cStmt.setString(3, tipMeserie);
			if(salariu != ""){
				cStmt.setInt(4, Integer.parseInt(salariu));
			}
			else {
				cStmt.setInt(4, 0);
			}
			
			cStmt.setString(5, nivelStudii);
			cStmt.setString(6, particularitati);
			cStmt.setInt(7, Integer.parseInt(idFirma));
			cStmt.setInt(8, Integer.parseInt(idLocatie));
			cStmt.executeUpdate();	
			
			resultSet.close();

			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return "redirect:/meserii";
	}
	
	//==================================================================DELETE======================================
	/*
	@RequestMapping(value="/deleteMeserie")
	public String deleteMeseria(
			@RequestParam(name="idMeserie") int idMeserie
			)
	{
		
		System.out.println("Id Meserie:" + idMeserie);

		
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		
		String deleteFromMeserii = "delete from meserii where id_meserie=?";

			
		
		try {
			dbConnection = OracleConnection.getDBConnection();

			
			preparedStatement = dbConnection.prepareStatement(deleteFromMeserii);
			preparedStatement.setInt(1, idMeserie);

			
			preparedStatement.executeUpdate();
			preparedStatement.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return "redirect:/meserii";
	}
	*/
	//=======================================================DELETE CU PACKAGE===============================
	
	@RequestMapping(value="/deleteMeserie")
	public String deleteMeseria(
			@RequestParam(name="idMeserie") int idMeserie
			)
	{
		
		Connection dbConnection = null;
		try {
			dbConnection = OracleConnection.getDBConnection();

			CallableStatement  cStmt = dbConnection.prepareCall("{call packagedelete.deleteMeserii(?)}");	//isDomainAlready is Oracle Function
			cStmt.setInt(1, idMeserie);
			cStmt.executeUpdate();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return "redirect:/meserii";
	}
	
	
	
	
	
	
	
	@RequestMapping(value="/deleteMeserieDinCos")
	public String deleteMeseriaDinCos(
			@RequestParam(name="idMeserie") int idMeserie
			)
	{
		
		Connection dbConnection = null;
		try {
			dbConnection = OracleConnection.getDBConnection();

			CallableStatement  cStmt = dbConnection.prepareCall("{call packagedelete.deleteMeseriiDinCos(?)}");	//isDomainAlready is Oracle Function
			cStmt.setInt(1, idMeserie);
			cStmt.executeUpdate();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return "redirect:/cos_meserii";
	}
	
	
	
	
	
	//=========================================================UPDATE==================================================
/*	
	@RequestMapping(value="/updateMeserie", method = RequestMethod.GET)
	public String updateMeseria(
			@RequestParam(name="idMeserie") String idMeserie,
			@RequestParam(name="numeMeserie") String numeMeserie,
			@RequestParam(name="tipMeserie") String tipMeserie,
			@RequestParam(name="salariu") String salariu,
			@RequestParam(name="nivelStudii") String nivelStudii,
			@RequestParam(name="numeFirma") String idFirma,
			@RequestParam(name="numeLocatie") String idLocatie,
			@RequestParam(name="particularitati") String particularitati
			)
	{
		
		System.out.println("Meserie:" + numeMeserie);
		System.out.println("tip:" + tipMeserie);
		System.out.println("sal:" + salariu);
		System.out.println("studii:" + nivelStudii);
		System.out.println("firma:" + idFirma);
		System.out.println("locatie:" + idLocatie);
		System.out.println("particularitati" + particularitati);
		
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		
		String updateMESERII = "UPDATE meserii SET nume_meserie = ?, tip_meserie = ?, salariu = ?, nivel_studii = ?, particularitati = ?, meserii_nume_firma = (select nume_firma from firme where id_firma = ?), meserii_locatie = (select judet from locatii where id_locatie = ?) ,id_firma = ?, id_locatie=?  WHERE id_meserie=?";
			
		
		try {
			dbConnection = OracleConnection.getDBConnection();
			
			
			
			preparedStatement = dbConnection.prepareStatement(updateMESERII);
			
			
			preparedStatement.setString(1, numeMeserie);
			preparedStatement.setString(2, tipMeserie);
			preparedStatement.setInt(3, Integer.parseInt(salariu));
			preparedStatement.setString(4, nivelStudii);
			preparedStatement.setString(5, particularitati);
			preparedStatement.setInt(6, Integer.parseInt(idFirma));
			preparedStatement.setInt(7, Integer.parseInt(idLocatie));
			preparedStatement.setInt(8, Integer.parseInt(idFirma));
			preparedStatement.setInt(9, Integer.parseInt(idLocatie));
			
			preparedStatement.setInt(10, Integer.parseInt(idMeserie));
			
			preparedStatement.executeUpdate();
			
			preparedStatement.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return "redirect:/meserii";
	}
	*/
	
	/////////////////=================================================UPDATE cu PackAge============================================
	
	@RequestMapping(value="/updateMeserie", method = RequestMethod.GET)
	public String updateMeseria(
			@RequestParam(name="idMeserie") String idMeserie,
			@RequestParam(name="numeMeserie") String numeMeserie,
			@RequestParam(name="tipMeserie") String tipMeserie,
			@RequestParam(name="salariu") String salariu,
			@RequestParam(name="nivelStudii") String nivelStudii,
			@RequestParam(name="numeFirma") String idFirma,
			@RequestParam(name="numeLocatie") String idLocatie,
			@RequestParam(name="particularitati") String particularitati
			)
	{
			
		Connection dbConnection = null;		
		
		try {
			dbConnection = OracleConnection.getDBConnection();
			
			CallableStatement  cStmt1 = dbConnection.prepareCall("{call packageupdate.updateMeserii(?,?,?,?,?,?,?,?)}");	//isDomainAlready is Oracle Function
			cStmt1.setInt(1, Integer.parseInt(idMeserie));
			cStmt1.setString(2, numeMeserie);
			cStmt1.setString(3, tipMeserie);
			if(salariu != ""){
				cStmt1.setInt(4, Integer.parseInt(salariu));
			}
			else {
				cStmt1.setInt(4, 0);
			}
			cStmt1.setString(5, nivelStudii);
			cStmt1.setString(6, particularitati);
			cStmt1.setInt(7, Integer.parseInt(idFirma));
			cStmt1.setInt(8, Integer.parseInt(idLocatie));
			cStmt1.executeUpdate();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return "redirect:/meserii";
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
	
	
	private static ArrayList<Meserii> selectRecordsFromMeserii()
	{
		Connection dbConnection = null;
		Statement statement = null;
		String selectSQL = "SELECT * FROM MESERII";
		ArrayList<Meserii> listaMeserii = new ArrayList<Meserii>();
		
		try {
			dbConnection = OracleConnection.getDBConnection();
			statement = dbConnection.createStatement();
			
			// execute select SQL stetement
			ResultSet resultSet = statement.executeQuery(selectSQL);
			
		
			while(resultSet.next())
			{
				Meserii meserie = new Meserii();
						meserie.setIdMeserie(resultSet.getInt("id_meserie"));
						meserie.setNumeMeserie(resultSet.getString("nume_meserie"));
						meserie.setTipMeserie(resultSet.getString("tip_meserie"));
						meserie.setSalariu(resultSet.getInt("salariu"));
						meserie.setNivelStudii(resultSet.getString("nivel_studii"));
						meserie.setParticularitati(resultSet.getString("particularitati"));
						meserie.setMeseriiNumeFirma(resultSet.getString("meserii_nume_firma"));
						meserie.setMeseriiLocatie(resultSet.getString("meserii_locatie"));
						meserie.setIdFirma(resultSet.getInt("id_firma"));
						meserie.setIdLocatie(resultSet.getInt("id_locatie"));
						
						
						
				listaMeserii.add(meserie);
				
			}	
			
			return listaMeserii;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaMeserii;
	}
	*/
	
	//=================================================LUAM DATELE DIN TABELE CU PACKAGE-cursori===============
	
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
	
	
	private static ArrayList<Meserii> selectRecordsFromMeserii(String apelul)
	{
		Connection dbConnection = null;
		
		ArrayList<Meserii> listaMeserii = new ArrayList<Meserii>();
		CallableStatement  cStmt=null;
		try {
			dbConnection = OracleConnection.getDBConnection();
			if(apelul.equals("meserii"))
			{
				cStmt = dbConnection.prepareCall("{call intoarcedate.intoarcetoatemeseriile(?)}");	//isDomainAlready is Oracle Function
			}
			else if(apelul.equals("cos_meserii"))
			{
				cStmt = dbConnection.prepareCall("{call intoarcedate.intoarcetoatemeseriiledincos(?)}");
			}
			cStmt.registerOutParameter(1, OracleTypes.CURSOR);
			cStmt.executeUpdate();
			
			ResultSet resultSet= (ResultSet) cStmt.getObject(1) ;
			
		
			while(resultSet.next())
			{
				Meserii meserie = new Meserii();
						meserie.setIdMeserie(resultSet.getInt("id_meserie"));
						meserie.setNumeMeserie(resultSet.getString("nume_meserie"));
						meserie.setTipMeserie(resultSet.getString("tip_meserie"));
						meserie.setSalariu(resultSet.getInt("salariu"));
						meserie.setNivelStudii(resultSet.getString("nivel_studii"));
						meserie.setParticularitati(resultSet.getString("particularitati"));
						meserie.setMeseriiNumeFirma(resultSet.getString("meserii_nume_firma"));
						meserie.setMeseriiLocatie(resultSet.getString("meserii_locatie"));
						meserie.setIdFirma(resultSet.getInt("id_firma"));
						meserie.setIdLocatie(resultSet.getInt("id_locatie"));
						
						
						
				listaMeserii.add(meserie);
				
			}	
			
			return listaMeserii;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaMeserii;
	}
	
	
	//Acesata methode este forlosita pentru sortare.Nu merge sa fac doar o metoda care sa incarce procedurile pentru a afisa inregistrarile deoarece acesata metoda primeste un parametru iar in /meserii nu
	private static ArrayList<Meserii> selectRecordsFromMeseriiCustom(String numeCriteriu, String ordinea)
	{
		Connection dbConnection = null;
		ArrayList<Meserii> listaMeserii = new ArrayList<Meserii>();
		CallableStatement  cStmt=null;
		try {
			dbConnection = OracleConnection.getDBConnection();
			if(numeCriteriu.equals("salariu"))
			{
				cStmt = dbConnection.prepareCall("{call intoarcedate.intoarceMeseriiDupaSalariu(?,?)}");	
				cStmt.setString(2, ordinea);
			}
			else if(numeCriteriu.equals("nume_meserie"))
			{
				cStmt = dbConnection.prepareCall("{call intoarcedate.intoarcemeseriidupanumemeserie(?,?)}");
				cStmt.setString(2, ordinea);
			}
			else if(numeCriteriu.equals("nume_firma"))
			{
				cStmt = dbConnection.prepareCall("{call intoarcedate.intoarcemeseriidupanumefirma(?,?)}");
				cStmt.setString(2, ordinea);
			}
			else if(numeCriteriu.equals("nume_locatie"))
			{
				cStmt = dbConnection.prepareCall("{call intoarcedate.intoarcemeseriidupanumelocatie(?,?)}");
				cStmt.setString(2, ordinea);
			}
			else if(numeCriteriu.equals("id_meserie"))
			{
				cStmt = dbConnection.prepareCall("{call intoarcedate.intoarcetoatemeseriile(?)}");
			}
			
			cStmt.registerOutParameter(1, OracleTypes.CURSOR);
			cStmt.executeUpdate();
			ResultSet resultSet= (ResultSet) cStmt.getObject(1) ;
			
			
		
			while(resultSet.next())
			{
				Meserii meserie = new Meserii();
						meserie.setIdMeserie(resultSet.getInt("id_meserie"));
						meserie.setNumeMeserie(resultSet.getString("nume_meserie"));
						meserie.setTipMeserie(resultSet.getString("tip_meserie"));
						meserie.setSalariu(resultSet.getInt("salariu"));
						meserie.setNivelStudii(resultSet.getString("nivel_studii"));
						meserie.setParticularitati(resultSet.getString("particularitati"));
						meserie.setMeseriiNumeFirma(resultSet.getString("meserii_nume_firma"));
						meserie.setMeseriiLocatie(resultSet.getString("meserii_locatie"));
						meserie.setIdFirma(resultSet.getInt("id_firma"));
						meserie.setIdLocatie(resultSet.getInt("id_locatie"));
						
						
						//System.out.println("id_meserie:" + meserie.getIdMeserie());
						//System.out.println("nume_meserie:" + meserie.getNumeMeserie());
						//System.out.println("salariu:" + meserie.getSalariu());

						
						
						
				listaMeserii.add(meserie);
				
			}	
			
			return listaMeserii;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listaMeserii;
	}
	
	
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
