package com.mizarion.jsocial.repository;

import com.mizarion.jsocial.model.entity.PostEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PostRepository extends CrudRepository<PostEntity, UUID> {
    List<PostEntity> findAllByOwner(String username);

    Optional<PostEntity> findById(Long id);

    boolean existsByOwnerAndTitle(String owner, String title);

}