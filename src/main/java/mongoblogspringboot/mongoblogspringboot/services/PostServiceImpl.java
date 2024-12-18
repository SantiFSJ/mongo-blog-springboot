package mongoblogspringboot.mongoblogspringboot.services;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import mongoblogspringboot.mongoblogspringboot.api.PostService;
import mongoblogspringboot.mongoblogspringboot.dto.AuthorPostCount;
import mongoblogspringboot.mongoblogspringboot.model.Post;
import mongoblogspringboot.mongoblogspringboot.repositories.PostRepository;

import org.elasticsearch.client.RequestOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.client.erhlc.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQuery;
import org.springframework.data.elasticsearch.client.erhlc.RestClients;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import java.util.*;


@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    private final ElasticsearchClient elasticsearchClient;
    private final ObjectMapper objectMapper;


    public PostServiceImpl(PostRepository postRepository, ElasticsearchClient elasticsearchClient, ObjectMapper objectMapper) {
        this.postRepository = postRepository;
        this.elasticsearchClient = elasticsearchClient;
        this.objectMapper = objectMapper;
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
                .size(0) // No necesitamos resultados de documentos
                .aggregations("posts_by_author", a -> a
                        .terms(t -> t
                                .field("author")
                                .size(100)
                        )
                )
        );

        // Ejecuta la consulta
        SearchResponse<Void> response = null;
        try {
            response = elasticsearchClient.search(searchRequest, Void.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Obtención de la agregación
        StringTermsAggregate  termsAggregate = response.aggregations()
                .get("posts_by_author")
                .sterms();

        // Mapeo de los buckets a AuthorPostCount
        List<AuthorPostCount> authorPostCounts = new ArrayList<>();
       for (StringTermsBucket bucket : termsAggregate.buckets().array()) {
            String author = bucket.key().stringValue(); // Nombre del autor
            int count = (int) bucket.docCount();   // Número de posteos
            authorPostCounts.add(new AuthorPostCount(author, count));
        }

        return authorPostCounts;

    }




}

