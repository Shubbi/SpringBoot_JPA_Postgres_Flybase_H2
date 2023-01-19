package com.vishal.springbootjpapostgresql.service;
import com.vishal.springbootjpapostgresql.dto.TutorialDto;
import com.vishal.springbootjpapostgresql.entity.TutorialEntity;
import com.vishal.springbootjpapostgresql.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class TutorialService {

    @Autowired
    private TutorialRepository tutorialRepository;

    public List<TutorialDto.RequestResponse.Tutorial> findAll() {
        List<TutorialDto.RequestResponse.Tutorial> tutorials = new ArrayList<>();
        var tutorialEntityList = tutorialRepository.findAll();

        tutorialEntityList.forEach(tutorialEntity -> tutorials.add(TutorialDto.from(tutorialEntity)));
        return tutorials;
    }

    public Optional<TutorialDto.RequestResponse.Tutorial> findById(Long id) {
        var tutorialEntity = tutorialRepository.findById(id);

        if(tutorialEntity.isPresent())
        {
            var tutorial = TutorialDto.from(tutorialEntity.get());
            return Optional.ofNullable(tutorial);
        }

        return Optional.empty();
    }

    public List<TutorialDto.RequestResponse.Tutorial> findByTitleContaining(String title) {
        List<TutorialDto.RequestResponse.Tutorial> tutorials = new ArrayList<>();
        var tutorialEntityList = tutorialRepository.findByTitleContaining(title);

        tutorialEntityList.forEach(tutorialEntity -> tutorials.add(TutorialDto.from(tutorialEntity)));
        return tutorials;
    }

    public List<TutorialDto.RequestResponse.Tutorial> findByPublished(boolean published) {
        var tutorialEntityList = tutorialRepository.findByPublished(published);

        List<TutorialDto.RequestResponse.Tutorial> tutorials = new ArrayList<>();

        tutorialEntityList.forEach(tutorialEntity -> tutorials.add(TutorialDto.from(tutorialEntity)));
        return tutorials;
    }

    public TutorialDto.RequestResponse.Tutorial createTutorial(TutorialDto.RequestResponse.Tutorial tutorial) {
        var tutorialEntity = new TutorialEntity();
        tutorialEntity.setTitle(tutorial.getTitle());
        tutorialEntity.setDescription(tutorial.getDescription());
        tutorialEntity.setPhone(tutorial.getPhone());
        tutorialEntity.setPublished(tutorial.getPublished());

        var _tutorialEntity = tutorialRepository.save(tutorialEntity);
        return TutorialDto.from(_tutorialEntity);
    }

    public Optional<TutorialDto.RequestResponse.Tutorial> updateTutorial(Long id, TutorialDto.RequestResponse.Tutorial tutorial) {
        var _tutorialEntity = tutorialRepository.findById(id);

        if(_tutorialEntity.isPresent())
        {
            var tutorialEntity = _tutorialEntity.get();
            tutorialEntity.setPublished(tutorial.getPublished());
            tutorialEntity.setTitle(tutorial.getTitle());
            tutorialEntity.setDescription(tutorial.getDescription());
            tutorialEntity.setPhone(tutorial.getPhone());
            return Optional.ofNullable(TutorialDto.from(tutorialRepository.save(tutorialEntity)));
        }

        return Optional.empty();
    }

    public Optional<TutorialDto.RequestResponse.Tutorial> deleteTutorial(Long id) {
        var _tutorialEntity = tutorialRepository.findById(id);

        if(_tutorialEntity.isPresent())
        {
            var tutorialEntity = _tutorialEntity.get();
            var tutorial = Optional.ofNullable(TutorialDto.from(tutorialEntity));
            tutorialRepository.deleteById(id);

            return tutorial;
        }

        return Optional.empty();
    }

    public void deleteAllTutorials() {
        tutorialRepository.deleteAll();
    }
}
