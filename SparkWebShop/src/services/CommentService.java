package services;

import java.util.ArrayList;

import beans.Comment;
import beans.Customer;
import beans.Restaurant;
import dto.AdminCommentDTO;

public class CommentService {
	
	private ArrayList<Comment> comments = new ArrayList<Comment>();
	private ArrayList<AdminCommentDTO> commentsDTO = new ArrayList<AdminCommentDTO>();

	public ArrayList<AdminCommentDTO> getComments() {
		return commentsDTO;
	}

	public void setComments(ArrayList<AdminCommentDTO> comments) {
		this.commentsDTO = comments;
	}

	public CommentService() {
		super();
	}

	public ArrayList<AdminCommentDTO> getCommentsDTO() {
		ArrayList <Customer> customers = new ArrayList<Customer>(); //ovo ce biti iz baze
		ArrayList <Restaurant> restaurants = new ArrayList<Restaurant>();
		String name = "";
		String lastname = "";
		String username = "";
		String restaurantName = "";
		for (Comment comment : comments) {
			for (Customer customer : customers) {
				if (comment.getCustomerId() == customer.getUser().getId()) {
					name = customer.getUser().getName();
					lastname = customer.getUser().getLastName();
					username = customer.getUser().getUsername();
					break;
				}
			}
			for (Restaurant restaurant : restaurants) {
				if (restaurant.getId() == comment.getRestaurantId()) {
					restaurantName = restaurant.getName();
					break;
				}
			}
			AdminCommentDTO commentDTO = new AdminCommentDTO(username, name, lastname, restaurantName, comment.getContent(), String.valueOf(comment.getRating()), comment.isApproved() ? "DA" : "NE");
			commentsDTO.add(commentDTO);
		}
		return commentsDTO;
	}
	
	
}
