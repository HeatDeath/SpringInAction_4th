package spittr.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import spittr.Spittle;
import spittr.data.SpittleRepository;

@Controller
@RequestMapping("/spittles")
public class SpittleController {

  private static final String MAX_LONG_AS_STRING = "9223372036854775807";
  
  private SpittleRepository spittleRepository;

  @Autowired // 注入 SpittleRepository
  public SpittleController(SpittleRepository spittleRepository) {
    this.spittleRepository = spittleRepository;
  }

  @RequestMapping(method=RequestMethod.GET)
  public List<Spittle> spittles(
      @RequestParam(value="max", defaultValue=MAX_LONG_AS_STRING) long max,
      @RequestParam(value="count", defaultValue="20") int count) {
    /**
     * 处理对 /spittles 的 GET 请求
     * max 存储请求参数中 max 的值， count 存储请求参数中 count 的值
     * 若以上参数在 请求中不存在，则使用默认值
     * 返回在 SpittleRepository 中找到的指定 Spittles 列表
     */
    return spittleRepository.findSpittles(max, count);
  }

  @RequestMapping(value="/{spittleId}", method=RequestMethod.GET)
  public String spittle(
      @PathVariable("spittleId") long spittleId, 
      Model model) {
    /**
     * 处理对 /spittles/{spittleID} 的 GET 请求
     * spittleID 存储 url路径 中的 {spittleID}
     * 在 SpittleRepository 中对应的 Spittle 对象，并添加到 model 中
     * 返回 /WEB-INF/views/spittle.jsp 页面
     */
    model.addAttribute(spittleRepository.findOne(spittleId));
    return "spittle";
  }

  @RequestMapping(method=RequestMethod.POST)
  public String saveSpittle(SpittleForm form, Model model) throws Exception {
    /**
     * 将对 /spittles 的 POST 请求传入的数据打包成 SpittleForm 对象
     * 提取 SpittleForm 对象中的数据，并保存到 SpittleRepository 中
     * 重定向到 /spittles （在页面中将会显示最新的 20 条 spittles）
     */
    spittleRepository.save(new Spittle(null, form.getMessage(), new Date(), 
        form.getLongitude(), form.getLatitude()));
    return "redirect:/spittles";
  }

}
