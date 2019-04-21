package kz.aa.sportNews.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usr", schema = "public")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    @NotEmpty(message = "*Пожалуйста введите ваше Имя")
    private String name;

    @Column(name = "last_name")
    @NotEmpty(message = "*Пожалуйста введите вашу Фамилию")
    private String lastName;

    @Column(columnDefinition = "varchar(32) default 'ADMIN'")
    @Enumerated(EnumType.STRING)
    private Role roles;
}