/*
 * App MaltratoCero for Study Jam GDG Buenos Aires
 * Developed by Juan Pablo Quiñones in May 2016
 * e-mail: jpq.1987@gmail.com
 *
 */
package com.gdg.jpq.maltratocero;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.gdg.jpq.maltratocero.adapter.UsefulInfoAdapter;

/**
 * Corresponds to UsefulInformation Class showing
 * FAQs about gender violence
 */
public class UsefulInformationActivity extends AppCompatActivity {

    private List<String> mListUsefulInfoDataTitle;
    private HashMap<String, List<String>> mListUsefulInfoDataDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useful_information);

        if(getActionBar() != null){
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // get the listview
        ExpandableListView mListViewUsefulInfo = (ExpandableListView) findViewById(R.id.expandable_list_view_usefulInfo);

        // preparing list data
        prepareListData();
        UsefulInfoAdapter mUsefulInfoAdapter = new UsefulInfoAdapter(this, mListUsefulInfoDataTitle, mListUsefulInfoDataDetail);

        // setting list adapter
        mListViewUsefulInfo.setAdapter(mUsefulInfoAdapter);
    }

    // loading the data list
    private void prepareListData(){
        mListUsefulInfoDataTitle = new ArrayList<String>();
        mListUsefulInfoDataDetail = new HashMap<String, List<String>>();

        // adding faq titles
        mListUsefulInfoDataTitle.add("Qué es la Violencia de género?");
        mListUsefulInfoDataTitle.add("Qué tipos de Violencia existen?");
        mListUsefulInfoDataTitle.add("Qué es y cómo funciona la línea telefónica 144?");
        mListUsefulInfoDataTitle.add("Cuáles son las modalidades de Violencia?");

        // adding faq detail
        List<String> faq1 = new ArrayList<String>();
        faq1.add("La violencia de género es un tipo de violencia física o psicológica ejercida contra cualquier persona sobre la base de su sexo o género que impacta de manera negativa su identidad y bienestar social, físico o psicológico.6 De acuerdo a Naciones Unidas, el término es utilizado «para distinguir la violencia común de aquella que se dirige a individuos o grupos sobre la base de su género», enfoque compartido por la ONG Observatorio de Derechos Humanos en diversos estudios realizados durante los últimos años.\n" +
                "Para la organización ONU-mujeres, este tipo de violencia «se refiere a aquella dirigida contra una persona en razón del género que él o ella tiene así como de las expectativas sobre el rol que él o ella deba cumplir en una sociedad o cultura» Y añade, \"la violencia basada en el género pone de relieve cómo la dimensión de género está presente en este tipo de actos, es decir, la relación entre el estado de subordinación femenina en la sociedad y su creciente vulnerabilidad respecto a la violencia. Ésta presenta distintas manifestaciones e incluye, de acuerdo al Comité para la Eliminación de la Discriminación contra la Mujer, actos que causan sufrimiento o daño, amenazas, coerción u otra privación de libertades. Estos actos se manifiestan en diversos ámbitos de la vida social y política, entre los que se encuentran la propia familia, la escuela, la Iglesia, entre otras.");
        List<String> faq2 = new ArrayList<String>();
        faq2.add("La violencia de género es un problema que puede incluir asaltos o violaciones sexuales, prostitución forzada, explotación laboral, el aborto selectivo por sexo, violencia física y sexual contra prostitutas y/o prostitutos, infanticidio en base al género, castración parcial o total, ablación de clítoris, tráfico de personas, violaciones sexuales durante período de guerra, patrones de acoso u hostigamiento en organizaciones masculinas, ataques homofóbicos hacia personas o grupos de homosexuales, bisexuales y transgéneros, entre otros.");
        List<String> faq3 = new ArrayList<String>();
        faq3.add("La implementación de la Línea Nacional 144 responde a la obligación de garantizar, como Estado Nacional, una respuesta integral y articulada sobre la violencia de género. Está destinada a brindar información, orientación, asesoramiento y contención a las mujeres en situación de violencia de todo el país, los 365 días del año, las 24 horas, de manera gratuita. El Consejo Nacional de las Mujeres es el organismo de implementación de esta línea telefónica, como establece la Ley 26.485 de Protección Integral para Prevenir, Sancionar y Erradicar la Violencia contra las Mujeres en los ámbitos en que desarrollen sus relaciones interpersonales.");
        List<String> faq4 = new ArrayList<String>();
        faq4.add("Violencia Doméstica: Aquella ejercida contra las mujeres por un integrante del grupo familiar, independientemente del espacio físico donde ésta ocurra, que dañe la dignidad, el bienestar, la integridad física, psicológica, sexual, económica o patrimonial, la libertad, comprendiendo la libertad reproductiva y el derecho al pleno desarrollo de las mujeres. Se entiende por grupo familiar el originado en el parentesco sea por consanguinidad o por afinidad, el matrimonio, las uniones de hecho y las parejas o noviazgos. Incluye las relaciones vigentes o finalizadas, no siendo requisito la convivencia;\n" +
                "\n" +
                "Violencia Institucional: Aquella realizada por las/los funcionarias/os, profesionales, personal y agentes pertenecientes a cualquier órgano, ente o institución pública, que tenga como fin retardar, obstaculizar o impedir que las mujeres tengan acceso a las políticas públicas y ejerzan los derechos previstos en esta ley. Quedan comprendidas, además, las que se ejercen en los partidos políticos, sindicatos, organizaciones empresariales, deportivas y de la sociedad civil;\n" +
                "\n" +
                "Violencia Laboral: Aquella que discrimina a las mujeres en los ámbitos de trabajo públicos o privados y que obstaculiza su acceso al empleo, contratación, ascenso, estabilidad o permanencia en el mismo, exigiendo requisitos sobre estado civil, maternidad, edad, apariencia física o la realización de test de embarazo. Constituye también violencia contra las mujeres en el ámbito laboral quebrantar el derecho de igual remuneración por igual tarea o función. Asimismo, incluye el hostigamiento psicológico en forma sistemática sobre una determinada trabajadora con el fin de lograr su exclusión laboral;\n" +
                "\n" +
                "Violencia Mediática: Aquella publicación o difusión de mensajes e imágenes estereotipados a través de cualquier medio masivo de comunicación, que de manera directa o indirecta promueva la explotación de mujeres o sus imágenes, injurie, difame, discrimine, deshonre, humille o atente contra la dignidad de las mujeres, como así también la utilización de mujeres, adolescentes y niñas en mensajes e imágenes pornográficas, legitimando la desigualdad de trato o construya patrones socioculturales reproductores de la desigualdad o generadores de violencia contra las mujeres.");

        mListUsefulInfoDataDetail.put(mListUsefulInfoDataTitle.get(0), faq1);
        mListUsefulInfoDataDetail.put(mListUsefulInfoDataTitle.get(1), faq2);
        mListUsefulInfoDataDetail.put(mListUsefulInfoDataTitle.get(2), faq3);
        mListUsefulInfoDataDetail.put(mListUsefulInfoDataTitle.get(3), faq4);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_usefulinformation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle presses on the action bar items
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}