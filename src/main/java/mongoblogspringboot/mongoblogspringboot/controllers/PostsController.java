package mongoblogspringboot.mongoblogspringboot.controllers;

import mongoblogspringboot.mongoblogspringboot.api.PostService;
import mongoblogspringboot.mongoblogspringboot.dto.AuthorPostCount;
import mongoblogspringboot.mongoblogspringboot.model.Page;
import mongoblogspringboot.mongoblogspringboot.model.Post;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/posts")
public class PostsController {

    private PostService postService ;

    public PostsController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/byauthor")
    public List<AuthorPostCount> getPagesByAuthor() {
        return this.postService.countPostsByAuthor();
    }

    @GetMapping("/latest")
    public List<Post> getLatest4Posts() {
        return this.postService.findLatestPosts();
    }

    @GetMapping("/posts-autor/{nombreautor}")
    public List<Post> getPostsByAuthor(@PathVariable String nombreautor) {
        return this.postService.findPostsByAuthor(nombreautor);
    }

    @GetMapping("/search/{text}")
    public List<Post> searchPosts(@PathVariable String text) {
        return this.postService.findPostsByText(text);
    }

    @ExceptionHandler(Exception.class)
    public void handleException(Exception exception) {
        exception.printStackTrace();
    }

}
