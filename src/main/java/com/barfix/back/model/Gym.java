package com.barfix.back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "gym", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Gym {
    @Id
    @NonNull
    public String id;
    @OneToOne(cascade = CascadeType.ALL)
    @NonNull
    @JoinColumn(name = "ownerId" , referencedColumnName = "id")
    public User owner;
    @Column(name = "name")
    @NonNull
    public String name;
    @Column(name = "starRating")
    @NonNull
    public Double starRating;
    @Column(name = "address")
    @NonNull
    public String address;
    @Column(name = "locationPluscode")
    public String plusCode;
    @Column(name = "pricePerSession")
    @NonNull
    public Double pricePerSession;
    @Column(name = "pricePerMonth")
    @NonNull
    public Double pricePerMonth;
    @OneToMany(targetEntity = Coach.class , mappedBy = "gym")
    @JsonManagedReference
    private List<Coach> coaches;
}
