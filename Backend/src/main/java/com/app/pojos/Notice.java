package com.app.pojos;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="notice_table")
@NoArgsConstructor
@Setter
@Getter
@ToString
public class Notice extends BaseEntity {

	//id : inherited
	//title of the file 
	private String title;
	
	private String fileName;
	
	private String type;

    @Lob
    private byte[] data;
    
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public LocalDate getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(LocalDate uploadTime) {
		this.uploadTime = uploadTime;
	}

	public LocalDate getNoticeExpiryDate() {
		return noticeExpiryDate;
	}

	public void setNoticeExpiryDate(LocalDate noticeExpiryDate) {
		this.noticeExpiryDate = noticeExpiryDate;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	private LocalDate uploadTime=LocalDate.now();
    
    private LocalDate noticeExpiryDate;
    
    @Transient
    private Boolean flag=false;

    
}
