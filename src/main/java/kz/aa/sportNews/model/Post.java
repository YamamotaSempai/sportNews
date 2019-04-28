package kz.aa.sportNews.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "*Пожалуйста введите название статьи")
    private String title;

    @Column(length = 10000)
    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "*Пожалуйста введите дату создания статьи или проведения события")
    @Column
    private Date date;

    @ElementCollection
    private List<String> urlImg;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
