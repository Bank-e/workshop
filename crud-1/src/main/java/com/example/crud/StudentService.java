package com.example.crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // แปลงจาก Entity -> DTO
    private StudentDTO convertToDTO(Student student) {
        return new StudentDTO(
            student.getId(),
            student.getUserName(),
            student.getType(),
            student.getFaculty(),
            student.getEmail(),
            student.getDisplayname_en()
        );
    }

    // แปลงจาก DTO -> Entity
    private Student convertToEntity(StudentDTO studentDTO) {
        Student student = new Student();
        student.setUserName(studentDTO.getUserName());
        student.setType(studentDTO.getType());
        student.setFaculty(studentDTO.getFaculty());
        student.setEmail(studentDTO.getEmail());
        student.setDisplayname_en(studentDTO.getDisplayname_en());
        return student;
    }

    // สร้าง Student ใหม่
    public StudentDTO createStudent(StudentDTO studentDTO) {
        Student student = convertToEntity(studentDTO);
        Student savedStudent = studentRepository.save(student);
        return convertToDTO(savedStudent);
    }

    // ดึงข้อมูลทั้งหมด
    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                       .map(this::convertToDTO)
                       .collect(Collectors.toList());
    }

    // ดึงข้อมูลโดยใช้ ID
    public Optional<StudentDTO> getStudentById(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        return student.map(this::convertToDTO);
    }

    // อัพเดตข้อมูล
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        if (studentRepository.existsById(id)) {
            Student student = convertToEntity(studentDTO);
            student.setId(id);
            Student updatedStudent = studentRepository.save(student);
            return convertToDTO(updatedStudent);
        }
        return null;
    }

    // ลบข้อมูล
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
}
