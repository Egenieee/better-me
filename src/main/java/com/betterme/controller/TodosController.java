package com.betterme.controller;

import com.betterme.domain.dto.todos.TodosResponseDto;
import com.betterme.domain.dto.todos.TodosSaveRequestDto;
import com.betterme.service.TodosService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class TodosController {

    private final TodosService todosService;

    @GetMapping("/todos")
    public String getTodosIndex(Model model, @RequestParam String betterMeId) {
        List<TodosResponseDto> todosList = todosService.getTodosList(Long.parseLong(betterMeId));

        model.addAttribute("todosList", todosList);
        model.addAttribute("betterMeId", betterMeId);
        model.addAttribute("todosSaveRequestDto", new TodosSaveRequestDto());

        return "todos/todosIndex";
    }
}
