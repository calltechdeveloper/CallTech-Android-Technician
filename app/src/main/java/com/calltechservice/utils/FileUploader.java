package com.calltechservice.utils;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

class FileUploader {

    public void upload(Document item) {
        Response response = null;
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new HttpLoggingInterceptor())
                .build();
        MediaType mediaType = MediaType.parse(item.getMimeType());
        try {

            RequestBody formBody = new MultipartBody.Builder().addFormDataPart(item.getName(), item.getName() + item.getMimeType(), RequestBody.create(mediaType, item.getFile())).build();
            Request request = new Request.Builder().url("index.php").post(formBody).build();
            response = client.newCall(request).execute();
            int statusCode = response.code(); // always 500

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (response != null)
                response.close();
        }
    }

}

class Document {

    private String mimeType;
    private String name;
    private File file;

    public Document(String name, String mimeType, File file) {
        this.name = name;
        this.mimeType = mimeType;
        this.file = file;
    }

    public Document(String name, String mimeType, String filePath) {
        this.name = name;
        this.mimeType = mimeType;
        setFile(filePath);
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setFile(String filePath) {
        this.file = new File(URI.create(filePath));
    }
}