package spittr.web;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;

import spittr.Spittle;
import spittr.data.SpittleRepository;
import spittr.web.SpittleController;

public class SpittleControllerTest {

  @Test
  public void shouldShowRecentSpittles() throws Exception {
    List<Spittle> expectedSpittles = createSpittleList(20);

    // 创建 SpittleRepository 接口的 mock 实现
    // 这个实现会从 findSpittles() 方法中返回 20 个 Spittle 对象
    SpittleRepository mockRepository = mock(SpittleRepository.class);
    when(mockRepository.findSpittles(Long.MAX_VALUE, 20))
        .thenReturn(expectedSpittles);

    // 将 mockRepository 注入到一个新的 SpittleController 实例中，创建 MockMVC 并使用这个控制器
    SpittleController controller = new SpittleController(mockRepository);
    MockMvc mockMvc = standaloneSetup(controller)
        .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))
        .build();


    mockMvc.perform(get("/spittles"))
       .andExpect(view().name("spittles"))
       .andExpect(model().attributeExists("spittleList"))
       .andExpect(model().attribute("spittleList", 
                  hasItems(expectedSpittles.toArray())));
  }

  @Test
  public void shouldShowPagedSpittles() throws Exception {
    List<Spittle> expectedSpittles = createSpittleList(50);
    SpittleRepository mockRepository = mock(SpittleRepository.class);
    when(mockRepository.findSpittles(238900, 50))
        .thenReturn(expectedSpittles);
    
    SpittleController controller = new SpittleController(mockRepository);
    MockMvc mockMvc = standaloneSetup(controller)
        .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))
        .build();

    mockMvc.perform(get("/spittles?max=238900&count=50"))
      .andExpect(view().name("spittles"))
      .andExpect(model().attributeExists("spittleList"))
      .andExpect(model().attribute("spittleList", 
                 hasItems(expectedSpittles.toArray())));
  }
  
  @Test
  public void testSpittle() throws Exception {
    Spittle expectedSpittle = new Spittle("Hello", new Date());
    SpittleRepository mockRepository = mock(SpittleRepository.class);
    when(mockRepository.findOne(12345)).thenReturn(expectedSpittle);
    
    SpittleController controller = new SpittleController(mockRepository);
    MockMvc mockMvc = standaloneSetup(controller).build();

    mockMvc.perform(get("/spittles/12345"))
      .andExpect(view().name("spittle"))
      .andExpect(model().attributeExists("spittle"))
      .andExpect(model().attribute("spittle", expectedSpittle));
  }

  @Test
  public void saveSpittle() throws Exception {
    SpittleRepository mockRepository = mock(SpittleRepository.class);
    SpittleController controller = new SpittleController(mockRepository);
    MockMvc mockMvc = standaloneSetup(controller).build();

    mockMvc.perform(post("/spittles")
           .param("message", "Hello World") // this works, but isn't really testing what really happens
           .param("longitude", "-81.5811668")
           .param("latitude", "28.4159649")
           )
           .andExpect(redirectedUrl("/spittles"));
    
    verify(mockRepository, atLeastOnce()).save(new Spittle(null, "Hello World", new Date(), -81.5811668, 28.4159649));
  }
  
  private List<Spittle> createSpittleList(int count) {
    /**
     * 生成 count 数量的 Spittle 对象，返回由其组成的 list
     */
    List<Spittle> spittles = new ArrayList<Spittle>();
    for (int i=0; i < count; i++) {
      spittles.add(new Spittle("Spittle " + i, new Date()));
    }
    return spittles;
  }
}
