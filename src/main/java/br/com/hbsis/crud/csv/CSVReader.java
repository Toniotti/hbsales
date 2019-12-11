package br.com.hbsis.crud.csv;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVReader {
    public static List<String[]> read(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        List<String[]> lines = new ArrayList<>();
        String linha;
        reader.readLine();
        while((linha = reader.readLine()) != null){
            lines.add(linha.split(";"));
        }
        if(!lines.isEmpty()){return lines;}throw new IllegalArgumentException("Não foi possivel ler o arquivo, ou ele está vazio.");
    }
}
