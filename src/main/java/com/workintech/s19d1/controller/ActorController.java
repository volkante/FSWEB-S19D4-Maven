package com.workintech.s19d1.controller;

import com.workintech.s19d1.dto.ActorRequest;
import com.workintech.s19d1.dto.ActorResponse;
import com.workintech.s19d1.entity.Actor;
import com.workintech.s19d1.entity.Movie;
import com.workintech.s19d1.service.ActorService;
import com.workintech.s19d1.service.ActorServiceImpl;
import com.workintech.s19d1.util.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/actor")
public class ActorController {

    private ActorService actorService;

    @Autowired
    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping
    public List<ActorResponse> findAll(){
        List<Actor> actors = actorService.findAll();
        return Converter.toActorResponseList(actors);
    }

    @GetMapping("/{id}")
    public ActorResponse findById(@PathVariable Long id){
        return Converter.toActorResponse(actorService.findById(id));
    }

    @PostMapping
    public ActorResponse save(@RequestBody ActorRequest actorRequest){
        Actor actor = actorRequest.getActor();
        List<Movie> movies = actorRequest.getMovies();
        for(Movie movie : movies){
            actor.addMovie(movie);
        }
        actorService.save(actor);
        return Converter.toActorResponse(actor);
    }

    @PutMapping("/{id}")
    public ActorResponse update(@PathVariable Long id, @RequestBody Actor actor){
        actorService.findById(id);
        actor.setId(id);
        actorService.save(actor);
        return Converter.toActorResponse(actor);
    }

    @DeleteMapping("/{id}")
    public ActorResponse delete(@PathVariable Long id){
        Actor actor = actorService.findById(id);
        actorService.delete(actor);
        return Converter.toActorResponse(actor);
    }


}
