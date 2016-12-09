package spittr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import spittr.Spitter;
import spittr.data.SpitterRepository;

@Controller
@RequestMapping("/spitter")
/*showRegistrationForm()方法的@RequestMapping注解以及类级别上的 @RequestMapping注解组合起来，声明了这个方法要处理的是针对“/spitter/register”的GET 请求。
这是一个简单的方法，没有任何输入并且只是返回名为registerForm的逻辑视图。按 照我们配置InternalResourceViewResolver的方式，
这意味着将会使用“/WEB-INF/views/registerForm.jsp”这个JSP来渲染注册表单。*/
public class SpitterController {
    private SpitterRepository spitterRepository;
    @Autowired
    public SpitterController(SpitterRepository spitterRepository){  //注入
        this.spitterRepository=spitterRepository;
    }
 @RequestMapping(value = "/register",method = RequestMethod.GET)   /*处理对"/spitter/register的GET请求"*/
  public String showRegistrationForm(){
   return "registerForm";
 }
 @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String processRegistration(Spitter spitter){
     spitterRepository.save(spitter);
     return "redirect:/spitter"+spitter.getUsername(); }
     //重定向到基本页信息
     /*当InternalResourceViewResolver看到视图格式中的“redirect:”前缀时，它就知道要将
     其解析为重定向的规则，而不是视图的名称。在本例中，它将会重定向到用户基本信息的页
     面。例如，如果Spitter.username属性的值为“jbauer”，那么视图将会重定向
     到“/spitter/jbauer”。*/
 /*@RequestMapping(value="/register", method=RequestMethod.POST)
 public String processRegistration(
         @Valid Spitter spitter,
         Errors errors) {
     if (errors.hasErrors()) {
         return "registerForm";
     }

     spitterRepository.save(spitter);
     return "redirect:/spitter/" + spitter.getUsername();
 }*/

    @RequestMapping(value="/{username}", method=RequestMethod.GET)
    public String showSpitterProfile(@PathVariable String username, Model model) {
        Spitter spitter = spitterRepository.findByUsername(username);
        model.addAttribute(spitter);
        return "profile";
    }



}
