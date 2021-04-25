package com.psbd.psbd;

public class Locatii {
	
	private int id_locatie;
	private String judet;
	private String nume_locatie;
	private String adresa;
	
	public void setIdLocatie(int id_locatie){
		this.id_locatie = id_locatie;
	}
	public void setJudet(String judet){
		this.judet = judet;
	}
	
	public void setNumeLocatie(String nume_locatie){
		this.nume_locatie = nume_locatie;
	}
	
	public void setAdresa(String adresa){
		this.adresa = adresa;
	}
	
	
	
	
	public int getIdLocatie(){
		return this.id_locatie;
	}
	public String getJudet(){
		return this.judet;
	}
	public String getNumeLocatie(){
		return this.nume_locatie;
	}
	
	public String getAdresa(){
		return this.adresa;
	}
	
	

}
