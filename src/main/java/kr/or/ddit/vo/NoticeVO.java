package kr.or.ddit.vo;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Lang;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class NoticeVO {
    private int boNo;
    private String boTitle;
    private String boContent;
    private String boWriter;
    private String boDate;
    private int boHit;

    private Integer[] delFileNo;
    private MultipartFile[] boFile;
    private List<NoticeFileVO> noticeFileList;
    private int fileCount;

    public void setBoFile(MultipartFile[] boFile) {
        this.boFile = boFile;
        if(boFile!=null){
            List<NoticeFileVO> noticeList = new ArrayList<NoticeFileVO>();
            for (MultipartFile item : boFile) {
                if (StringUtils.isBlank(item.getOriginalFilename())) {
                    continue;
                }
                NoticeFileVO noticeFileVO = new NoticeFileVO(item);
                noticeList.add(noticeFileVO);
            }
            this.noticeFileList = noticeList;
        }
    }
}

/*
라이브러리: Apache Commons Lang 3
클래스: StringUtils
한마디 정의: "자바 문자열 작업의 만능 해결사"
🛠️ 왜 이걸 쓸까요?
자바 기본 기능인 String만 쓰면 null 값이 들어왔을 때 프로그램이 멈추는(NullPointerException) 에러가 자주 발생합니다. StringUtils는 이런 상황을 알아서 다 방어해 줍니다.

주요 기능 예시
   - StringUtils.isEmpty(str): str이 null이거나 ""이면 true 반환 (에러 안 남)
   - StringUtils.isBlank(str): 공백("  ")까지 포함해서 비어있는지 체크
   - StringUtils.defaultString(str, "기본값"): str이 null이면 대신 "기본값"을 넣어줌
   - StringUtils.reverse(str): 문자열을 거꾸로 뒤집음
*/
