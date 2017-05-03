package entity;

import java.io.Serializable;
import java.util.Date;

public class Grade implements Serializable{
	private Long id;

    private String name;

    private String url;

    private int expNum;
    
    private Byte status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getExpNum() {
		return expNum;
	}

	public void setExpNum(int expNum) {
		this.expNum = expNum;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

}