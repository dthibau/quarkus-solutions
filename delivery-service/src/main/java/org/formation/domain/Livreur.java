package org.formation.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Livreur {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	
	public String nom;
	
	public String telephone;

	@OneToMany(cascade = CascadeType.ALL)
	public List<Review> reviews;
	
}
