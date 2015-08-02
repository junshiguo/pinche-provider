package domain;

import java.sql.Blob;

public class User {
	public static int GENDER_MALE = 0;
	public static int GENDER_FEMALE = 1;
	public static int GENDER_NULL = 2;
	
	/**
	 * user phone number, unique primary key
	 */
	String phoneNumber;
	String password;
	/**
	 * the account balance, precision: fen
	 */
	int balance;
	/**
	 * gender info. 
	 * GENDER_MALE = 0; GENDER_FEMALE = 1; GENDER_NULL = 2.
	 */
	int gender;
	int age;
	String name;
	String nationality;
	String city;
	String job;
	/**
	 * the image is stored as blob
	 */
	Blob photo;
	
	public User(){};
	public User(String phoneNumber, int gender, int age){
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.age = age;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public Blob getPhoto() {
		return photo;
	}
	public void setPhoto(Blob photo) {
		this.photo = photo;
	}
	
}
