package web;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import spittr.Spitter;
import spittr.data.SpitterRepository;
import spittr.web.SpitterController;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class SpitterControllerTest {

  @Test
  public void shouldShowRegistration() throws Exception {
      SpitterRepository mockRepository = mock(SpitterRepository.class);
      SpitterController controller = new SpitterController(mockRepository); /*构建MockMvc*/
      MockMvc mockMvc = standaloneSetup(controller).build();
      mockMvc.perform(get("/spitter/register"))
           .andExpect(view().name("registerForm"));          /*断言registerForm视图*/
  }

  @Test
  public void shouldProcessRegistration() throws Exception {
    SpitterRepository mockRepository = mock(SpitterRepository.class);   /*构建Repository*/
    Spitter unsaved = new Spitter("jbauer", "24hours", "Jack", "Bauer", "jbauer@ctu.gov");
    Spitter saved = new Spitter(24L, "jbauer", "24hours", "Jack", "Bauer", "jbauer@ctu.gov");
    when(mockRepository.save(unsaved)).thenReturn(saved);

    SpitterController controller = new SpitterController(mockRepository);
    MockMvc mockMvc = standaloneSetup(controller).build();    /*构建MockMvc*/

    mockMvc.perform(post("/spitter/register")    /*执行请求*/
            .param("firstName", "Jack")
            .param("lastName", "Bauer")
            .param("username", "jbauer")
            .param("password", "24hours")
            .param("email", "jbauer@ctu.gov"))
            .andExpect(redirectedUrl("/spitter/jbauer"));

    verify(mockRepository, atLeastOnce()).save(unsaved);   /*校验SpitterRepository的mock实现最终会真正用来保存表单上传入的数据*/
  }

}
