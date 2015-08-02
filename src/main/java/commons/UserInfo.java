package commons;

public class UserInfo {
  private String phoneNumber;
  private String verifyCode;
  private String password;
  private String username;
  private Integer gender;
  private String job;
  private Integer age;
  
  public UserInfo(String p, String v, String pwd, String u, Integer g, String j, Integer a) {
    phoneNumber = p;
    verifyCode = v;
    password = pwd;
    username = u;
    gender = g;
    job = j;
    age = a;
  }
  
  @Override
  public String toString() {
    return "User: " + "\n" 
        + "  phoneNumber:\t" + phoneNumber
        + "  verifyCode:\t" + verifyCode
        + "  password:\t" + password
        + "  username:\t" + username
        + "  gender:\t" + gender
        + "  job:\t" + job
        + "  age:\t" + age;
  }
  
  public String getPhoneNumber() {
    return phoneNumber;
  }



  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }



  public String getVerifyCode() {
    return verifyCode;
  }



  public void setVerifyCode(String verifyCode) {
    this.verifyCode = verifyCode;
  }



  public String getPassword() {
    return password;
  }



  public void setPassword(String password) {
    this.password = password;
  }



  public String getUsername() {
    return username;
  }



  public void setUsername(String username) {
    this.username = username;
  }



  public int getGender() {
    return gender;
  }



  public void setGender(Integer gender) {
    this.gender = gender;
  }



  public String getJob() {
    return job;
  }



  public void setJob(String job) {
    this.job = job;
  }



  public int getAge() {
    return age;
  }



  public void setAge(Integer age) {
    this.age = age;
  }

}
