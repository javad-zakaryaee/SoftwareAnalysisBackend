package com.barfix.back.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.sql.Date;

@Entity
@Table(name = "exerciseplan", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Plan {
    @Id
    public String id;
    @ManyToOne()
    @NonNull
    @JoinColumn(name = "coach_id" , referencedColumnName = "id")
    public Coach coach;
    @Column(name = "name")
    public String name;
    @Column(name = "difficulty")
    public String text;
    @Column(name = "exercises")
    public String exercises;
    @Column(name = "repeatTimes")
    public String repeatTimes;
}
