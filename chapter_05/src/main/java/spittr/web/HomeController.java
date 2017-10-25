package spittr.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // 基于 @Component 注解，辅助实现组件扫描
@RequestMapping("/")
public class HomeController {
  /**
   * 处理对 "/" 的请求，并渲染应用首页
   */
  @RequestMapping(method = GET)
  public String home(Model model) {
    return "home";
  }

}
