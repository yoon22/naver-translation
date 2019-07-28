package com.osf.sp.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.osf.sp.dao.impl.NaverTranslationDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class NaverTranslationController {
	@Resource
	private NaverTranslationDAO ntdao;

	@RequestMapping(value = "/trans", method = RequestMethod.GET)
	public String trans(Model m) throws IOException, SQLException {
		List<Map<String,Object>> clobData = ntdao.selectOrderByCount();
		for(int i=0;i<clobData.size();i++) {	
			clobData.get(i).put("TH_RESULT", clobToStr((Clob)clobData.get(i).get("TH_RESULT")));
		}
		m.addAttribute("countList",clobData);
		return "trans";
	}
	
	 @RequestMapping(value="/translations", method=RequestMethod.GET)
	   public @ResponseBody List<Map<String,Object>> getTranslations(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException, SQLException{
	      List<Map<String,Object>> tList = ntdao.selectOrderByCount();
	      for(int i=0; i<tList.size(); i++) {
	         tList.get(i).put("TH_RESULT", clobToStr((Clob)tList.get(i).get("TH_RESULT")));
	      }

	      return tList;
	   }
	
	 //CLOB 데이터 읽어오기
	 public static String clobToStr(Clob clob) throws IOException, SQLException{
		  BufferedReader contentReader = new BufferedReader(clob.getCharacterStream());
		  StringBuffer out = new StringBuffer();
		  String aux;
		  while ((aux=contentReader.readLine())!=null) {
		   out.append(aux);
		      }
		  return out.toString();

		 }
	
	
	

	@RequestMapping(value = "/translation/{source}/{target}/{text}", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> doTranslation(@PathVariable("target") String target,
			@PathVariable("source") String source, @PathVariable("text") String text) throws IOException, SQLException {
		log.info("target=>{}, source=>{}, text=>{}", new String[] { target, source, text });
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("target", target);
		param.put("source", source);
		param.put("text", text);
		Map<String, Object> rMap = ntdao.selectTranslationHisOne(param);
		List<Map<String,Object>> cMap = ntdao.selectOrderByCount();
		log.info("======================>{}", rMap);
		log.info("======================>{}", cMap);
		if (rMap == null) {
			rMap = translationTest(param);
			param.put("result", ((Map) ((Map) rMap.get("message")).get("result")).get("translatedText").toString());
			ntdao.insertOne(param);
			
		} else {
			log.info("rMap : {}", rMap);
			ntdao.updateOne(rMap);
			rMap.put("TH_RESULT",clobToStr((Clob)rMap.get("TH_RESULT")));
			return rMap;
		}
		System.out.println(ntdao.selectOrderByCount());
		return param;
	}
	
	

	private Map<String, Object> translationTest(Map<String, Object> param) {
		String clientId = "";// 애플리케이션 클라이언트 아이디값";
		String clientSecret = "";// 애플리케이션 클라이언트 시크릿값";
		String text = (String) param.get("text");
		String source = (String) param.get("source");
		String target = (String) param.get("target");
		try {
			text = URLEncoder.encode(text, "UTF-8");
			String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("X-Naver-Client-Id", clientId);
			con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
			// post request
			String postParams = "source=" + source + "&target=" + target + "&text=" + text;
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(postParams);
			wr.flush();
			wr.close();
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if (responseCode == 200) { // 정상 호출
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else { // 에러 발생
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			ObjectMapper om = new ObjectMapper();
			br.close();
			Map<String, Object> rMap = om.readValue(response.toString(), Map.class);

			return rMap;
		} catch (Exception e) {
			log.info("err=>{}", e);
		}
		return null;
	}

}
