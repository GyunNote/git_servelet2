package com.study.array.servelt;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.study.array.entity.DataObj;
import com.study.array.service.DataService;


@WebServlet("/data/addition")
public class DataInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DataService dataService;
	
	public DataInsertServlet() {
		dataService = DataService.getInstance();
	}
       
  	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		StringBuilder json = new StringBuilder();   //이줄을
		String requestData = null;
		
		//문자열 -> 스트림
		BufferedReader reader = request.getReader();
		while((requestData = reader.readLine())!=null) {  //여기에 하나씩 넣음
			json.append(requestData);
		}
		Gson gson = new Gson();
		DataObj dataObj = gson.fromJson(json.toString(), DataObj.class);
		System.out.println(dataObj); //제이슨을 객체 형태로
		
		System.out.println(gson.toJson(dataObj)); //제이슨 형태로
		int responseBody = dataService.addData(dataObj);
		
		Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("data", responseBody);
		responseMap.put("responseMessage","데이터 추가 완료");
		
		response.setContentType("application/json");
		response.setStatus(201);
		response.getWriter().println(gson.toJson(responseMap));
	}

}
