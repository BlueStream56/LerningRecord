package com.example.emaildemo.mail;

import javax.activation.DataSource;
import java.io.*;

public class InputStreamDataSource implements DataSource {

    private InputStream inputStream;

    private String contenttype;

    private String name;

    private ByteArrayOutputStream buffer = new ByteArrayOutputStream();

    public InputStreamDataSource(InputStream inputStream) {
        try {
            int nRead;
            byte[] data = new byte[16384];
            while ((nRead = inputStream.read(data,0,data.length)) != -1){
                buffer.write(data,0,nRead);
            }
            buffer.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new ByteArrayInputStream(buffer.toByteArray());
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    @Override
    public String getContentType() {
        return contenttype;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
