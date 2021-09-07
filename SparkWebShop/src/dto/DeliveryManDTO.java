package dto;

import java.time.LocalDateTime;

public class DeliveryManDTO {
	private String ime;
	private String prezime;
	private String imeArtikla;
	private LocalDateTime datumIvremePorudzbine;
	private double cena;
	
	
	public String getIme() {
		return ime;
	}


	public void setIme(String ime) {
		this.ime = ime;
	}


	public String getPrezime() {
		return prezime;
	}


	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}


	public String getImeArtikla() {
		return imeArtikla;
	}


	public void setImeArtikla(String imeArtikla) {
		this.imeArtikla = imeArtikla;
	}


	public LocalDateTime getDatumIvremePorudzbine() {
		return datumIvremePorudzbine;
	}


	public void setDatumIvremePorudzbine(LocalDateTime datumIvremePorudzbine) {
		this.datumIvremePorudzbine = datumIvremePorudzbine;
	}


	public double getCena() {
		return cena;
	}


	public void setCena(double cena) {
		this.cena = cena;
	}


	public DeliveryManDTO() {
		// TODO Auto-generated constructor stub
	}


	public DeliveryManDTO(String ime, String prezime, String imeArtikla, LocalDateTime datumIvremePorudzbine,
			double cena) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.imeArtikla = imeArtikla;
		this.datumIvremePorudzbine = datumIvremePorudzbine;
		this.cena = cena;
	}
	
}
