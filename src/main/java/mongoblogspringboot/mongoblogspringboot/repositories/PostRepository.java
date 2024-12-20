package mongoblogspringboot.mongoblogspringboot.repositories;

import mongoblogspringboot.mongoblogspringboot.model.Post;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PostRepository extends ElasticsearchRepository<Post, String> {

    default List<Post> findLatestPosts() {
        return findAll(PageRequest.of(0, 4, Sort.by(Sort.Order.desc("date")))).getContent();
    }

    @Query(value = """
            {
              "match": {
                "author": "?0"
              }
            }
            """)
    List<Post> findByAuthor(String author);

    @Query(" { \"wildcard\": { \"text\": { \"value\": \"?0*\" } }  }")
    List<Post> findByText(String text);


}