package com.hslcreator.LibraryAPI.models.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book_departments")
public class BookDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookDepartmentId;

    @Enumerated(EnumType.STRING)
    private Department departmentName;

    // TODO: Add one to one mapping with the according entities
    private int bookId;
}
