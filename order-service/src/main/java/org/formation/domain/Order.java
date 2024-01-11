package org.formation.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "t_order")
public class Order {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private Instant date;
	
	private float discount;
	

	@Embedded
	private PaymentInformation paymentInformation;
	
	@Embedded
	  private DeliveryInformation deliveryInformation;
	
	@OneToMany(cascade = CascadeType.ALL)
	List<OrderItem> orderItems = new ArrayList<>();
	
}
