package com.example.wonit.controller;

import org.springframework.web.bind.annotation.*;

// Controller 계층은 사용자의 요청을 받아서 화면에 돌려주는 역할
// 스프링의 어노테이션으로 등록해놓은 클래스들을 Bean
@RestController     // Controller 중 REST API를 처리하는 Controller임을 명시
@RequestMapping("/hello")       // 사용자의 요청에 매핑 -> localhost:8080/hello 요청이 들어오면 아래 HelloController가 처리
// 사용자의 요청(Request)을 주소줄과 매핑해서 서버에 전달하는 역할 => Routing 기능
public class HelloController {

    // 값을 전달하는 방법 3가지

    // 1. GET 요청 : URL 경로에 있는 값을 변수로 받아오는 역할
    @GetMapping // GET 요청을 처리하는 메서드 -> 어떤 방식으로 아래 함수를 호출할지 결정
    public String sayHello() {
        return "HELLO!!!!!!!";
    }

    // 2. PathVariable : URL 경로에 있는 값을 변수로 받아오는 역할
//    @GetMapping("/{path}") // localhost:8080/hello/{path} 요청이 들어오면 아래 HelloController가 처리
//    public String sayHello(@PathVariable String path) { // @PathVariable : URL) 경로에 있는 값을 변수로 받아오는 역할{
//        return "HELLO!!!!!!!" + path; // hello 뒤에 path를 붙여서 반환
//    }

//    // 3. RequestParam : URL 쿼리스트링에 있는 값을 변수로 받아오는 역할
//    @GetMapping // "http://localhost:8080/hello?name=송준표" 요청이 들어오면 아래 HelloController가 처리
//    public String sayHello(@RequestParam(value = "name") String name) { // @RequestParam : URL) 쿼리스트링에 있는 값을 변수로 받아오는 역할
//        return "HELLO!!!!!!!" + name; // hello 뒤에 name 붙여서 반환
//    }

    // 위 3개의 방법 모두 활용
    // locahost:8080/hello -> "HELLO!!!!!!!" 출력
    // loadcast:8080/hello/이름 -> "HELLO!!!!!!!이름 안 들어옴" 출력
    // localhost:8080/hello/이름?name=송준표 -> "HELLO!!!!!!!이름송준표" 출력

//    @GetMapping("/{path}")
//    public String sayHello(@PathVariable String path, @RequestParam(value = "name", defaultValue = " 안 들어옴") String name) { // @RequestParam : URL) 쿼리스트링에 있는 값을 변수로 받아오는 역할
//        return "HELLO!!!!!!!" + path + name; // hello 뒤에 path와 name 붙여서 반환
//    }

    @GetMapping("/{path}")
    public String sayHello(@PathVariable String path,
                           @RequestParam(value = "name", defaultValue = " 안 들어옴") String name,
                           @RequestParam(value = "footsize", defaultValue = "안 들어옴") String footsize) { // @RequestParam : URL) 쿼리스트링에 있는 값을 변수로 받아오는 역할
        return "안녕 Spring boot " + path+ " " + name + ", 신발사이즈는 " + footsize; // hello 뒤에 path와 name 붙여서 반환
    }




}
