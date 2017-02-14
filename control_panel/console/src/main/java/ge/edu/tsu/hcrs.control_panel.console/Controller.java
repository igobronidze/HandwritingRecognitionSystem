package ge.edu.tsu.hcrs.control_panel.console;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@RestController
public class Controller {
    @RequestMapping("/hello/{name}")
    String hello(@PathVariable String name) {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        return "Hello, " + name + "!";
    }
}
