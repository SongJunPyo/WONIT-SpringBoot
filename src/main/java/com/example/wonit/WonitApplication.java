package com.example.wonit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// 입구 파일 -> 웬만하면 수정할 필요 없음
@SpringBootApplication
public class WonitApplication {

	public static void main(String[] args) {
		SpringApplication.run(WonitApplication.class, args);        // run: static 메서드라서 객체 생성 없이 바로 호출 가능
	}

}
