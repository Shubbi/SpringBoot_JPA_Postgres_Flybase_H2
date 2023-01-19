package com.vishal.springbootjpapostgresql.repository;
import com.vishal.springbootjpapostgresql.entity.TutorialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TutorialRepository extends JpaRepository<TutorialEntity, Long> {
    List<TutorialEntity> findByPublished(boolean published);
    List<TutorialEntity> findByTitleContaining(String title);
}
