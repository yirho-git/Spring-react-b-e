package kr.or.ddit.controller;

import kr.or.ddit.vo.NoticeVO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/api/react/notice")
public class NoticeAPIController {

    @PostMapping("/list")
    public ResponseEntity<List<NoticeVO>> noticeList(){

        return null;
    }


}
