package mongoblogspringboot.mongoblogspringboot.api;

import mongoblogspringboot.mongoblogspringboot.dto.AuthorPostCount;
import mongoblogspringboot.mongoblogspringboot.model.Post;

import java.util.List;

public interface PostService {

    Post insertPost(Post post);

    List<Post> findPost(String id);

    List<Post> findLatestPosts();

    List<Post> findPostsByAuthor(String author);

    List<Post> findPostsByText(String text);

    List<AuthorPostCount> countPostsByAuthor();


}
