package application;

public class Information {
	String date, ip, id, course, event;

	public Information(String date, String course, String event, String id, String ip) {
		this.date = date;
		this.ip = ip;
		this.id = id;
		this.course = course;
		this.event = event;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}
}