package soundsystem;
import org.springframework.stereotype.Component;

@Component // 注解表明该类会作为 组件类，并告知 Spring 要为其创建 Bean
public class SgtPeppers implements CompactDisc {
  // SgtPeppers 实现 CompactDisc 接口

  private String title = "Sgt. Pepper's Lonely Hearts Club Band";  
  private String artist = "The Beatles";
  
  public void play() {
    System.out.println("Playing " + title + " by " + artist);
  }
  
}
