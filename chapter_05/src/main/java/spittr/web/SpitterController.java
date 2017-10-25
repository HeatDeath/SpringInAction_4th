package spittr.web;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import spittr.Spitter;
import spittr.data.SpitterRepository;

@Controller
@RequestMapping("/spitter")
public class SpitterController {

  private SpitterRepository spitterRepository;

  @Autowired // 注入 SpitterRepository
  public SpitterController(SpitterRepository spitterRepository) {
    this.spitterRepository = spitterRepository;
  }
  
  @RequestMapping(value="/register", method=GET)
  public String showRegistrationForm() {
    /**
     * 请求url为： /spitter/register/
     * 请求方法为： GET
     * 返回 /WEB-INF/views/registerForm.jsp 页面
     */
    return "registerForm";
  }
  
  @RequestMapping(value="/register", method=POST)
  public String processRegistration(
      @Valid Spitter spitter,  // 校验 Spitter 输入
      Errors errors) {
    if (errors.hasErrors()) {  // 如果校验出现错误，则重新返回表单
      return "registerForm";
    }

    // 保存用户注册的 Spitter
    spitterRepository.save(spitter);

    // 重定向到用户基本信息页面
    return "redirect:/spitter/" + spitter.getUsername();
  }

  // 通过 GET 请求的 url 路径，获取 username 参数
  @RequestMapping(value="/{username}", method=GET)
  public String showSpitterProfile(@PathVariable String username, Model model) {

    // 通过 username 在 spitterRepository 中查找到对应的 spitter 对象
    Spitter spitter = spitterRepository.findByUsername(username);

    /**
     * Model 实际上是一个 Map，它会传递给视图，这样数据就能渲染到客户端
     * 当调用 addAttribute() 方法且不指定 key，那么 key 会根据值的对象类型推断确定
     */
    // 在 model 中添加找到的 Spitter 对象
    model.addAttribute(spitter);

    // 返回 /WEB-INF/views/profile.jsp 页面
    return "profile";
  }
  
}
