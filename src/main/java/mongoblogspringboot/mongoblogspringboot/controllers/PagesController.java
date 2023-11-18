package mongoblogspringboot.mongoblogspringboot.controllers;

import io.swagger.v3.oas.annotations.Operation;
import mongoblogspringboot.mongoblogspringboot.api.PageService;
import mongoblogspringboot.mongoblogspringboot.dto.AuthorPostCount;
import mongoblogspringboot.mongoblogspringboot.model.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/pages")
public class PagesController {

    private PageService pageService ;

    public PagesController(PageService pageService) {
        this.pageService = pageService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recupera una pagina en base a su id")
    public List<Page> getPageById(@PathVariable String id) {
        return this.pageService.findById(id);

    }

    @ExceptionHandler(Exception.class)
    public void handleException(Exception exception) {
        exception.printStackTrace();
    }

}
