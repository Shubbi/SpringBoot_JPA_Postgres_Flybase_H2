package com.vishal.springbootjpapostgresql;
import static org.assertj.core.api.Assertions.assertThat;

import com.vishal.springbootjpapostgresql.dto.TutorialDto;
import com.vishal.springbootjpapostgresql.entity.TutorialEntity;
import com.vishal.springbootjpapostgresql.service.TutorialService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

//@DataJpaTest
@SpringBootTest
@ComponentScan(basePackages = {"com.vishal.springbootjpapostgresql"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class JPAUnitTest {
    @Autowired
    @Qualifier("tutorialService")
    private TutorialService tutorialService;

    @BeforeAll
    void beforeAllInit() {
        System.out.println("running before all");
    }

    @AfterAll
    void afterAllCleanUp() {
        System.out.println("running after all");
    }

    @BeforeEach
    void init() {
        System.out.println("running before each...");
    }

    @AfterEach
    void cleanUp() {
        System.out.println("running after each...");
        tutorialService.deleteAllTutorials();
    }

    @Test
    @Order(1)
    public void should_find_no_tutorials_if_repository_is_empty() {
        Iterable tutorials = tutorialService.findAll();

        assertThat(tutorials).isEmpty();
    }

    @Test
    @Order(2)
    public void should_store_a_tutorial() {
        TutorialDto.RequestResponse.Tutorial tutorial = tutorialService.createTutorial(new TutorialDto.RequestResponse.Tutorial("Tut title", "Tut desc", true, ""));

        assertThat(tutorial).hasFieldOrPropertyWithValue("title", "Tut title");
        assertThat(tutorial).hasFieldOrPropertyWithValue("description", "Tut desc");
        assertThat(tutorial).hasFieldOrPropertyWithValue("published", true);
    }

    @Test
    @Order(3)
    public void should_find_all_tutorials() {
        TutorialDto.RequestResponse.Tutorial tut1 = tutorialService.createTutorial(new TutorialDto.RequestResponse.Tutorial("Tut#1", "Desc#1", true, ""));

        TutorialDto.RequestResponse.Tutorial tut2 = tutorialService.createTutorial(new TutorialDto.RequestResponse.Tutorial("Tut#2", "Desc#2", false, ""));

        TutorialDto.RequestResponse.Tutorial tut3 = tutorialService.createTutorial(new TutorialDto.RequestResponse.Tutorial("Tut#3", "Desc#3", true, ""));

        Iterable tutorials = tutorialService.findAll();

        assertThat(tutorials).hasSize(3).contains(tut1, tut2, tut3);
    }

    @Test
    @Order(5)
    public void should_find_tutorial_by_id() {
        System.out.println("Vishal -- Executing 5");
        TutorialDto.RequestResponse.Tutorial tut1 = tutorialService.createTutorial(new TutorialDto.RequestResponse.Tutorial("Tut#1", "Desc#1", true, ""));

        TutorialDto.RequestResponse.Tutorial tut2 = tutorialService.createTutorial(new TutorialDto.RequestResponse.Tutorial("Tut#2", "Desc#2", false, ""));

        TutorialDto.RequestResponse.Tutorial foundTutorial = tutorialService.findById(tut2.getId()).get();

        assertThat(foundTutorial).isEqualTo(tut2);
    }

    @Test
    @Order(4)
    public void should_find_published_tutorials() {
        System.out.println("Vishal.......Executing 4");
        TutorialDto.RequestResponse.Tutorial tut1 = tutorialService.createTutorial(new TutorialDto.RequestResponse.Tutorial("Tut#1", "Desc#1", true, ""));

        TutorialDto.RequestResponse.Tutorial tut2 = tutorialService.createTutorial(new TutorialDto.RequestResponse.Tutorial("Tut#2", "Desc#2", false, ""));

        TutorialDto.RequestResponse.Tutorial tut3 = tutorialService.createTutorial(new TutorialDto.RequestResponse.Tutorial("Tut#3", "Desc#3", true, ""));

        Iterable tutorials = tutorialService.findByPublished(true);

        assertThat(tutorials).hasSize(2).contains(tut1, tut3);
    }

    @Test
    @Order(6)
    public void should_find_tutorials_by_title_containing_string() {
        TutorialDto.RequestResponse.Tutorial tut1 = tutorialService.createTutorial(new TutorialDto.RequestResponse.Tutorial("Spring Boot Tut#1", "Desc#1", true, ""));

        TutorialDto.RequestResponse.Tutorial tut2 = tutorialService.createTutorial(new TutorialDto.RequestResponse.Tutorial("Java Tut#2", "Desc#2", false, ""));

        TutorialDto.RequestResponse.Tutorial tut3 = tutorialService.createTutorial(new TutorialDto.RequestResponse.Tutorial("Spring Data JPA Tut#3", "Desc#3", true, ""));

        Iterable tutorials = tutorialService.findByTitleContaining("Tut");

        assertThat(tutorials).hasSize(3).contains(tut1, tut3);
    }

    @Test
    @Order(7)
    public void should_update_tutorial_by_id() {
        TutorialDto.RequestResponse.Tutorial tut1 = tutorialService.createTutorial(new TutorialDto.RequestResponse.Tutorial("Tut#1", "Desc#1", true, ""));

        TutorialDto.RequestResponse.Tutorial tut2 = tutorialService.createTutorial(new TutorialDto.RequestResponse.Tutorial("Tut#2", "Desc#2", false, ""));

        TutorialDto.RequestResponse.Tutorial updatedTut = new TutorialDto.RequestResponse.Tutorial("updated Tut#2", "updated Desc#2", true, "");

        TutorialDto.RequestResponse.Tutorial tut = tutorialService.findById(tut2.getId()).get();
        tut.setTitle(updatedTut.getTitle());
        tut.setDescription(updatedTut.getDescription());
        tut.setPublished(updatedTut.getPublished());
        tutorialService.updateTutorial(tut.getId(), tut);

        TutorialDto.RequestResponse.Tutorial checkTut = tutorialService.findById(tut2.getId()).get();

        assertThat(checkTut.getId()).isEqualTo(tut2.getId());
        assertThat(checkTut.getTitle()).isEqualTo(updatedTut.getTitle());
        assertThat(checkTut.getDescription()).isEqualTo(updatedTut.getDescription());
        assertThat(checkTut.getPublished()).isEqualTo(updatedTut.getPublished());
    }

    @Test
    @Order(8)
    public void should_delete_tutorial_by_id() {
        TutorialDto.RequestResponse.Tutorial tut1 = tutorialService.createTutorial(new TutorialDto.RequestResponse.Tutorial("Spring Boot Tut#1", "Desc#1", true, ""));

        TutorialDto.RequestResponse.Tutorial tut2 = tutorialService.createTutorial(new TutorialDto.RequestResponse.Tutorial("Java Tut#2", "Desc#2", false, ""));

        TutorialDto.RequestResponse.Tutorial tut3 = tutorialService.createTutorial(new TutorialDto.RequestResponse.Tutorial("Spring Data JPA Tut#3", "Desc#3", true, ""));

        tutorialService.deleteTutorial(tut2.getId());

        Iterable tutorials = tutorialService.findAll();

        assertThat(tutorials).hasSize(2).contains(tut1, tut3);
    }

    @Test
    @Order(9)
    public void should_delete_all_tutorials() {
        TutorialDto.RequestResponse.Tutorial tut1 = tutorialService.createTutorial(new TutorialDto.RequestResponse.Tutorial("Spring Boot Tut#1", "Desc#1", true, ""));

        TutorialDto.RequestResponse.Tutorial tut2 = tutorialService.createTutorial(new TutorialDto.RequestResponse.Tutorial("Java Tut#2", "Desc#2", false, ""));

        TutorialDto.RequestResponse.Tutorial tut3 = tutorialService.createTutorial(new TutorialDto.RequestResponse.Tutorial("Spring Data JPA Tut#3", "Desc#3", true, ""));

        tutorialService.deleteAllTutorials();

        assertThat(tutorialService.findAll()).isEmpty();
    }
}

