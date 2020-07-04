package com.revature.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.crypto.bcrypt.BCrypt;


@Entity
@Table(name = "Users")
public class User {
	
	@Id//id is set to be the primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)//auto-generated
	private int uid;
	private int user_type;
	@Column(unique = true) //sets unique constraint on the column "username"
	private String username;
	private String userpass;
	private String firstname;
	private String lastname;
	
	@Column(unique = true) //sets unique constraint on the column "email"
	private String email;
	private float rating_sigma = 0;
	private int times_rated = 0;
	private String user_img;
	private String hash;
	private String salt;
	
	
	/* Primary Constructor */
	public User(int uid, int user_type, String username,String firstname,String lastname, String userpass, String email) {
		super();
		this.uid=uid;
		this.user_type = user_type;
		this.username = username;
		this.firstname= firstname;
		this.lastname=lastname;
		this.userpass = userpass;
		this.salt=BCrypt.gensalt();
		this.hash=BCrypt.hashpw(userpass, salt);
		this.email = email;
	}
	
	
	/* Default Constructor */
	public User() {
		super();
	}
	
	
	
	/* Getters and Setters*/
	public int getUid() {
		return uid;
	}
	
	
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	


	public int getUser_type() {
		return user_type;
	}


	public int isUser_type() {	//this getter doesn't use standard name scheme
		return user_type;
	}
	public void setUser_type(int user_type) {
		this.user_type = user_type;
	}
	

	public String getImg() { //this getter doesn't use standard name scheme
		return user_img;
	}
	public void setImg(String user_img) {
		this.user_img = user_img;
	}

	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	
	public String getUserpass() {
		return userpass;
	}
	public void setUserpass(String userpass) {
		this.userpass = userpass;
	}
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public float getRating_sigma() {
		return rating_sigma;
	}
	
	
	public int getTimes_rated() {
		return times_rated;
	}
	
	
	/* Logic for user Rating management */
	//method for determining average rating.
	private float calculate_average(float rateSig, int timesRated) {
		return rateSig/timesRated;
	}
	
	//process for adding and updating the rating for the user.
	//returns the average user rating calculated via the rating_sigma as the numerator and times_rated as the denominator.
	public float addRating(int rate) {
		this.rating_sigma+=rate;
		this.times_rated++;
		return calculate_average(this.rating_sigma,this.times_rated); 	}

	
	/* ToString Method */
	@Override
	public String toString() {//omitted the user_img string
		return "User [uid=" + uid + ", user_type=" + user_type + ", username=" + username + ", userpass=" + userpass
				+ ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
				+ ", rating_sigma=" + rating_sigma + ", times_rated=" + times_rated + "]";
	}

}