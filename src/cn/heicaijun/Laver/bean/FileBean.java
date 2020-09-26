package cn.heicaijun.Laver.bean;

public class FileBean {
	private String absolutePath;
	private String fatherPath;
	private String fileName;
	FileBean(String fileName,String fatherPath) {
		this.fileName = fileName;
		this.fatherPath = fatherPath;
	}
	public FileBean(String fileName,String fatherPath,String absolutePath) {
		this.fileName = fileName;
		this.fatherPath = fatherPath;
		this.absolutePath = absolutePath;
	}
	public String toString() {
		return fatherPath + "," + fileName + "," + absolutePath;
	}
	public String toAbsoluteString() {
		return absolutePath + "," + fileName;
	}
	public String getAbsolutePath() {
		return absolutePath;
	}
	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}
	public String getFatherPath() {
		return fatherPath;
	}
	public void setFatherPath(String fatherPath) {
		this.fatherPath = fatherPath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}
