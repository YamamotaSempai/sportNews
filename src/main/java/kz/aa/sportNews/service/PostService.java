package kz.aa.sportNews.service;

import kz.aa.sportNews.model.Post;
import kz.aa.sportNews.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService extends BaseService<Post, Long> {
    Page<Post> findAll(Pageable pageable);
}
