package com.example.demo;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableOAuth2Sso
public class HelloController {	
	
	@RequestMapping("/home")
	public  Map<String, String> home() {
		Map< String,String> hm =  
                new HashMap< String,String>();
		hm.put("homeValue", "home");
		return hm;
//	    return "Hello World";
	  }
//	@CrossOrigin(origins="*", allowedHeaders="*", allowCredentials="true")
	
	@RequestMapping("/user")
//	@PreAuthorize("hasRole('customer')")
	Object user(Principal user) {
		System.out.println(user.toString());
//		  OAuth2Authentication authentication = (OAuth2Authentication) user;
//		  System.out.println(authentication.getDetails());
//		  Object str = authentication.getDetails(); System.out.println(str); JSONObject jsonObj =
//		  new JSONObject(str);
//		  System.out.println(jsonObj);
		return user;
//		return authentication.getDetails();
	}
	@GetMapping("/admin")
//	@PreAuthorize("hasRole('admin')")
	Map<String, String> admins(Principal admin) {
//		return "Hello " + admin.getName();
		Map< String,String> hm =  
                new HashMap< String,String>();
		hm.put("adminValue", "admin");
		return hm;
	}
	
	@RequestMapping("/random")
	public Map<String, String> random() {
		Map< String,String> hm =  
                new HashMap< String,String>();
		hm.put("randomValue", "random");
		return hm;
		
		}
	
	
	public static Map<String,Object> parseJSONObjectToMap(JSONObject jsonObject) throws JSONException{
	    Map<String, Object> mapData = new HashMap<String, Object>();
	    Iterator<String> keysItr = jsonObject.keys();
	        while(keysItr.hasNext()) {
	            String key = keysItr.next();
	            Object value = jsonObject.get(key);

	            if(value instanceof JSONArray) {
	                value = parseJSONArrayToList((JSONArray) value);
	            }else if(value instanceof JSONObject) {
	                value = parseJSONObjectToMap((JSONObject) value);
	            }
	            mapData.put(key, value);
	        }
	    return mapData;
	}

	public static List<Object> parseJSONArrayToList(JSONArray array) throws JSONException {
	    List<Object> list = new ArrayList<Object>();
	    for(int i = 0; i < array.length(); i++) {
	        Object value = array.get(i);
	        if(value instanceof JSONArray) {
	            value = parseJSONArrayToList((JSONArray) value);
	        }else if(value instanceof JSONObject) {
	            value = parseJSONObjectToMap((JSONObject) value);
	        }
	        list.add(value);
	    }
	    return list;
	}
	
}
