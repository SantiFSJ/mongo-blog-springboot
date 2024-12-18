package mongoblogspringboot.mongoblogspringboot.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "posts")
public class Post {
    @Id
    private String id;
    @Field(type = FieldType.Text)
    private String title;
    @Field(type = FieldType.Text)
    private String text;
    @Field(type = FieldType.Keyword)
    private List<String> tags;
    @Field(type = FieldType.Text)
    private String resume;
    @Field(type = FieldType.Keyword)
    private List<String> relatedLinks;
    @Field(type = FieldType.Keyword)
    private String author;
    @Field(type = FieldType.Date)
    private LocalDate date;

}