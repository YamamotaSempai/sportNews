package kz.aa.sportNews.service.impl;

import kz.aa.sportNews.model.Post;
import kz.aa.sportNews.repository.PostRepository;
import kz.aa.sportNews.service.PostService;
import kz.aa.sportNews.service.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PostServiceImpl extends BaseServiceImpl<Post, Long> implements PostService {
    @Autowired
    private PostRepository repository;

    @Override
    public Page<Post> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
