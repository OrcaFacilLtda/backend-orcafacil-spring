package com.orcafacil.api.interfaceadapter.controller;

import com.orcafacil.api.application.service.category.CategoryService;
import com.orcafacil.api.domain.category.Category;
import com.orcafacil.api.interfaceadapter.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Category>> create(@RequestBody Category category){
        Category created = categoryService.create(category);
        return  ResponseEntity
                .status(201)
                .body(new ApiResponse<>(true,"Categoria criada com sucesso.",created));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<Category>> update(@PathVariable Integer id, @RequestBody Category category){
        Category created = categoryService.update(id,category);
        return  ResponseEntity
                .status(201)
                .body(new ApiResponse<>(true,"Categoria atualizada com sucesso.",created));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Integer id){
        categoryService.delete(id);
        return  ResponseEntity
                .status(201)
                .body(new ApiResponse<>(true,"Categoria atualizada com sucesso.",null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Category>> categoryGetById(@PathVariable Integer id){
        return categoryService
                .findById(id)
                .map(category -> ResponseEntity.ok(new ApiResponse<>(true,"Categoria encontrada com sucesso",category)))
                .orElse(ResponseEntity
                        .status(404)
                        .body(new ApiResponse<>(false,"Categoria não foi encontrada com sucesso.",null)));
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<Category>>> categoryGetById(){
         List<Category> categories = categoryService.findAll();
              return ResponseEntity.ok(new ApiResponse<>(true,"Lista de categorias encontrada com sucesso.",categories));
    }

}
