package life.fallingu.commuity.commuity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @RequestMapping("/")
    public String index(){
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select * from user");
        System.out.println(maps);
        return "index";
    }
}
