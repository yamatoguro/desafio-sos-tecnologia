package com.sos.front.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sos.front.model.Marca;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.sos.front.util.ConfigEnum;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.Part;
import org.apache.http.HttpEntity;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.primefaces.json.JSONException;

@ManagedBean(name = "marca")
@SessionScoped
public class MarcaBean implements Serializable {

    public List<Marca> marcas;
    public Marca marca = new Marca();
    public Part file;

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public void getMarcaFromAPI(Long id) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(ConfigEnum.API_URL.getDescricao() + "/marcas/" + id);
        HttpResponse response = client.execute(request);

        // Get the response
        BufferedReader rd = new BufferedReader(new InputStreamReader(
                response.getEntity().getContent()));

        String output = "";
        String line = "";
        while ((line = rd.readLine()) != null) {
            output += line;
        }
        marca = parseObjJSON(output);
    }

    public List<Marca> getMarcas() throws IOException, JSONException {

        marcas = new ArrayList<>();
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(ConfigEnum.API_URL.getDescricao() + "/marcas");
        HttpResponse response = client.execute(request);

        // Get the response
        BufferedReader rd = new BufferedReader(new InputStreamReader(
                response.getEntity().getContent()));

        String output = "";
        String line = "";
        while ((line = rd.readLine()) != null) {
            output += line;
        }
        marcas = parseListJSON(output);
        return marcas;
    }

    private List<Marca> parseListJSON(String jsonString) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Marca>>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    private Marca parseObjJSON(String jsonString) {
        Gson gson = new Gson();
        Type type = new TypeToken<Marca>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    public void save() {
        if (file != null && file.getSize() > 0L) {
            uploadFile();
        }
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(ConfigEnum.API_URL.getDescricao() + "/marcas");
        Gson gson = new Gson();
        StringEntity entity = new StringEntity(gson.toJson(marca),
                ContentType.APPLICATION_FORM_URLENCODED);

        try {
            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));

            String line = "";
            while ((line = rd.readLine()) != null) {
                System.out.println(line);
                if (line.startsWith("Auth=")) {
                    String key = line.substring(5);
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void uploadFile() {
        try (InputStream input = file.getInputStream()) {
            System.out.println(file.getSubmittedFileName());
            CloseableHttpClient httpclient = HttpClients.createDefault();
            try {
                HttpPost httppost = new HttpPost(ConfigEnum.API_URL.getDescricao() + "/upload");
                StringBody comment = new StringBody("Arquivo: " + file.getSubmittedFileName() + " | do tipo:", ContentType.TEXT_PLAIN);

                HttpEntity reqEntity = MultipartEntityBuilder.create()
                        .addPart("file", new InputStreamBody(input, file.getSubmittedFileName()))
                        .addPart("comment", comment)
                        .build();

                httppost.setEntity(reqEntity);

                System.out.println("executing request " + httppost.getRequestLine());
                CloseableHttpResponse response = httpclient.execute(httppost);
                try {
                    System.out.println("----------------------------------------");
                    System.out.println(response.getStatusLine());
                    HttpEntity resEntity = response.getEntity();
                    if (resEntity != null) {
                        System.out.println("Response content length: " + resEntity.getContentLength());
                        marca.setPdf(file.getSubmittedFileName());
                    }
                    EntityUtils.consume(resEntity);
                } finally {
                    response.close();
                }
            } finally {
                httpclient.close();
            }
        } catch (IOException ioe) {
            System.out.println("Erro ao escrever: " + ioe.getLocalizedMessage());
        }
    }
}