package com.tntu.server.docs.core.models.data;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.*;

public class BytesMultipartFile implements MultipartFile {

    private final String name;
    private final byte[] content;
    private final ByteArrayInputStream inputStream;

    public BytesMultipartFile(@NotNull String name, @NotNull byte[] content) {
        this.name = name;
        this.content = content;
        this.inputStream = new ByteArrayInputStream(content);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return name;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return content.length == 0;
    }

    @Override
    public long getSize() {
        return content.length;
    }

    @Override
    public byte[] getBytes() throws IOException {
        return content;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return inputStream;
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
    }
}
