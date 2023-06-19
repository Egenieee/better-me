package com.betterme.controller;

import com.betterme.domain.dto.todos.TodosResponseDto;
import com.betterme.domain.dto.todos.TodosSaveRequestDto;
import com.betterme.domain.dto.todos.TodosUpdateRequestDto;
import com.betterme.service.TodosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @PostMapping("/todos/new")
    public String save(@Valid TodosSaveRequestDto requestDto, BindingResult result) {

        if (result.hasErrors()) {
            return "todos/todosIndex";
        }

        todosService.save(requestDto);

        return "todos/todosIndex";
    }

    @GetMapping("/todos/{todosId}")
    public String findById(Model model, @PathVariable Long todosId) {
        TodosUpdateRequestDto requestDto = todosService.getUpdateRequestDto(todosId);
        model.addAttribute("todosUpdateRequestDto", requestDto);

        return "todos/updateTodosForm";
    }

    @PutMapping("/todos/{todosId}")
    public String update(@PathVariable Long todosId, @Valid TodosUpdateRequestDto requestDto, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "todos/updateTodosForm";
        }

        todosService.update(todosId, requestDto);

        redirectAttributes.addAttribute("betterMeId", requestDto.getBetterMeId());

        return "redirect:/todos";
    }

    @DeleteMapping("/todos/{todosId}")
    public String delete(@PathVariable Long todosId, RedirectAttributes redirectAttributes) {
        Long betterMeId = todosService.getBetterMeId(todosId);
        redirectAttributes.addAttribute("betterMeId", betterMeId);

        todosService.delete(todosId);

        return "redirect:/todos";
    }
}
