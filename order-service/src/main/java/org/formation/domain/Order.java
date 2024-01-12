package org.formation.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "t_order")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private Instant date;
	
	private float discount;
	

	@Embedded
	private PaymentInformation paymentInformation;
	
	@Embedded
	private DeliveryInformation deliveryInformation;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	List<OrderItem> orderItems = new ArrayList<>();
	
}
