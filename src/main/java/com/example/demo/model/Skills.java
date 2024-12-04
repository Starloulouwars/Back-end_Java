package com.example.demo.model;

import com.example.demo.view.SkillView;
import com.example.demo.view.UserSkillView;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Skills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView(SkillView.class)
    Integer id;

    @JsonView({UserSkillView.class, SkillView.class})
    String name;

    @ManyToMany(mappedBy = "skills")
    @JsonView(SkillView.class)
    List<User> user = new ArrayList<>();
}
