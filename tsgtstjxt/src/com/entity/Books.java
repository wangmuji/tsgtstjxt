package com.entity;

import com.util.VeDate;

public class Books {
	private String booksid = "B" + VeDate.getStringId();
	private String booksname;
	private String image;
	private String cateid;
	private String publisher;
	private String author;
	private String addtime;
	private String storage;
	private String lendnum;
	private String weizhi;
	private String contents;
	private String catename;

	public String getBooksid() {
		return booksid;
	}

	public void setBooksid(String booksid) {
		this.booksid = booksid;
	}

	public String getBooksname() {
		return this.booksname;
	}

	public void setBooksname(String booksname) {
		this.booksname = booksname;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCateid() {
		return this.cateid;
	}

	public void setCateid(String cateid) {
		this.cateid = cateid;
	}

	public String getPublisher() {
		return this.publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAddtime() {
		return this.addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getStorage() {
		return this.storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public String getLendnum() {
		return this.lendnum;
	}

	public void setLendnum(String lendnum) {
		this.lendnum = lendnum;
	}

	public String getWeizhi() {
		return this.weizhi;
	}

	public void setWeizhi(String weizhi) {
		this.weizhi = weizhi;
	}

	public String getContents() {
		return this.contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getCatename() {
		return this.catename;
	}

	public void setCatename(String catename) {
		this.catename = catename;
	}
}
/**
 * 此程序作者 QQ:549710689 如有修改请联系本QQ
 */
