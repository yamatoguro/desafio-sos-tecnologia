package com.sos.front.bean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sos.front.model.Patrimonio;
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
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import org.apache.commons.io.FileUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.primefaces.json.JSONException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean(name = "patrimonio")
@SessionScoped
@MultipartConfig(location = "./tmp")
public class PatrimonioBean implements Serializable {

    public List<Patrimonio> patrimonios;
    public Patrimonio patrimonio = new Patrimonio();
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

    public void setFile(Part file) {
        this.file = file;
    }

    public Part getFile() {
        return file;
    }

    public Patrimonio getPatrimonio() {
        return patrimonio;
    }

    public void init() {
        novo = true;
    }

    public void onLoad() {
        if (novo != null) {
            if (novo) {
                patrimonio = new Patrimonio();
            }
        } else {
            patrimonio = new Patrimonio();
        }
    }

    public List<Patrimonio> getPatrimonios() throws IOException, JSONException {
        patrimonios = new ArrayList<>();
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(ConfigEnum.API_URL.getDescricao() + "/patrimonios");
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader(new InputStreamReader(
                response.getEntity().getContent()));

        String output = "";
        String line = "";
        while ((line = rd.readLine()) != null) {
            output += line;
        }
        patrimonios = parseJSON(output);
        patrimonios.forEach(p -> {
            if (p.getPdf() != null) {
                try {
                    p.setPdf(new String(p.getPdf().getBytes("iso-8859-1"), "UTF-8"));
                } catch (UnsupportedEncodingException ex) {
                    Logger.getLogger(PatrimonioBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        return patrimonios;
    }

    public void getPatrimonioFromAPI() throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(ConfigEnum.API_URL.getDescricao() + "/patrimonios/" + id);
        HttpResponse response = client.execute(request);

        // Get the response
        BufferedReader rd = new BufferedReader(new InputStreamReader(
                response.getEntity().getContent()));

        String output = "";
        String line;
        while ((line = rd.readLine()) != null) {
            output += line;
        }
        patrimonio = parseObjJSON(output);
        FacesContext.getCurrentInstance().getExternalContext().redirect("patrimonio-form.jsf");
    }

    private List<Patrimonio> parseJSON(String jsonString) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<Patrimonio>>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    private Patrimonio parseObjJSON(String jsonString) {
        Gson gson = new Gson();
        Type type = new TypeToken<Patrimonio>() {
        }.getType();
        return gson.fromJson(jsonString, type);
    }

    public void save() {
        if (file != null && file.getSize() > 0L) {
            uploadFile();
        }
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(ConfigEnum.API_URL.getDescricao() + "/patrimonios");
        Gson gson = new Gson();
        StringEntity entity = new StringEntity(gson.toJson(patrimonio),
                ContentType.APPLICATION_FORM_URLENCODED);

        try {
            post.setEntity(entity);
            HttpResponse response = client.execute(post);
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));
            System.out.println(rd.lines());
            FacesContext.getCurrentInstance().getExternalContext().redirect("patrimonio-list.jsf");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void update() {
        if (file != null && file.getSize() > 0L) {
            uploadFile();
        }
        HttpClient client = new DefaultHttpClient();
        HttpPut put = new HttpPut(ConfigEnum.API_URL.getDescricao() + "/patrimonios/" + id);
        Gson gson = new Gson();
        StringEntity entity = new StringEntity(gson.toJson(patrimonio),
                ContentType.APPLICATION_FORM_URLENCODED);

        System.out.println(entity);
        try {
            put.setEntity(entity);
            System.out.println("executing request " + put.getRequestLine());
            put.getEntity().writeTo(System.out);
            System.out.println();
            HttpResponse response = client.execute(put);
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));
            System.out.println(rd.lines());
            FacesContext.getCurrentInstance().getExternalContext().redirect("patrimonio-list.jsf");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void delete() {
        HttpClient client = new DefaultHttpClient();
        HttpDelete delete = new HttpDelete(ConfigEnum.API_URL.getDescricao() + "/patrimonios/" + id);
        try {
            HttpResponse response = client.execute(delete);
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));
            System.out.println(rd.lines());
            FacesContext.getCurrentInstance().getExternalContext().redirect("patrimonio-list.jsf");
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
                        .setCharset(Charset.forName("UTF-8"))
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
                        patrimonio.setPdf(file.getSubmittedFileName());
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
        pdf = new String(pdf.getBytes("iso-8859-1"), "UTF-8");
        File f = new File(pdf);
        FileUtils.copyURLToFile(new URL(ConfigEnum.API_URL.getDescricao() + "/arquivos/" + pdf), f);
        String contentType = FacesContext.getCurrentInstance().getExternalContext().getMimeType(f.getAbsolutePath());
        return new DefaultStreamedContent(new FileInputStream(f), contentType, f.getName());
    }
}
