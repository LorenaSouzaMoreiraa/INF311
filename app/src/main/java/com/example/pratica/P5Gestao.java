package com.example.pratica;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class P5Gestao extends AppCompatActivity {

    private DBHelperP5 dbHelper;
    private SQLiteDatabase db;
    private LinearLayout layoutConteudo;
    private LinearLayout layoutDeletar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p5gestao);

        dbHelper = DBHelperP5.getInstance(this);
        db = dbHelper.getWritableDatabase();  // writable para inserir no Logs
        layoutConteudo = findViewById(R.id.layoutConteudo);
        layoutDeletar = findViewById(R.id.layoutDeletar);

        populateCheckinList();
    }

    private void populateCheckinList() {
        // Busca todos os nomes de locais do banco
        List<String> locations = dbHelper.getAllLocationNames();

        // Limpa as views antigas para o caso de a tela ser recriada
        layoutConteudo.removeAllViews();
        layoutDeletar.removeAllViews();

        for (String localName : locations) {
            // Cria e adiciona o TextView com o nome do local
            TextView textView = createTextView(localName);
            layoutConteudo.addView(textView);

            // Cria e adiciona o ImageButton para deletar
            ImageButton imageButton = createDeleteButton(localName);
            layoutDeletar.addView(imageButton);
        }
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextColor(getResources().getColor(android.R.color.white));
        textView.setTextSize(18f);
        // Define uma altura fixa e gravidade para alinhar com o botão
        textView.setHeight(150);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setPadding(8, 8, 8, 8);
        return textView;
    }

    private ImageButton createDeleteButton(String localName) {
        ImageButton imageButton = new ImageButton(this);
        // Usa um ícone de deleção padrão do Android
        imageButton.setImageResource(android.R.drawable.ic_delete);
        imageButton.setBackground(null); // Remove o fundo padrão do botão
        // Define a tag do botão como o nome do local, que é a chave primária
        imageButton.setTag(localName);
        // Define o método que será chamado ao clicar.
        imageButton.setOnClickListener(this::handleDeleteClick);
        return imageButton;
    }

    // Este é o método único que trata o clique de todos os botões de deletar.
    public void handleDeleteClick(View view) {
        // Recupera a tag (nome do local) do botão que foi clicado
        String localToDelete = (String) view.getTag();
        showConfirmationDialog(localToDelete);
    }

    private void showConfirmationDialog(String local) {
        new AlertDialog.Builder(this)
                .setTitle("Exclusão")
                .setMessage("Tem certeza que deseja excluir " + local + "?")
                .setPositiveButton("SIM", (dialog, which) -> {
                    // Se o usuário clicar em SIM, deleta o registro
                    dbHelper.deleteCheckin(local);
                    Toast.makeText(this, local + " foi excluído.", Toast.LENGTH_SHORT).show();
                    // Recria a tela para atualizar a lista, como solicitado
                    recreate();
                })
                .setNegativeButton("NÃO", null) // Não faz nada se clicar em NÃO
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.p5gestao, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_voltar_management) {
            finish(); // Fecha a tela atual e volta para a anterior
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
