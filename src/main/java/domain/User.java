package domain;

import java.sql.Blob;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	public static Integer GENDER_MALE = 0;
	public static Integer GENDER_FEMALE = 1;
	public static Integer GENDER_NULL = 2;
	
	/**
	 * user phone number, unique primary key
	 */
	String phoneNumber;
	String password;
	/**
	 * gender info. 
	 * GENDER_MALE = 0; GENDER_FEMALE = 1; GENDER_NULL = 2.
	 */
	byte gender;
	byte age;
	String name;
	String nationality;
	String city;
	String job;
	/**
	 * the image is stored as url
	 */
	String photo;
	
	/**
	 * return JsonObject format String, containg name, gender, age, nationality, city, job, photo
	 */
	public JSONObject toQueryJson(){
		JSONObject user = new JSONObject();
		try {
			user.put("phoneNumber", phoneNumber);
			user.put("name", name);
			user.put("gender", gender);
			user.put("age", age);
			user.put("nationality", nationality);
			user.put("city", city);
			user.put("job", job);
			user.put("photo", photo);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public User(){};
	public User(String phoneNumber, byte gender, byte age){
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
	public byte getGender() {
		return gender;
	}
	public void setGender(byte gender) {
		this.gender = gender;
	}
	public byte getAge() {
		return age;
	}
	public void setAge(byte age) {
		this.age = age;
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
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	
}
