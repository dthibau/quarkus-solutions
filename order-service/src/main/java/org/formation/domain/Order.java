package org.formation.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Table(name = "t_order")
@Builder
public class Order {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Instant date;
	private float discount;

	@Embedded
	private PaymentInformation paymentInformation;
	
	@Embedded
	private DeliveryInformation deliveryInformation;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	List<OrderItem> orderItems = new ArrayList<>();
	
}
