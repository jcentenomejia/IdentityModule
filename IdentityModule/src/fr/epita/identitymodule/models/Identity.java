package fr.epita.identitymodule.models;

public class Identity {
	private String uid;
	private String displayname;
	private String email;
	private String birthDate;
	private String password;
	private String userType;
	
	public Identity(String uid, String displayname, String email, String birthDate, String password, String userType) {
		
		this.uid = uid;
		this.displayname = displayname;
		this.email = email;
		this.birthDate = birthDate;
		this.password = password;
		this.userType = userType;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public void setUserType(String userType){
		this.userType = userType;
	}
	
	public String getUserType(){
		return this.userType;
	}
	
	@Override
	public String toString() {
		return "Identity [uid=" + uid + ", displayname=" + displayname + ", email=" + email + " bithdate: " + birthDate + "]";
	}
}
