package mongoblogspringboot.mongoblogspringboot.services;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import mongoblogspringboot.mongoblogspringboot.api.PostService;
import mongoblogspringboot.mongoblogspringboot.dto.AuthorPostCount;
import mongoblogspringboot.mongoblogspringboot.model.Post;
import mongoblogspringboot.mongoblogspringboot.repositories.PostRepository;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import java.util.*;


@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    private final ElasticsearchClient elasticsearchClient;


    public PostServiceImpl(PostRepository postRepository, ElasticsearchClient elasticsearchClient) {
        this.postRepository = postRepository;
        this.elasticsearchClient = elasticsearchClient;
    }

    @Override
    public Post insertPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> findPost(String id) {
        Optional<Post> optionalPost = this.postRepository.findById(id);
        return optionalPost
                .map(Collections::singletonList)
                .orElse(Collections.emptyList());
    }

    @Override
    public List<Post> findLatestPosts() {
        return postRepository.findLatestPosts();
    }

    @Override
    public List<Post> findPostsByAuthor(String author) {
        return this.postRepository.findByAuthor(author);
    }

    @Override
    public List<Post> findPostsByText(String text) {
        return this.postRepository.findByText(text);
    }

    @Override
    public List<AuthorPostCount> countPostsByAuthor() {
        SearchRequest searchRequest = SearchRequest.of(b -> b
                .index("posts")
                .size(0)
                .aggregations("posts_by_author", a -> a
                        .terms(t -> t
                                .field("author")
                                .size(100)
                        )
                )
        );

        SearchResponse<Void> response = null;
        try {
            response = elasticsearchClient.search(searchRequest, Void.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        StringTermsAggregate  termsAggregate = response.aggregations()
                .get("posts_by_author")
                .sterms();

        List<AuthorPostCount> authorPostCounts = new ArrayList<>();
       for (StringTermsBucket bucket : termsAggregate.buckets().array()) {
            String author = bucket.key().stringValue();
            int count = (int) bucket.docCount();
            authorPostCounts.add(new AuthorPostCount(author, count));
        }

        return authorPostCounts;

    }




}

