package com.example.revelucide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.example.revelucide.models.Reve;
import com.example.revelucide.models.ReveAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity { // JournalActivity

    private ListView listView;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        this.loadData();
        ReveAdapter reveAdapter = new ReveAdapter(this, R.layout.listview_row, Reve.getReveLog());

        listView.setAdapter(reveAdapter);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomBar);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true); // j'active le bouton de l'activité

        bottomNavigationView.setBackground(null);
        menu.getItem(2).setEnabled(false);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> { // listener sur les boutons du bottom navigation bar
            Intent intent0;
            switch (item.getItemId()) {
                case R.id.journal:
                    break;
                case R.id.statistique:
                    intent0 = new Intent(MainActivity.this, StatistiqueActivity.class);
                    startActivity(intent0);
                    break;
                case R.id.recherche:
                    intent0 = new Intent(MainActivity.this, RechercheActivity.class);
                    startActivity(intent0);
                    break;
                case R.id.rappel:
                    intent0 = new Intent(MainActivity.this, RappelActivity.class);
                    startActivity(intent0);
                    break;
            }
            return true;
        });

        FloatingActionButton buton = findViewById(R.id.boutonAjout);

        buton.setOnClickListener(v -> {
            Intent intent0 = new Intent(MainActivity.this, AjoutActivity.class);
            startActivity(intent0);
        });
    }

    private void loadData() {
        sp = getSharedPreferences("ReveLucidePref", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("reveLog", null);
        Type type = new TypeToken<ArrayList<Reve>>() {}.getType();
        Reve.setReveLog( gson.fromJson(json, type));
        if (Reve.getReveLog() == null) {
            this.createReveExample();
            this.loadData();
        }
    }

    private void saveReve() {
        sp = getSharedPreferences("ReveLucidePref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(Reve.getReveLog());
        editor.putString("reveLog", json);
        editor.apply();
    }

    private void createReveExample() {
        Reve.setReveLog(new ArrayList<>());
        Date currentTime = Calendar.getInstance().getTime();
        Reve.addReveLog(new Reve("Les oreillers magiques","Malgré les lois qui fixent qu'on doit aimer le roi, le tyran Croque-peuple ne se sent pas aimé de ses sujets. Alors il invente des oreillers à cauchemars qui rendent leurs journées meilleures que leurs nuits. Mais c'est sans compter sur Antoine, le maître d'école, et ses élèves, qui tentent de déjouer le complot.",
                currentTime,"clair", true));
        Reve.addReveLog(new Reve("Un oiseau gigantesque","Je suis certain que dans la ville où je vis, habite un animal gigantesque",
                currentTime,"clair", false));
        Reve.addReveLog(new Reve("Cours !","Cours, petit bonhomme ! Cours ! Prends tes jambes à ton cou !",
                currentTime,"clair", false));
        Reve.addReveLog(new Reve("Goliath","C'est la nuit... sur le perron d'un manoir abandonné, un très vieux chat, Goliath, dort lourdement. Quand soudain, un ennemi invisible se fait entendre… Goliath en appelle à ses sujets, les arbres, les vents, les oiseaux... Qui est cet ennemi qui s'approche. Mais devenu forts par sa faiblesse, tous refusent de servir le vieux tyran... Cette fable est tendre et humoristique. Elle montre à travers l’histoire de ce vieux matou - ancien roi et tyran, - que les êtres ne sont jamais ni totalement bons, ni totalement mauvais. Et que les vrais ennemis ne sont pas toujours ceux que l’on croit...",
                currentTime,"clair", true));
        Reve.addReveLog(new Reve("Alice ","Alice rêvassait dans un champ d’herbes folles lorsqu’un Lapin Blanc, fort bien vêtu, passa en courant. Près d’une haie, il marqua un arrêt, tira une montre de sa poche et regarda l’heure, avant de s’engouffrer dans un énorme terrier…",
                currentTime,"clair", true));
        Reve.addReveLog(new Reve("Cumulus","Un jour où il s’ennuie, un jeune garçon remarque un cumulus qui flotte seul dans le ciel. Touché par la situation du nuage, qui ressemble étrangement à la sienne, le jeune solitaire entame le dialogue avec le cumulus. Ensemble, ils parcourent le village. Doucement, le garçon se confie au sujet de la séparation de ses parents, de sa solitude, de son école et de la vie en général.",
                currentTime,"clair", false));
        Reve.addReveLog(new Reve("L'Anniversaire","Retomber en enfance... se souvenir d’un fait précis et d’une rencontre marquante ! Que peut-on rêver de mieux que de rencontrer sa future meilleure amie le jour de son anniversaire ? « C’est l’amie dont je rêvais, je suis l’amie qu’elle attendait. » Cependant, cette belle rencontre a un prix très élevé... les petites filles ne pourront pas se revoir sauf avec l’accord de la Reine de la nuit. Que décidera cette dernière ?",
                currentTime,"clair", false));
        Reve.addReveLog(new Reve("Zoe l'abeille","En affrontant les dangers de la vie hors de la ruche et en surmontant sa crainte de la nuit, Zoé la petite abeille va enfin voler de ses propres ailes.",
                currentTime,"clair", false));
        Reve.addReveLog(new Reve("Rêvasser au soleil","L’été, riche en moments de détente, est présenté ici avec simplicité et douceur. Le prétexte : une fillette, en vacances chez son oncle, déambule autour de la maison et croise divers protagonistes qui, tous, les uns après les autres, empruntent quelques minutes à leur routine pour se détendre et observer ce que le ciel estival a à leur offrir. La poésie minimaliste du texte est telle qu’elle suggère un autre regard sur les actions des personnages ; monter la garde peut se faire de plusieurs façons, alors que préparer le repas et lire le journal ont, ici, une toute nouvelle définition.",
                currentTime,"clair", true));
        this.saveReve();
    }
}
