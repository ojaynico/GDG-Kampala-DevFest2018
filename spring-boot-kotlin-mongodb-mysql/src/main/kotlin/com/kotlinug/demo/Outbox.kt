package com.kotlinug.demo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Repository
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import javax.persistence.Entity
import javax.persistence.Id

// Outbox data class for mysql
@Entity // for mysql
//@Document // for mongodb
data class Outbox(
        //@Id var id: String? = null, // for mongodb
        @Id var id: Int = 0, // for mysql
        var name: String = "",
        var company: String = "",
        var experience: String = "",
        var age: Int = 0
)

// Our outbox repository to handle data persistence

// This is for SQL
@Repository
interface OutboxRepository : JpaRepository<Outbox, Int>

// This is for mongodb
/*
@Repository
interface OutboxRepository : MongoRepository<Outbox, String>
*/

// Our Outbox controller - for routes and other other http stuff
@Controller
class OutboxController(var outboxRepository: OutboxRepository) {

    @GetMapping("/")
    fun home(model: Model, outbox: Outbox): String {
        model.addAttribute("name", "Nicodemus Ojwee")
        return "home"
    }

    @GetMapping("/registered")
    fun registered(model: Model): String {
        model.addAttribute("outbox_list", outboxRepository.findAll())
        return "registered"
    }

    @GetMapping("/info")
    fun info(model: Model): String {
        model.addAttribute("heading", "We love kotlin!")
        model.addAttribute("paragraph", "You can do lots of projects using kotlin " +
                "and share the same code throughout the projects. " +
                "Don't forget to try out kotlinJS, kotlin react, kotlin android, kotlin with iOS, kotlin native, kotlin jvm.")
        model.addAttribute("amazing", "Wow wow wow")
        return "info"
    }

    @PostMapping("/add")
    fun add(@ModelAttribute outbox: Outbox): String {
        outboxRepository.save(outbox)
        return "redirect:/registered"
    }

}


