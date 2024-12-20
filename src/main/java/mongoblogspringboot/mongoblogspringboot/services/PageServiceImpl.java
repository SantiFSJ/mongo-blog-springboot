package mongoblogspringboot.mongoblogspringboot.services;

import mongoblogspringboot.mongoblogspringboot.api.PageService;
import mongoblogspringboot.mongoblogspringboot.model.Page;
import mongoblogspringboot.mongoblogspringboot.repositories.PageRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PageServiceImpl implements PageService {
    private final PageRepository pageRepository;

    public PageServiceImpl(PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    public List<Page> findById(String id) {
        Optional<Page> optionalPage = this.pageRepository.findById((id));
        return optionalPage
                .map(Collections::singletonList)
                .orElse(Collections.emptyList());
    }

}
