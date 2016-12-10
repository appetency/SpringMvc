package spittr.web;

        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        import org.springframework.web.bind.annotation.PathVariable;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        import org.springframework.web.bind.annotation.RequestParam;
        import spittr.Spittle;
        import spittr.data.SpittleRepository;

        import java.util.List;

@Controller
@RequestMapping("/spittles")
public class SpittleController {
    private SpittleRepository spittleRepository;

    private static final String MAX_LONG_AS_STRING = "9223372036854775807";

    @Autowired    /*注入spittleRepository*/
    public SpittleController(SpittleRepository spittleRepository) {
        this.spittleRepository = spittleRepository;
    }

    @RequestMapping(method=RequestMethod.GET)
    public List<Spittle> spittles(
            @RequestParam(value="max", defaultValue=MAX_LONG_AS_STRING) long max,
            @RequestParam(value="count", defaultValue="20") int count) {
        return spittleRepository.findSpittles(max, count);
    }
    @RequestMapping(value="/{spittleId}", method=RequestMethod.GET)
    public String spittle(@PathVariable("spittleId") long spittleId, Model model) {
        model.addAttribute(spittleRepository.findOne(spittleId));
        return "spittle";/*返回视图名*/
    }
    /*spittle()方法会将参数传递到SpittleRepository的findOne()方法中，用来获取某
    个Spittle对象，然后将Spittle对象添加到模型中。模型的key将会是spittle，这是根据
    传递到addAttribute()方法中的类型推断得到的。*/


}
