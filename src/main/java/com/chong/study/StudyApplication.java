package com.chong.study;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
 
@ComponentScan({"com.chong"})
@MapperScan({ "com.chong.study.mapper" })
@SpringBootApplication
// @RestController
public class StudyApplication  {
	public static void main(String[] args) {
		SpringApplication.run(StudyApplication.class, args);
	} 
}