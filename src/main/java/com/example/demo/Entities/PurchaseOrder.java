package com.example.demo.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Table(name = "PURCHASE_ORDER")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PurchaseOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	Long customer_id;
	
	@Column
	Long product_id;
	
	@Column
	int ordered_quantity;
	
	@Column
	float price_per_unit;
	
	@Column
	float total_price;
	
	public PurchaseOrder(Long c_id,  Long p_id, int o_q,  float p_p_u, float t_p){
		customer_id = c_id;
		product_id = p_id;
		ordered_quantity = o_q;
		price_per_unit = p_p_u;
		total_price = t_p;
	}
}
