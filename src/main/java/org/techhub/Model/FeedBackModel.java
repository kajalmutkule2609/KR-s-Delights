package org.techhub.Model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedBackModel {
	private int feedbackId;
	private String feedback;
	private String rating;
	private String email;

}
