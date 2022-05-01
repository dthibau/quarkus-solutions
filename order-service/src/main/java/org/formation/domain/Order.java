package org.formation.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
