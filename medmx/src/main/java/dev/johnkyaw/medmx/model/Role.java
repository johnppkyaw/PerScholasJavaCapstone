package dev.johnkyaw.medmx.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import jakarta.persistence.*;
@Setter
@Getter
@NoArgsConstructor
@ToString
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    @SequenceGenerator(name = "role_seq_seq", sequenceName = "role_seq_sequence", allocationSize = 1)
    private Long id;
    private String name;
    public Role(String name) {
        this.name = name;
    }
}