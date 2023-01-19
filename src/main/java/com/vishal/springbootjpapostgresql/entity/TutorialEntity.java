package com.vishal.springbootjpapostgresql.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vishal.springbootjpapostgresql.dto.contract.TutorialContract;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name="tutorials")
public class TutorialEntity  implements TutorialContract, BaseEntity, Serializable {
    @Id
    @SequenceGenerator(name="tutorials_gen", sequenceName = "tutorials_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tutorials_gen")
    private Long id;

    @Basic
    @Column(name = "title")
    private String title;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "published")
    private Boolean published;

    @Basic
    @Column(name="phone")
    private String phone;
}
