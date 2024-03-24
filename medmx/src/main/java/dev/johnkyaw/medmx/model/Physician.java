package dev.johnkyaw.medmx.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "physician",
        uniqueConstraints = { @UniqueConstraint(name = "UniqueNumberAndStatus", columnNames = {"first_name", "last_name", "specialty"})})
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
@Getter
@Setter
public class Physician {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "physician_seq")
    @SequenceGenerator(name = "physician_seq", sequenceName = "physician_sequence", allocationSize = 1)
    private long id;

    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "physician_roles",
            joinColumns = @JoinColumn(name = "physician_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    @NonNull
    @Column(name = "first_name")
    private String firstName;

    @NonNull
    @Column(name = "last_name")
    private String lastName;

    @NonNull
    @Column(name = "specialty")
    private String specialty;

    @NonNull
    private String clinicName;

    @NonNull
    private String clinicAddress;

    @NonNull
    private String clinicAddress2;

    @NonNull
    private String phone;
}