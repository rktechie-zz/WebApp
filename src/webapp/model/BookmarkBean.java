package webapp.model;

public class BookmarkBean {
	private String email;
	private String url;
	private String comment;
	private int count;
	
	public BookmarkBean() {
		email = "";
		url = "";
		comment = "";
		count = 1;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
}
