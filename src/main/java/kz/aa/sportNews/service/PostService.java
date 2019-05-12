package kz.aa.sportNews.service;

import kz.aa.sportNews.model.Post;
import kz.aa.sportNews.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotEmpty;

public interface PostService extends BaseService<Post, Long> {
    Page<Post> findAll(Pageable pageable);
    void deletePost(Long id);
    Page<Post> findByTitleLikeOrderByDate(@NotEmpty(message = "*Пожалуйста введите название статьи") String title, Pageable pageable);
    Page<Post> findAllByOrderByDate(Pageable pageable);
}
