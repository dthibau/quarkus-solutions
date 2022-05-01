package org.formation.web;

import java.util.List;

import org.formation.domain.Address;
import org.formation.domain.DeliveryInformation;
import org.formation.domain.Order;
import org.formation.domain.OrderItem;
import org.formation.domain.PaymentInformation;

import lombok.Data;

@Data
public class CreateOrderRequest {

	  private List<OrderItem> lineItems;
	  private Address deliveryAddress;
	  private PaymentInformation paymentInformation;
	  
	  public Order createOrder() {
		  Order order = new Order();
		  DeliveryInformation df = new DeliveryInformation();
		  df.setAddress(deliveryAddress);  
		  order.setDeliveryInformation(df);
		  order.setOrderItems(lineItems);
		  order.setPaymentInformation(paymentInformation);
		  
		  return order;
	  }
}
