package web;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.view.InternalResourceView;
import spittr.Spittle;
import spittr.data.SpittleRepository;
import spittr.web.SpittleController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class SpittleControllerTest {

  @Test
  public void shouldShowRecentSpittles() throws Exception {
    List<Spittle> expectedSpittles = createSpittleList(20);
    SpittleRepository mockRepository = mock(SpittleRepository.class);/*mock repository*/
    when(mockRepository.findSpittles(Long.MAX_VALUE, 20))
        .thenReturn(expectedSpittles);

    SpittleController controller = new SpittleController(mockRepository);
    MockMvc mockMvc = standaloneSetup(controller)/*Mock Spring mvc*/
        .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))    /*调用了 setSingleView()。这样的话，mock框架就不用解析控制器中的视图名了*/
        .build();

    mockMvc.perform(get("/spittles")) /*发起get请求*/
       .andExpect(view().name("spittles"))
       .andExpect(model().attributeExists("spittleList"))
       .andExpect(model().attribute("spittleList",     /*断言期望值*/
                  hasItems(expectedSpittles.toArray())));
  }
  @Test
  public void shouldShowPagedSpittles() throws Exception {
    List<Spittle> expectedSpittles = createSpittleList(50);
    SpittleRepository mockRepository = mock(SpittleRepository.class);
    when(mockRepository.findSpittles(238900, 50))                 /*预期的max和count参数*/
            .thenReturn(expectedSpittles);

    SpittleController controller = new SpittleController(mockRepository);
    MockMvc mockMvc = standaloneSetup(controller)
            .setSingleView(new InternalResourceView("/WEB-INF/views/spittles.jsp"))
            .build();

    mockMvc.perform(get("/spittles?max=238900&count=50"))         /*传入max和count参数*/
            .andExpect(view().name("spittles"))
            .andExpect(model().attributeExists("spittleList"))
            .andExpect(model().attribute("spittleList",
                    hasItems(expectedSpittles.toArray())));
  }
  @Test
  public void testSpittle() throws Exception {
    Spittle expectedSpittle = new Spittle("Hello", new Date());  //返回信息和时间
    SpittleRepository mockRepository = mock(SpittleRepository.class);//模拟信息
    when(mockRepository.findOne(12345)).thenReturn(expectedSpittle); //这样的语法便是设置方法调用的返回值

    SpittleController controller = new SpittleController(mockRepository);
    MockMvc mockMvc = standaloneSetup(controller).build();

    mockMvc.perform(get("/spittles/12345"))
            .andExpect(view().name("spittle"))
            .andExpect(model().attributeExists("spittle"))  ///验证存在spittle属性
            .andExpect(model().attribute("spittle", expectedSpittle));//验证属性相等性
  }
  private List<Spittle> createSpittleList(int count) {
    List<Spittle> spittles = new ArrayList<Spittle>();
    for (int i=0; i < count; i++) {
      spittles.add(new Spittle("Spittle " + i, new Date()));
    }
    return spittles;
  }
}
