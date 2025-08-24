package com.stupa.stupa_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Builder
@Data
@Entity
@Table(name = "class_room", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"school_id", "grade", "year"})
})
@AllArgsConstructor
@NoArgsConstructor
public class ClassRoom {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String grade;

  @Column(nullable = false)
  private String year;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "school_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private School school;

}
