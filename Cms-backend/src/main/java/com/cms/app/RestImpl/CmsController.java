package com.cms.app.RestImpl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CmsController {

    @GetMapping("/")
    public String welcome() {
        return "welcome";
    }
}

