package dto;

import java.time.LocalDateTime;

import enums.OrderStatus;

public class RestaurantOrderDTO {
	private String ime;
	private String prezime;
	private String adresa;
	private String imeArtikla;
	private String dateInfo;
	private LocalDateTime datumIVremePorudzbine;
	private Double cena;
	private OrderStatus status;
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
	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public String getImeArtikla() {
		return imeArtikla;
	}
	public void setImeArtikla(String imeArtikla) {
		this.imeArtikla = imeArtikla;
	}
	public LocalDateTime getDatumIVremePorudzbine() {
		return datumIVremePorudzbine;
	}
	public void setDatumIVremePorudzbine(LocalDateTime datumIVremePorudzbine) {
		this.datumIVremePorudzbine = datumIVremePorudzbine;
	}
	public Double getCena() {
		return cena;
	}
	public void setCena(Double cena) {
		this.cena = cena;
	}
	public OrderStatus getStatus() {
		return status;
	}
	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	public RestaurantOrderDTO(String ime, String prezime, String adresa, String imeArtikla,
			LocalDateTime datumIVremePorudzbine, Double cena, OrderStatus status,String dateInfo) {
		super();
		this.ime = ime;
		this.prezime = prezime;
		this.adresa = adresa;
		this.imeArtikla = imeArtikla;
		this.datumIVremePorudzbine = datumIVremePorudzbine;
		this.cena = cena;
		this.status = status;
		this.dateInfo = dateInfo;
	}
	public String getDateInfo() {
		return dateInfo;
	}
	public void setDateInfo(String dateInfo) {
		this.dateInfo = dateInfo;
	}
	
	
}
