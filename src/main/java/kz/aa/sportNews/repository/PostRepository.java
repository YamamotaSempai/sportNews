package kz.aa.sportNews.repository;

import kz.aa.sportNews.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.validation.constraints.NotEmpty;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);

    @Modifying
    @Query("delete from Post p where p.id = :id")
    void delete(@Param("id") Long id);

    Page<Post> findAllByOrderByDateDesc(Pageable pageable);

    Page<Post> findByTitleLikeOrderByDateDesc(@NotEmpty(message = "*Пожалуйста введите название статьи") String title, Pageable pageable);
}
