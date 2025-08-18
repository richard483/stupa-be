package com.stupa.stupa_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@Entity
@Table(name = "class_room", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"school_name", "class_grade", "class_year"})
})
@AllArgsConstructor
@NoArgsConstructor
public class ClassRoom {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String schoolName;

  @Column(nullable = false)
  private String classGrade;

  @Column(nullable = false)
  private String classYear;

}
