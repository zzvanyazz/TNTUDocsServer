package com.tntu.server.docs.communication.models.responses;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.tntu.server.docs.communication.models.ErrorCode;
import com.tntu.server.docs.core.data.exceptions.DocsException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class ResponseEntityFactory {
    private ResponseEntityFactory() {
    }

    public static ResponseEntity<?> createBadRequest(BindingResult bindingResult) {
        Multimap<String, String> field2Error = ArrayListMultimap.create();

        for (ObjectError objError : bindingResult.getAllErrors()) {
            String key;
            if (objError instanceof FieldError) {
                key = ((FieldError) objError).getField();
            } else {
                key = objError.getObjectName();
            }
            field2Error.put(key, objError.getDefaultMessage());
        }

        Map<String, String> data = new HashMap<>();
        field2Error.asMap().forEach((key, value) -> data.put(key, String.join("\n", value)));

        var error = new Error(ErrorCode.BAD_REQUEST, "Validation failed.");
        error.setDetails(data);

        return createResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> createBadRequest(String message) {
        var error = new Error(ErrorCode.BAD_REQUEST, message);

        return createResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

    public static ResponseEntity<?> createCreated() {
        return new ResponseEntity<>(Response.empty, HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<?> createCreated(T data) {
        var response = new Response<>(data);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public static <T> ResponseEntity<?> createOk(T data) {
        var response = new Response<>(data);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static ResponseEntity<?> createOk() {
        return new ResponseEntity<>(Response.empty, HttpStatus.OK);
    }

    public static ResponseEntity<?> createFile(MultipartFile file) throws DocsException {
        var headers = new HttpHeaders();
        var originalFilename = Optional.ofNullable(file.getOriginalFilename()).orElse("no_name");
        var decodedFileName = URLEncoder.encode(originalFilename, StandardCharsets.UTF_8);
        decodedFileName = decodedFileName.replaceAll("\\+", "%20");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + decodedFileName);

        var size = file.getSize();
        var mediaType = MediaType.MULTIPART_FORM_DATA;
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            throw new DocsException("Can not load file.");
        }
        var resource = new InputStreamResource(inputStream);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(size)
                .contentType(mediaType)
                .body(resource);
    }

    private static ResponseEntity<?> createResponseEntity(Error error, HttpStatus httpStatus) {
        var response = new Response<>(error);

        return new ResponseEntity<>(response, httpStatus);
    }
}
