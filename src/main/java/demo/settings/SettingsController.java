package demo.settings;

import java.security.Principal;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class SettingsController {

    @GetMapping("/settings")
    String settings(Principal p, Map<String, Object> model) {
        model.put("username", p == null ? null : p.getName());
        return "settings";
    }

}
