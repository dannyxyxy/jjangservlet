package com.jjangplay.image.vo;

import lombok.Data;



// DB안의 Board table 의 한행의 데이터을 저장할 수 있는 클래스 
public class ImageVO {

		private Long no;
		private String title;
		private String content;
		private String id;
		private String name;
		private String writeDate;
		private String fileName;
		public Long getNo() {
			return no;
		}
		public void setNo(Long no) {
			this.no = no;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getWriteDate() {
			return writeDate;
		}
		public void setWriteDate(String writeDate) {
			this.writeDate = writeDate;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
	
}
