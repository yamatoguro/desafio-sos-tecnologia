package com.sos.desafio.main.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadController {

    private static String UPLOADED_FOLDER = "src/main/resources/arquivos/";
    
    @PostMapping("/upload")
    public ResponseEntity<Object> singleFileUpload(
            @RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

            if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Por favor selecione um arquivo para fazer upload");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            return ResponseEntity.ok(UPLOADED_FOLDER + file.getOriginalFilename());

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}