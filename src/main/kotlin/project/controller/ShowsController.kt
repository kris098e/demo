package project.controller

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import project.repository.ShowsRepo

@Controller("/api/shows")
class ShowsController(
    private val showsRepo: ShowsRepo
) {

    @Get("/all")
    fun getAllShows(): String {
        return showsRepo.fetchAllShows().toString()
    }
}