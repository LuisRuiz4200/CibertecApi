package com.cibertec.api.reuzable;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.cibertec.api.model.ConsultaValidezCpeSunat;
import com.cibertec.api.model.TokenSunat;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

public class ApiSunat {
    public static void main(String[] args) {
    	
        try {
        	ConsultaValidezCpeSunat consultaValidezCpeSunat = new ConsultaValidezCpeSunat();
            consultaValidezCpeSunat.setNumRuc("20406464181");
            consultaValidezCpeSunat.setCodComp("03");
            consultaValidezCpeSunat.setNumeroSerie("B005");
            consultaValidezCpeSunat.setNumero("1");
            consultaValidezCpeSunat.setFechaEmision("06/05/2022");
            consultaValidezCpeSunat.setMonto("18.0");
            
        	mostrarEstadoSUNATDelDocumento(null,consultaValidezCpeSunat);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void mostrarEstadoSUNATDelDocumento (Component component,ConsultaValidezCpeSunat consultaValidezCpeSunat) {
    	try {
            // URL del servicio de consulta de validez de comprobantes electrónicos de SUNAT
            String url = "https://api.sunat.gob.pe/v1/contribuyente/contribuyentes/10764538833/validarcomprobante";

            // Crear un cliente HTTP
            HttpClient httpClient = HttpClients.createDefault();

            // Crear una solicitud POST
            HttpPost httpPost = new HttpPost(url);
            
            
            // Agregar los encabezados personalizados que necesites
            List<Header> headers = new ArrayList<>();
            
            String token = generarTokenSunatApi().getAccess_token();
            
            headers.add(new BasicHeader("Authorization", "Bearer " + token));
            headers.add(new BasicHeader("Content-Type","application/json"));
            
            // Agrega más encabezados si es necesario
            httpPost.setHeaders(headers.toArray(new Header[0]));
            httpPost.setHeaders(headers.toArray(new Header[1]));
            
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            
            String jsonString = gson.toJson(consultaValidezCpeSunat);
            
            // Establecer el cuerpo de la solicitud como JSON
            httpPost.setEntity(new StringEntity(jsonString));

            
            // Ejecutar la solicitud POST
            HttpResponse response = httpClient.execute(httpPost);

            // Obtener la respuesta
            HttpEntity entity = response.getEntity();
            String responseText = EntityUtils.toString(entity);
            
            String serie = consultaValidezCpeSunat.getNumeroSerie();
            String correlativo = consultaValidezCpeSunat.getNumero();
            
            consultaValidezCpeSunat = gson.fromJson(responseText, new ConsultaValidezCpeSunat().getClass());
            
            if (consultaValidezCpeSunat.getData().getEstadoCp()==null) {
            	JOptionPane.showMessageDialog(component,responseText,"ERROR DE RETORNO",0);
            }else if (consultaValidezCpeSunat.getData().getEstadoCp().equals("1")) {
                JOptionPane.showMessageDialog(component,serie +  "-" + correlativo + " REGISTRADO EN SUNAT" + "\r\n" + Arrays.toString(consultaValidezCpeSunat.getData().getObservaciones()),"ATENCION",1);
            }else if (consultaValidezCpeSunat.getData().getEstadoCp().equals("0")) {
                JOptionPane.showMessageDialog(component,serie +  "-" + correlativo + " NO INFORMADO A SUNAT" + "\r\n" + Arrays.toString(consultaValidezCpeSunat.getData().getObservaciones()),"ATENCION",0);
            }else if (consultaValidezCpeSunat.getData().getEstadoCp().equals("2")) {
                JOptionPane.showMessageDialog(component,serie +  "-" + correlativo + " ANULADO EN SUNAT" + "\r\n" + Arrays.toString(consultaValidezCpeSunat.getData().getObservaciones()),"ATENCION",2);
            }else {
            	JOptionPane.showMessageDialog(component,"ESTADO DESCONOCIDO","ERROR SERVER",1);
            }

            // Imprimir la respuesta
            System.out.println("Respuesta de SUNAT:");
            System.out.println(responseText);
            System.out.println(consultaValidezCpeSunat.toString());
            
            
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static TokenSunat generarTokenSunatApi() {

    	String client_id ="46562263-a72a-41be-9932-5c448e316c84";
    	String client_secret = "H9OqkMqOHVuxSJwsu2abEQ==";
    	String url = "https://api-seguridad.sunat.gob.pe/v1/clientesextranet/"+ client_id +"/oauth2/token/";
    	TokenSunat tokenSunat = new TokenSunat();
    	
    	try {

        	HttpClient httpClient = HttpClients.createDefault();
        	
        	HttpPost httpPost =new  HttpPost(url);
        	
        	/*HEADERS*/
        	List<Header> headers = new ArrayList<Header>();
        	headers.add(new BasicHeader("Content-Type", "application/x-www-form-urlencoded"));
        	
        	httpPost.setHeaders(headers.toArray(new Header[0]));
        	
        	/*BODY*/
        	List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        	params.add(new BasicNameValuePair("grant_type", "client_credentials"));
        	params.add(new BasicNameValuePair("scope", "https://api.sunat.gob.pe/v1/contribuyente/contribuyentes"));
        	params.add(new BasicNameValuePair("client_id", client_id));
        	params.add(new BasicNameValuePair("client_secret", client_secret));
        	
        	httpPost.setEntity(new UrlEncodedFormEntity(params));
        	
        	/*EJECUTAR EL POST EN UN RESPONSE*/
        	HttpResponse response = httpClient.execute(httpPost);
        	
        	/*ALMACENARLO EN UN OBJETO ENTITY Y PARSEARLO A STRING*/
        	HttpEntity entity = response.getEntity();
        	String responseString = EntityUtils.toString(entity);
        	
        	tokenSunat = new Gson().fromJson(responseString,TokenSunat.class);
        	
        	System.out.println(responseString);
        	
        	
    	}catch(Exception ex) {
    		
    	}
    	
    	return tokenSunat;
    	
    }
    
}

