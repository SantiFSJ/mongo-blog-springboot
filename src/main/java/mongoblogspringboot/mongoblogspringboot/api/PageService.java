package mongoblogspringboot.mongoblogspringboot.api;

import mongoblogspringboot.mongoblogspringboot.model.Page;

import java.util.List;

public interface PageService {

    List<Page> findById(String id);


}
