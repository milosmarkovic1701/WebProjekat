package services;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Administrator;
import beans.Comment;
import beans.Customer;
import beans.Order;
import beans.Restaurant;
import dto.AdminCommentDTO;
import dto.CommentDTO;
import dto.RestaurantCommentDTO;

public class CommentService {
	
	private ArrayList<Comment> comments = new ArrayList<Comment>();
	private ArrayList<AdminCommentDTO> commentsDTO = new ArrayList<AdminCommentDTO>();
	private static OrderService orderService = new OrderService();
	private static CustomerService customerService = new CustomerService();
	private static RestaurantService restaurantService = new RestaurantService();

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
	
	
	
	public void addComment(CommentDTO comment) {
		int ratingsAmount = 0;
		int rating = 0;
		getAllComments();
		Comment newComment = new Comment(comment.getCustomerId(), comment.getRestaurantId(), comment.getOrderId(), comment.getRating(), comment.getContent());
		comments.add(newComment);
		for (Comment c : comments) {
			if (c.getRestaurantId() == comment.getRestaurantId()) {
				ratingsAmount++;
				rating += c.getRating();
			}
		}
		HashMap<Integer, Restaurant> restaurants = restaurantService.getAllRestaurants();
		restaurants.get(comment.getRestaurantId()).setRating(rating/ratingsAmount);
		ArrayList <Order> orders = orderService.getAllOrders();
		for (Order o : orders) {
			if (o.getId() == comment.getOrderId()) {
				o.setRating(comment.getRating());
				break;
			}
		}
		saveAllComments();
		restaurantService.saveAllRestaurants(restaurants);
		orderService.saveAllOrders(orders);
	}

	public CommentService() {
		comments = getAllComments();
	}

	public ArrayList<AdminCommentDTO> getCommentsDTO() {
		commentsDTO.clear();
		comments = getAllComments();
		ArrayList <Customer> customers = customerService.getAllCustomers();
		ArrayList <Restaurant> restaurants = new ArrayList<>(restaurantService.getAllRestaurants().values());
		String name = "";
		String lastname = "";
		String username = "";
		String restaurantName = "";
		for (Comment comment : comments) {
			for (Customer customer : customers) {
				if (customer.getUser().getId() == comment.getCustomerId()) {
					name = customer.getUser().getName();
					lastname = customer.getUser().getLastName();
					username = customer.getUser().getUsername();
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
	
	public ArrayList<Comment> getAllComments() {
		Gson gson = new Gson();
		comments.clear();
		try {
			Reader reader = Files.newBufferedReader(Paths.get("./static/data/comments.json"));
			Comment[] commentsList = gson.fromJson(reader, Comment[].class);
			if(commentsList != null) {
			    for (int i = 0; i < commentsList.length; i++) {
			        comments.add(commentsList[i]);
			    }
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return comments;
	}
	
	public void saveAllComments() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try {
			Writer writer = Files.newBufferedWriter(Paths.get("./static/data/comments.json"));
			writer.append(gson.toJson(comments));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
}
