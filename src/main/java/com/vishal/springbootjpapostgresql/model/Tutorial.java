//package com.vishal.springbootjpapostgresql.model;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@NoArgsConstructor
//@Builder
//@AllArgsConstructor
//@Entity
//@Table(name="tutorials")
//@Data
//public class Tutorial {
//    @Id
//    @SequenceGenerator(name="tutorials_gen", sequenceName = "tutorials_id_seq", allocationSize=1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tutorials_gen")
//    private long id;
//
//    @Column(name = "title")
//    private String title;
//
//    @Column(name = "description")
//    private String description;
//
//    @Column(name = "published")
//    private boolean published;
//
//    @Column(name="phone")
//    private String phone;
//
//    public Tutorial(String title, String description, boolean published, String phone) {
//        this.title = title;
//        this.description = description;
//        this.published = published;
//        this.phone = phone;
//    }
//}
