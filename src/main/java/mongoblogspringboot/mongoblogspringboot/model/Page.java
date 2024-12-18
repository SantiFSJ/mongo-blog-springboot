package mongoblogspringboot.mongoblogspringboot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@Builder
@Document(indexName = "pages")
public class Page {
    @Id
    private String id;
    @Field(type = FieldType.Text) // Campo de texto completo
    private String title;
    @Field(type = FieldType.Text)
    private String text;
    @Field(type = FieldType.Keyword)
    private String author;
    @Field(type = FieldType.Date)
    private LocalDate date;

}
