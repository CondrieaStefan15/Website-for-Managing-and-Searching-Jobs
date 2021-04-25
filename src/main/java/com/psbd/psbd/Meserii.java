package com.psbd.psbd;


public class Meserii {

	private int id_meserie;
	private String nume_meserie;
	private String tip_meserie;
	private int salariu;
	private String nivel_studii;
	private String meserii_nume_firma;
	private String meserii_locatie;
	
	private int id_firma;
	private int id_locatie;
	private String particularitati;
	
	
	public void setIdMeserie(int id_meserie){
		this.id_meserie = id_meserie;
	}
	public void setNumeMeserie(String nume_meserie){
		this.nume_meserie = nume_meserie;
	}
	public void setTipMeserie(String tip_meserie){
		this.tip_meserie = tip_meserie;
	}
	public void setSalariu(int salariu){
		this.salariu = salariu;
	}
	public void setNivelStudii(String nivel_studii){
		this.nivel_studii = nivel_studii;
	}
	public void setIdFirma(int id_firma){
		this.id_firma = id_firma;
	}
	public void setIdLocatie(int id_locatie){
		this.id_locatie = id_locatie;
	}
	public void setParticularitati(String particularitati){
		this.particularitati = particularitati;
	}
	public void setMeseriiNumeFirma(String meserii_nume_firma){
		this.meserii_nume_firma = meserii_nume_firma;
	}
	public void setMeseriiLocatie(String meserii_locatie){
		this.meserii_locatie = meserii_locatie;
	}
	
	
	
	
	public int getIdMeserie(){
		return this.id_meserie;
	}
	public String getNumeMeserie(){
		return this.nume_meserie;
	}
	public String getTipMeserie(){
		return this.tip_meserie;
	}
	public int getSalariu(){
		return this.salariu;
	}
	public String getNivelStudii(){
		return this.nivel_studii;
	}
	public int getIdFirma(){
		return this.id_firma;
	}
	public int getIdLocatie(){
		return this.id_locatie;
	}
	public String getParticularitati(){
		return this.particularitati;
	}
	public String getMeseriiNumeFirma(){
		return this.meserii_nume_firma;
	}
	public String getMeseriiLocatie(){
		return this.meserii_locatie;
	}



	
}
