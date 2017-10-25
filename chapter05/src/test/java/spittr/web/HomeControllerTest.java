package spittr.web;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import spittr.web.HomeController;

public class HomeControllerTest {

  @Test
  public void testHomePage() throws Exception {
    HomeController controller = new HomeController();
    // 搭建 MockMVC
    MockMvc mockMvc = standaloneSetup(controller).build();
    // 对"/" 执行 GET 请求
    mockMvc.perform(get("/"))
            // 预期得到的 "home" 视图
           .andExpect(view().name("home"));
  }

}
