package com.sos.front.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sos.front.model.Marca;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.sos.front.util.ConfigEnum;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
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
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "marca")
@SessionScoped
public class MarcaBean implements Serializable {

    public List<Marca> marcas;
    public Marca marca = new Marca();
    public Part file;
    public Long id;
    public Boolean novo;

    public Boolean getNovo() {
        return novo;
    }

    public void setNovo(Boolean novo) {
        this.novo = novo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public void init() {
        novo = true;
    }

    public void onLoad() {
        if (novo != null) {
            if (novo) {
                marca = new Marca();
            }
        } else {
            marca = new Marca();
        }
    }

    public void getMarcaFromAPI() throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(ConfigEnum.API_URL.getDescricao() + "/marcas/" + id);
        HttpResponse response = client.execute(request);

        // Get the response
        BufferedReader rd = new BufferedReader(new InputStreamReader(
                response.getEntity().getContent()));

        String output = "";
        String line;
        while ((line = rd.readLine()) != null) {
            output += line;
        }
        marca = parseObjJSON(output);
        FacesContext.getCurrentInstance().getExternalContext().redirect("marca-form.jsf");
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
        String line;
        while ((line = rd.readLine()) != null) {
            output += line;
        }
        marcas = parseListJSON(output);
        if (marcas.size() > 0) {
            marcas.forEach(m -> {
                if (m.getPdf() != null) {
                    try {
                        m.setPdf(new String(m.getPdf().getBytes("iso-8859-1"), "UTF-8"));
                    } catch (UnsupportedEncodingException ex) {
                        Logger.getLogger(PatrimonioBean.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }
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
            System.out.println(rd.lines());
            FacesContext.getCurrentInstance().getExternalContext().redirect("marca-list.jsf");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void update() {
        if (file != null && file.getSize() > 0L) {
            uploadFile();
        }
        HttpClient client = new DefaultHttpClient();
        HttpPut put = new HttpPut(ConfigEnum.API_URL.getDescricao() + "/marcas/" + id);
        Gson gson = new Gson();
        StringEntity entity = new StringEntity(gson.toJson(marca),
                ContentType.APPLICATION_FORM_URLENCODED);

        try {
            put.setEntity(entity);
            HttpResponse response = client.execute(put);
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));
            System.out.println(rd.lines());
            FacesContext.getCurrentInstance().getExternalContext().redirect("marca-list.jsf");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void delete() {
        HttpClient client = new DefaultHttpClient();
        HttpDelete delete = new HttpDelete(ConfigEnum.API_URL.getDescricao() + "/marcas/" + id);
        try {
            HttpResponse response = client.execute(delete);
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));
            System.out.println(rd.lines());
            FacesContext.getCurrentInstance().getExternalContext().redirect("marca-list.jsf");
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
                        resEntity.writeTo(System.out);
                        System.out.println();
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

    public StreamedContent download(String pdf) throws MalformedURLException, IOException {
        File f = new File(pdf);
        FileUtils.copyURLToFile(new URL(ConfigEnum.API_URL.getDescricao() + "/arquivos/" + pdf), f);
        String contentType = FacesContext.getCurrentInstance().getExternalContext().getMimeType(f.getAbsolutePath());
        return new DefaultStreamedContent(new FileInputStream(f), contentType, f.getName());
    }
}
