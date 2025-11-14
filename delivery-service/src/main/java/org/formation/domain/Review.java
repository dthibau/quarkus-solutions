package org.formation.domain;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.formation.web.LivraisonViews;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@JsonView(LivraisonViews.Full.class)
public class Review {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	public long id;
	
	public int note;
	
	public String commentaire;
}
