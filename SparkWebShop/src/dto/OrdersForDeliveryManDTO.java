package dto;

import java.time.LocalDateTime;

import enums.OrderStatus;

public class OrdersForDeliveryManDTO {

	private int orderId;
	private String ime;
	private String prezime;
	private String adresa;
	private String orderInfo;
	private String dateInfo;
	private LocalDateTime datumIVremePorudzbine;
	private Double cena;
	private OrderStatus status;
	private String restaurantName;
	private int restaurantId;
	private int deliveryId;
	
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
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
		return orderInfo;
	}
	public void setImeArtikla(String orderInfo) {
		this.orderInfo = orderInfo;
	}
	public String getDateInfo() {
		return dateInfo;
	}
	public void setDateInfo(String dateInfo) {
		this.dateInfo = dateInfo;
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
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public OrdersForDeliveryManDTO(int orderId, String ime, String prezime, String adresa, String orderInfo,
			String dateInfo, LocalDateTime datumIVremePorudzbine, Double cena, OrderStatus status,
			String restaurantName, int restaurantId,int deliveryId) {
		super();
		this.orderId = orderId;
		this.ime = ime;
		this.prezime = prezime;
		this.adresa = adresa;
		this.orderInfo = orderInfo;
		this.dateInfo = dateInfo;
		this.datumIVremePorudzbine = datumIVremePorudzbine;
		this.cena = cena;
		this.status = status;
		this.restaurantName = restaurantName;
		this.restaurantId = restaurantId;
		this.deliveryId = deliveryId;
	}
	public int getDeliveryId() {
		return deliveryId;
	}
	public void setDeliveryId(int deliveryId) {
		this.deliveryId = deliveryId;
	}
	
}
