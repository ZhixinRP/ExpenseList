package sg.edu.rp.c346.id20047401.expenselist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {

    TextView tvTotalCost;
    ListView lv;
    ArrayList<Expense> expenseList;
    CustomAdapter caExpense;
    double totalCost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tvTotalCost = findViewById(R.id.textViewTotalCost);
        lv = findViewById(R.id.lv);

        expenseList = new ArrayList<Expense>();

        caExpense = new CustomAdapter(this, R.layout.row, expenseList);
        lv.setAdapter(caExpense);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.input, null);
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(SecondActivity.this);

                final EditText etInputName = viewDialog.findViewById(R.id.editTextName);
                final EditText etInputCost = viewDialog.findViewById(R.id.editTextCost);

                myBuilder.setView(viewDialog);
                myBuilder.setTitle("Add an expense");
                myBuilder.setCancelable(false);
                myBuilder.setNegativeButton("CANCEL", null);
                myBuilder.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbh = new DBHelper(SecondActivity.this);
                        String name = etInputName.getText().toString();
                        double cost = Double.parseDouble(etInputCost.getText().toString());
                        dbh.insertExpense(name,cost);
                        expenseList.clear();
                        expenseList.addAll(dbh.getAllExpenses());
                        caExpense.notifyDataSetChanged();
                        totalCost = 0.0;
                        for(int i = 0; i < expenseList.size();i++){
                            totalCost += expenseList.get(i).getCost();
                        }
                        setTotalCost(totalCost);
                    }
                });
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SecondActivity.this, ThirdActivity.class);
                i.putExtra("expense", expenseList.get(position));
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.clearSelection) {
            expenseList.clear();
            DBHelper dbh = new DBHelper(SecondActivity.this);
            dbh.deleteAllExpense();
            caExpense.notifyDataSetChanged();
            totalCost = 0.0;
            setTotalCost(totalCost);
        }
        return super.onOptionsItemSelected(item);

    }

    public void setTotalCost(double totalCost) {
        tvTotalCost.setText("Total Cost: $" +  String.format("%.2f", totalCost));
    }

    @Override
    protected void onResume() {
        super.onResume();
        DBHelper dbh = new DBHelper(this);
        expenseList.clear();
        expenseList.addAll(dbh.getAllExpenses());
        caExpense.notifyDataSetChanged();
        totalCost = 0.0;
        for(int i = 0; i < expenseList.size();i++){
            totalCost += expenseList.get(i).getCost();
        }
        setTotalCost(totalCost);
    }

}