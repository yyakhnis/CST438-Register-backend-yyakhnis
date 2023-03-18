package com.cst438.domain;

public class StudentDTO {
	public int student_id;
	public String name;
	public String email;
	public String status;
	public int status_code;
	
	public StudentDTO() {
		this.student_id = 0;
		this.name = null;
		this.email=null;
		this.status="No Holds";
		this.status_code = 0;
	}
	
	
	public StudentDTO(int student_id, String name, String email, String status, int status_code) {
		this.student_id = student_id;
		this.name = name;
		this.email=email;
		this.status=status;
		this.status_code = status_code;
	}


	@Override
	public String toString() {
		return "StudentDTO [student_id=" + student_id + ", name=" + name + ", email=" + email
				+ ", status=" + status + ", status_code=" + status_code + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentDTO other = (StudentDTO) obj;
		if (student_id != other.student_id)
			return false;
		if (status_code != other.status_code)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

}