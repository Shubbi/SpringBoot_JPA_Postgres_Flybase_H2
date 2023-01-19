package com.vishal.springbootjpapostgresql.controller;
import com.vishal.springbootjpapostgresql.dto.TutorialDto;
import com.vishal.springbootjpapostgresql.service.TutorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TutorialController {
    @Autowired
    TutorialService tutorialService;

    @GetMapping("/tutorials")
    public ResponseEntity<List<TutorialDto.RequestResponse.Tutorial>> getAllTutorials(@RequestParam(required = false) String title) {
        try {

            List<TutorialDto.RequestResponse.Tutorial> tutorials = new ArrayList<TutorialDto.RequestResponse.Tutorial>();

            if (title == null)
                tutorialService.findAll().forEach(tutorials::add);
            else
                tutorialService.findByTitleContaining(title).forEach(tutorials::add);

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/tutorials/{id}")
    public ResponseEntity<TutorialDto.RequestResponse.Tutorial> getTutorialById(@PathVariable("id") long id) {
        Optional<TutorialDto.RequestResponse.Tutorial> tutorialData = tutorialService.findById(id);

        if (tutorialData.isPresent()) {
            return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/tutorials")
    public ResponseEntity<TutorialDto.RequestResponse.Tutorial> createTutorial(@RequestBody TutorialDto.RequestResponse.Tutorial tutorial) {
        try {
            TutorialDto.RequestResponse.Tutorial _tutorial = tutorialService.createTutorial(tutorial);
            return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
        }
        catch (Exception ex)
        {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<TutorialDto.RequestResponse.Tutorial> updateTutorial(@PathVariable("id") long id, @RequestBody TutorialDto.RequestResponse.Tutorial tutorial) {
        Optional<TutorialDto.RequestResponse.Tutorial> tutorialData = tutorialService.updateTutorial(id, tutorial);

        if (tutorialData.isPresent()) {
            return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/tutorials/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        try {
            tutorialService.deleteTutorial(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/tutorials")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        try {
            tutorialService.deleteAllTutorials();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/tutorials/published")
    public ResponseEntity<List<TutorialDto.RequestResponse.Tutorial>> findByPublished() {
        try {
            List<TutorialDto.RequestResponse.Tutorial> tutorials = tutorialService.findByPublished(true);

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
