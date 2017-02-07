package fr.epita.identitymodule.models;

public class Identity {
	private String uid;
	private String displayname;
	private String email;
	private String birthDate;

	public Identity(String uid, String displayname, String email, String birthDate) {
		
		this.uid = uid;
		this.displayname = displayname;
		this.email = email;
		this.birthDate = birthDate;
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
	
	@Override
	public String toString() {
		return "Identity [uid=" + uid + ", displayname=" + displayname + ", email=" + email + " bithdate: " + birthDate + "]";
	}
}
