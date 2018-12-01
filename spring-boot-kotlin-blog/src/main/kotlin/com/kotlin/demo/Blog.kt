package com.kotlin.demo

import org.hibernate.annotations.CreationTimestamp
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Repository
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Post(@Id var id: Int = 0,
                var title: String = "",
                var topic: String = "",
                var content: String = "",
                @CreationTimestamp var created_date: Date = Date())

@Repository
interface BlogRepository : JpaRepository<Post, Int>

@Controller
class BlogController(var blogRepository: BlogRepository) {

    @GetMapping("/")
    fun home(model: Model): String{
        model.addAttribute("posts", blogRepository.findAll())
        return "index"
    }

    @GetMapping("/about")
    fun about(model: Model): String{
        return "about"
    }

    @GetMapping("/post/{id}")
    fun post(@PathVariable("id") id: Int, model: Model): String{
        model.addAttribute("post", blogRepository.findById(id).get())
        return "post"
    }

    @GetMapping("/contact")
    fun contact(model: Model): String{
        return "contact"
    }

    @PostMapping("/post")
    fun addPost(@ModelAttribute post: Post): String {
        blogRepository.save(post)
        return "redirect:/"
    }
}