package shop.mtcoding.hiberpc.model.board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@Getter
@Table(name = "board_tb")
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String author;
    private String title;
    private String content;

    @CreationTimestamp
    private Timestamp createdAt;

    @Builder
    public Board(Integer id, String author, String title, String content, Timestamp createdAt) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
