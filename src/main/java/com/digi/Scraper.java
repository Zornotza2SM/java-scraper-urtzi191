package com.digi;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Scraper {

    public static void main(String[] args) {
        
        // ----------------------------------------------------
        // FASE 1: Recolección y Acceso al Dato No Estructurado
        // ----------------------------------------------------
        
        // 1. Crear el StringBuilder para construir el contenido del CSV
        StringBuilder csvData = new StringBuilder();
        csvData.append("Titulo;Precio_Euros\n"); // Cabecera del CSV
        
        try {
            // Carga el archivo HTML estático localmente
            Document doc = Jsoup.connect("https://www.huffingtonpost.es/").get();

            System.out.println("Documento HTML cargado. Iniciando el scraping (Extracción de datos)...");
            
            // 2. Seleccionar todos los contenedores de producto
            Elements productos = doc.select(".producto"); 
            
            // ----------------------------------------------------
            // FASE 2: Preprocesamiento (Extracción y Limpieza)
            // Tareas del Alumno: Completar la lógica de extracción y limpieza.
            // ----------------------------------------------------
            
            for (Element producto : productos) {
                
                // Tarea A: Extraer el título
                String titulo = producto.select(".titulo-producto").text();
                
                // Tarea B: Extraer el precio como texto
                String precioTexto = producto.select(".precio").text();
                
                // Tarea C: Limpieza del Dato (EL ALUMNO COMPLETA ESTA LÓGICA)
                String precioLimpio = precioTexto;
                
                // ----------------------------------------------------------------------------------
                //  *** CÓDIGO A COMPLETAR POR EL ALUMNO (SOLUCIÓN DEL PROFESOR ABAJO) ***
                //  1. Eliminar el símbolo de moneda (" €")
                //  2. Reemplazar la coma decimal por un punto decimal (para ser compatible con CSV/Números)
                precioLimpio = precioLimpio.replace(" €", "").replace(",", "."); 
                //  (Opcional: Filtrar precios obviamente erróneos si deseas añadir un reto de limpieza)
                // ----------------------------------------------------------------------------------
                
                // 3. Estructurar el dato: Añadir al StringBuilder en formato CSV
                csvData.append(titulo).append(";").append(precioLimpio).append("\n");
            }
            
            // ----------------------------------------------------
            // FASE 3: Guardar el archivo (Resultado Estructurado)
            // ----------------------------------------------------
            System.out.println("Extracción completada. Guardando datos en productos_limpios.csv");
            FileWriter writer = new FileWriter("productos_limpios.csv");
            writer.write(csvData.toString());
            writer.close();
            System.out.println("Proceso finalizado con éxito. Revisa el archivo productos_limpios.csv");
            
        } catch (IOException e) {
            System.err.println("Error al acceder al archivo index.html o al escribir el CSV.");
            e.printStackTrace();
        }
    }
}