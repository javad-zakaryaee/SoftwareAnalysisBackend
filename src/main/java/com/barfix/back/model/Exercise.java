package com.barfix.back.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exercise", schema = "public")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {
    @Id
    public String id;
    @Column(name = "name")
    public String name;
    @Column(name = "descrption")
    public String description;
    @Column(name = "pic_url")
    public String picUrl;
}
