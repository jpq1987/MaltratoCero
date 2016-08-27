package com.gdg.jpq.maltratocero.gson;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.gdg.jpq.maltratocero.model.Party;

public class PartyGsonParser {
    public List<Party> leerFlujoJson(InputStream in) throws IOException {
        // Nueva instancia de la clase Gson
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new InputStreamReader(in));
        List<Party> partidos = new ArrayList<>();
        // Iniciar el array
        reader.beginArray();
        while (reader.hasNext()) {
            // Lectura de objetos
            Party partido = gson.fromJson(reader, Party.class);
            partidos.add(partido);
        }
        reader.endArray();
        reader.close();
        return partidos;
    }
}
