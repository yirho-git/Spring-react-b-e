package kr.or.ddit.vo;

/*
라이브러리: Apache Commons IO
	- 클래스: FileUtils
	- 한마디 정의: "파일 복사, 삭제, 읽기, 쓰기를 위한 치트키"
	🛠️주요 기능 (왜 쓰나요?)
	- 파일 읽기/쓰기 (한 줄 컷)
	readFileToString(file, "UTF-8"): 파일 내용을 통째로 문자열로 읽어옵니다.
	writeStringToFile(file, data, "UTF-8"): 문자열을 파일로 바로 저장합니다.
	- 폴더 및 파일 복사/이동
	copyDirectory(srcDir, destDir): 폴더 전체를 다른 곳으로 복사합니다. (자바 기본 기능으로는 직접 구현하기 매우 까다롭습니다.)
	moveFile(srcFile, destFile): 파일을 이동시킵니다.
	- 삭제 및 정리
	deleteDirectory(dir): 폴더 안에 파일이 들어있어도 강제로 싹 다 지워버립니다.
	cleanDirectory(dir): 폴더 자체는 남겨두고 내용물만 비웁니다.
	- 크기 계산
	byteCountToDisplaySize(size): 파일 크기를 10MB, 2GB 처럼 사람이 읽기 편한 단위로 변환해 줍니다.
*/
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

public class NoticeFileVO {
	private MultipartFile item;
	private Integer boNo;
	private Integer fileNo; 
	private String fileName;
	private Long fileSize;
	private String fileFancysize;
	private String fileMime;
	private String fileSavepath;
	private Integer fileDowncount;
	
	public NoticeFileVO(){}
	
	public NoticeFileVO(MultipartFile item){
		this.item =item;
		fileName=item.getOriginalFilename();
		fileSize=item.getSize();
		fileMime=item.getContentType();
		fileFancysize = FileUtils.byteCountToDisplaySize(fileSize);
	}
	public MultipartFile getItem() {
		return item;
	}
	public void setItem(MultipartFile item) {
		this.item = item;
	}
	public Integer getBoNo() {
		return boNo;
	}
	public void setBoNo(Integer boNo) {
		this.boNo = boNo;
	}
	public Integer getFileNo() {
		return fileNo;
	}
	public void setFileNo(Integer fileNo) {
		this.fileNo = fileNo;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileFancysize() {
		return fileFancysize;
	}
	public void setFileFancysize(String fileFancysize) {
		this.fileFancysize = fileFancysize;
	}
	public String getFileMime() {
		return fileMime;
	}
	public void setFileMime(String fileMime) {
		this.fileMime = fileMime;
	}
	public String getFileSavepath() {
		return fileSavepath;
	}
	public void setFileSavepath(String fileSavepath) {
		this.fileSavepath = fileSavepath;
	}
	public Integer getFileDowncount() {
		return fileDowncount;
	}
	public void setFileDowncount(Integer fileDowncount) {
		this.fileDowncount = fileDowncount;
	}
	
}

