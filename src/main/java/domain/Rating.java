package domain;

import java.sql.Timestamp;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity
@IdClass(RatingPK.class)
public class Rating {
	/**
	 * id includes userId and orderId
	 */
	@Id
	String userId;
	@Id
	String orderId;
	/**
	 * the user who does rating
	 */
	String commentorId;
	/**
	 * rating from 1 to 5
	 */
	int rating;
	String comment;
	Timestamp ratingTime;
	
	public Rating(){};
	
//	public RatingPK getId() {
//		return id;
//	}
//
//	public void setId(RatingPK id) {
//		this.id = id;
//	}

	public String getCommentorId() {
		return commentorId;
	}
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setCommentorId(String commenterId) {
		this.commentorId = commenterId;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Timestamp getRatingTime() {
		return ratingTime;
	}
	public void setRatingTime(Timestamp ratingTime) {
		this.ratingTime = ratingTime;
	}
	
}
