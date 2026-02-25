package org.formation.domain;




import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderItem {

	private long id;
	
	@NotBlank
	private String refProduct;
	
	private float price;
	
	@Min(1)
	private int quantity;
	
	
}
