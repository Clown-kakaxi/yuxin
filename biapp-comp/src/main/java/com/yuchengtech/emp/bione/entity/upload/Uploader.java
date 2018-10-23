package com.yuchengtech.emp.bione.entity.upload;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class Uploader {
	public CommonsMultipartFile upload;//上传的文件
	public String name;//随机生成的临时名称
	public int chunks;//总块数
	public int chunk;//当前上传的是第几块

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getChunks() {
		return chunks;
	}
	public CommonsMultipartFile getUpload() {
		return upload;
	}
	public void setUpload(CommonsMultipartFile upload) {
		this.upload = upload;
	}
	public void setChunks(int chunks) {
		this.chunks = chunks;
	}
	public int getChunk() {
		return chunk;
	}
	public void setChunk(int chunk) {
		this.chunk = chunk;
	}

}