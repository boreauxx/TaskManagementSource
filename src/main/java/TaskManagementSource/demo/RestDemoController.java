package TaskManagementSource.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class RestDemoController {

    private RestDemoService userService;

    @Autowired
    public void setRestDemoService(RestDemoService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/all", method = RequestMethod.GET)
    @GetMapping("/users/all")
    public String getAll() {
        return this.userService.getAll();
    }

}
