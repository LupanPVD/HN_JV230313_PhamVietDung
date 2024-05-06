package com.example.Exam_JavaCore_Webapp.controller;

import com.example.Exam_JavaCore_Webapp.StudentRepository;
import com.example.Exam_JavaCore_Webapp.request.StudentRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentRepository studentRepository;
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("student", new StudentRequest());
        return "add";
    }
    @GetMapping("/list")
    public String showEmployeeList(Model model) {
        List<StudentRequest> studentList = studentRepository.findAll();
        model.addAttribute("students", studentList);
        return "list";
    }
    @PostMapping("/add")
    public String doAdd(@Valid @ModelAttribute("student") StudentRequest request, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add";
        }

        StudentRequest student = new StudentRequest();
        student.setStudentName(request.getStudentName());
        student.setSex(request.isSex());
        student.setAddress(request.getAddress());
        student.setImageUrl(request.getImageUrl());
        student.setPhoneNumber(request.getPhoneNumber());
        student.setBirthday(request.getBirthday()); // Sử dụng giá trị birthday từ request trực tiếp

        studentRepository.save(student);
        return "redirect:/students/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<StudentRequest> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            model.addAttribute("student", optionalStudent.get());
            return "edit";
        } else {
            return "redirect:/students/list";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable("id") Long id, @Valid @ModelAttribute("student") StudentRequest request, BindingResult result) {
        if (result.hasErrors()) {
            return "edit";
        }

        Optional<StudentRequest> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            StudentRequest student = optionalStudent.get();
            student.setStudentName(request.getStudentName());
            student.setSex(request.isSex());
            student.setAddress(request.getAddress());
            student.setImageUrl(request.getImageUrl());
            student.setPhoneNumber(request.getPhoneNumber());
            student.setBirthday(request.getBirthday()); // Sử dụng giá trị birthday từ request trực tiếp

            studentRepository.save(student);
        }
        return "redirect:/students/list";
    }
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id) {
        studentRepository.deleteById(id);
        return "redirect:/students/list";
    }
}
