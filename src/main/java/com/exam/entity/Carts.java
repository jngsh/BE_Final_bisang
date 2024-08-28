package com.exam.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "carts")
public class Carts {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int cartId;
//    int userId;
	
	@OneToOne
	@JoinColumn(name = "userId")
	Users users;
    
//    @OneToMany(mappedBy = "cart")
//    @JsonIgnore
//    List<CartItems> items;
}
