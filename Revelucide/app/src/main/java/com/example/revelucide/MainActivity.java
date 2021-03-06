package com.example.revelucide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.example.revelucide.models.Reve;
import com.example.revelucide.models.ReveAdapter;
import com.example.revelucide.models.bottomNavBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity { // JournalActivity

    private ListView listView;
    private SharedPreferences sp;
    private ReveAdapter reveAdapter;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialisation de firebase authentification
        mAuth = FirebaseAuth.getInstance();

        listView = findViewById(R.id.listView);
        EditText filter = findViewById(R.id.searchBar);

        this.loadData();
        reveAdapter = new ReveAdapter(this, R.layout.listview_row, Reve.getReveLog());

        listView.setAdapter(reveAdapter);

        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (MainActivity.this).reveAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomBar);
        bottomNavBar.createBottomNavBar(0, bottomNavigationView, MainActivity.this);

        FloatingActionButton buton = findViewById(R.id.boutonAjout);

        buton.setOnClickListener(v -> {
            Intent intent0 = new Intent(MainActivity.this, AjoutActivity.class);
            startActivity(intent0);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main_menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        MenuItem itemLogin = menu.findItem(R.id.login);
        MenuItem itemLogout = menu.findItem(R.id.logout);
        if (currentUser != null) {
            itemLogin.setVisible(false);
            itemLogout.setVisible(true);
        } else {
            itemLogin.setVisible(true);
            itemLogout.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        MenuItem itemLogin = menu.findItem(R.id.login);
        MenuItem itemLogout = menu.findItem(R.id.logout);
        if (currentUser != null) {
            itemLogin.setVisible(false);
            itemLogout.setVisible(true);
        } else {
            itemLogin.setVisible(true);
            itemLogout.setVisible(false);
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.login:
                Intent intent0 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent0);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void loadData() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) { // si un utilisateur est connecté faire un envoie a la base
            // je dois spécifier l'url car sinon elle n'est pas bonne
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://reve-lucide-default-rtdb.europe-west1.firebasedatabase.app/");
            DatabaseReference myRef = database.getReference(currentUser.getUid());

            //String json = myRef.getDatabase().getReference("reveList");
        } else {

        }
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

    private void saveData() {
        sp = getSharedPreferences("ReveLucidePref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(Reve.getReveLog());
        editor.putString("reveLog", json);
        editor.apply();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) { // si un utilisateur est connecté faire un envoie a la base
            // je dois spécifier l'url car sinon elle n'est pas bonne
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://reve-lucide-default-rtdb.europe-west1.firebasedatabase.app/");
            DatabaseReference myRef = database.getReference();
            // je crée un noeu unique pour chaque utilisateur
            myRef.child(currentUser.getUid()).child("reveList").setValue(json);
            myRef.push();
        }
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
        this.saveData();
    }
}
