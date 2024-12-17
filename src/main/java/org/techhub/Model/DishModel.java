package org.techhub.Model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DishModel extends MenuModel {
	private int dishId;
	private String dishName;
	private int price;
	private String category;

}
