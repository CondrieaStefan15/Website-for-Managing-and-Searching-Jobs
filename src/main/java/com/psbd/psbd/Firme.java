package com.psbd.psbd;

public class Firme {

	private int idFirma;
	private String numeFirma;
	private int numarTelefon;
	private int idDomeniu;
	private String email;
	private String firmeNumeDomeniu;
	
	

	public void setIdFirma(int idFirma){
		this.idFirma = idFirma;
	}
	public void setNumeFirma(String numeFirma){
		this.numeFirma = numeFirma;
	}
	public void setEmail(String email){
		this.email = email;
	}
	public void setNumarTelefon(int numarTelefon){
		this.numarTelefon = numarTelefon;
	}
	public void setIdDomeniu(int idDomeniu){
		this.idDomeniu = idDomeniu;
	}
	public void setFirmeNumeDomeniu(String firmeNumeDomeniu){
		this.firmeNumeDomeniu = firmeNumeDomeniu;
	}
	
	
	public int getIdFirma(){
		return this.idFirma;
	}
	public String getNumeFirma(){
		return this.numeFirma;
	}
	public String getEmail(){
		return this.email;
	}
	public int getNumarTelefon(){
		return this.numarTelefon;
	}
	public int getIdDomeniu(){
		return this.idDomeniu;
	}
	public String getFirmeNumeDomeniu(){
		return this.firmeNumeDomeniu;
	}
	
}
