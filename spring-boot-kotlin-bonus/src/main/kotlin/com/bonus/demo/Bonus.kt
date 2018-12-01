package com.bonus.demo

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class BonusController{

    @GetMapping("/")
    fun one(): String {
        return "index"
    }
}