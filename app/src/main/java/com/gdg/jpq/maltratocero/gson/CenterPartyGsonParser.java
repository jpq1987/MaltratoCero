package com.gdg.jpq.maltratocero.gson;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.gdg.jpq.maltratocero.model.CenterParty;


public class CenterPartyGsonParser {

    public List<CenterParty> leerFlujoJson(InputStream in) throws IOException {
        // Nueva instancia de la clase Gson
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new InputStreamReader(in));
        List<CenterParty> centrosAtencion = new ArrayList<>();
        // Iniciar el array
        reader.beginArray();
        while (reader.hasNext()) {
            // Lectura de objetos
            CenterParty centroAtencion = gson.fromJson(reader, CenterParty.class);
            centrosAtencion.add(centroAtencion);
        }
        reader.endArray();
        reader.close();
        return centrosAtencion;
    }
}
