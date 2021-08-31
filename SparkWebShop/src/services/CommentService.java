package services;

import java.util.ArrayList;

import beans.Comment;
import beans.Customer;
import beans.Order;
import beans.Restaurant;
import dto.AdminCommentDTO;
import dto.RestaurantCommentDTO;

public class CommentService {
	
	private ArrayList<Comment> comments = new ArrayList<Comment>();
	private ArrayList<AdminCommentDTO> commentsDTO = new ArrayList<AdminCommentDTO>();
	private static OrderService orderService = new OrderService();
	private static CustomerService customerService = new CustomerService();

	public ArrayList<AdminCommentDTO> getComments() {
		return commentsDTO;
	}
	
	public ArrayList<RestaurantCommentDTO> getRestaurantComments(int restaurantId) {
		ArrayList<RestaurantCommentDTO> restaurantComments = new ArrayList<RestaurantCommentDTO>();
		for (Comment comment : comments) {
			if (comment.getRestaurantId() == restaurantId /*&& comment.isApproved()*/) { //jer jos nije uradjeno
				for (Order o : orderService.getOrders()) {
					if (o.getId() == comment.getOrderId()) {
						for (Customer c : customerService.getCustomers()) {
							if (c.getUser().getId() == o.getCustomerId()) {
								RestaurantCommentDTO rcDTO = new RestaurantCommentDTO(c.getUser().getName(), c.getUser().getLastName(), o.getOrderInfo(), comment.getContent(), o.getRating());
								restaurantComments.add(rcDTO);
							}
						}
					}
				}
			}
		}
		return restaurantComments;
	}

	public void setComments(ArrayList<AdminCommentDTO> comments) {
		this.commentsDTO = comments;
	}

	public CommentService() {
		comments.add(new Comment(10, 2, 1, 5, "Dobar"));
		comments.add(new Comment(10, 4, 3, 2, "Nije dobar"));
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
