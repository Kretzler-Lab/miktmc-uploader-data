package org.miktmc.packages;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;

@JsonPropertyOrder({ "originalFileName", "size", "id" })
@Document(collection = "packages")
public class Attachment {

	@Id
	@Field("universalId")
	private String id;
	private long size;
	private String originalFileName;
	private String fileName;
	private String md5checksum;
	private Date replacedOn;

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String filename) {
		this.originalFileName = filename;
	}

	public String getMd5checksum() {
		return md5checksum;
	}

	public void setMd5checksum(String md5checksum) {
		this.md5checksum = md5checksum;
	}

	public Date getReplacedOn() {
		return replacedOn;
	}

	public void setReplacedOn(Date replacedOn) {
		this.replacedOn = replacedOn;
	}
}
