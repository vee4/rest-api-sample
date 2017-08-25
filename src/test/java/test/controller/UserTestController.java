package test.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.telecom.hz.sample.App;
import com.telecom.hz.sample.domain.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@WebAppConfiguration
public class UserTestController {
	
	private MockMvc mvc;
	
	@Autowired
	private WebApplicationContext ctx;
	
	@Before
	public void setup() {
		this.mvc = webAppContextSetup(ctx).build();
	}
	
	@Test
	public void testExample() throws Exception {
		
		User user = new User("admin@g.cn", "12121122");
//		JSONObject object = new JSONObject();
//		object.put("account", "admin@g.cn");
//		object.put("password", "12121122");
		System.out.println(JSONObject.toJSONString(user));
		this.mvc.perform(post("/api/v1/auth/login")
				.param("user",JSONObject.toJSONString(user)).contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();
	}
}






