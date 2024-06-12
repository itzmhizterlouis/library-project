package com.hslcreator.LibraryAPI.repositories;

import com.hslcreator.LibraryAPI.models.entities.BookDepartment;
import com.hslcreator.LibraryAPI.models.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookDepartmentRepository extends JpaRepository<BookDepartment, Long> {

    List<BookDepartment> findAllByDepartmentName(Department departmentName);

     void deleteByBookId(int bookId);
}
