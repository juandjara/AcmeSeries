package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
@Access(AccessType.PROPERTY)
public class RoleForm {

	private String role;
	private int staffId;
	private int mediaId;
	private int id;
	
	@NotBlank
	@Pattern(regexp = "^WRITER|DIRECTOR|COMPOSER|ACTOR$")
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Min(1)
	public int getStaffId() {
		return staffId;
	}
	public void setStaffId(int staffId) {
		this.staffId = staffId;
	}
	@Min(1)
	public int getMediaId() {
		return mediaId;
	}
	public void setMediaId(int mediaId) {
		this.mediaId = mediaId;
	}
	@Min(0)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
