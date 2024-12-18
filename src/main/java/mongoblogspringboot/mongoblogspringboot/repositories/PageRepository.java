package mongoblogspringboot.mongoblogspringboot.repositories;

import mongoblogspringboot.mongoblogspringboot.model.Page;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface PageRepository extends ElasticsearchRepository<Page, String> {
    Optional<Page> getPagesById(String id);
}
