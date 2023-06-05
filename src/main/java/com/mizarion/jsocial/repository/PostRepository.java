package com.mizarion.jsocial.repository;

import com.mizarion.jsocial.model.entity.PostEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, UUID> {
    List<PostEntity> findAllByOwner(String username);

    List<PostEntity> findAllByOwner(String username, Pageable pageable);

    Optional<PostEntity> findById(Long id);

    boolean existsByOwnerAndTitle(String owner, String title);

}