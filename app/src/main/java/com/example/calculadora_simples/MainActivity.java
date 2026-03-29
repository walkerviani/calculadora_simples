package com.example.calculadora_simples;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView textPainel;
    private EditText editPrimeiroNum, editSegundoNum;
    private Button btnSoma, btnSubtracao, btnMultiplicacao, btnDivisao, btnCalcular, btnLimpar;
    private String operacao = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textPainel = findViewById(R.id.painel);
        editPrimeiroNum = findViewById(R.id.primeiroNumeroInput);
        editSegundoNum = findViewById(R.id.segundoNumeroInput);
        btnSoma = findViewById(R.id.somaButton);
        btnSubtracao = findViewById(R.id.subtracaoButton);
        btnMultiplicacao = findViewById(R.id.multiplicacaoButton);
        btnDivisao = findViewById(R.id.divisaoButton);
        btnCalcular = findViewById(R.id.calcularButton);
        btnLimpar = findViewById(R.id.limparButton);

        btnSoma.setOnClickListener(v -> operacao = "+");
        btnDivisao.setOnClickListener(v -> operacao = "/");
        btnMultiplicacao.setOnClickListener(v -> operacao = "*");
        btnSubtracao.setOnClickListener(v -> operacao = "-");
        btnCalcular.setOnClickListener(v -> executarCalculo());
        btnLimpar.setOnClickListener((v) -> limparTela());

        editPrimeiroNum.addTextChangedListener(criarTextWatcher(editPrimeiroNum));
        editSegundoNum.addTextChangedListener(criarTextWatcher(editSegundoNum));
    }

    private void calcular(String operacao) {
        String valor1 = editPrimeiroNum.getText().toString();
        String valor2 = editSegundoNum.getText().toString();

        if (valor1.isEmpty() || valor2.isEmpty()) {
            textPainel.setText("Preencha todos os campos!");
            return;
        }

        if (operacao.isEmpty()) {
            textPainel.setText("Escolha uma das operações: (+,-,*,/)");
            return;
        }

        try {
            double num1 = Double.parseDouble(valor1);
            double num2 = Double.parseDouble(valor2);
            double resultado = 0;
            switch (operacao) {
                case ("+"):
                    resultado = num1 + num2;
                    textPainel.setText(String.format("%.2f", num1) + " + " + String.format("%.2f", num2) + " = " + String.format("%.2f", resultado));
                    break;
                case ("-"):
                    resultado = num1 - num2;
                    textPainel.setText(String.format("%.2f", num1) + " - " + String.format("%.2f", num2) + " = " + String.format("%.2f", resultado));
                    break;
                case ("*"):
                    resultado = num1 * num2;
                    textPainel.setText(String.format("%.2f", num1) + " * " + String.format("%.2f", num2) + " = " + String.format("%.2f", resultado));
                    break;
                case ("/"):
                    resultado = num1 / num2;
                    if (num2 == 0) {
                        textPainel.setText("Não é possível dividir por zero!");
                        break;
                    }
                    textPainel.setText(String.format("%.2f", num1) + " / " + String.format("%.2f", num2) + " = " + String.format("%.2f", resultado));
                    break;
                default:
                    textPainel.setText("Operação inválida!");
                    break;
            }
        } catch (NumberFormatException e) {
            textPainel.setText("Digite valores válidos!");
        }
    }

    private void executarCalculo() {
        calcular(operacao);
    }

    private void limparTela() {
        textPainel.setText("");
        editPrimeiroNum.setText("");
        editSegundoNum.setText("");
        operacao = "";
    }

    private TextWatcher criarTextWatcher(EditText editText) {
        return new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                String texto = s.toString();

                if (texto.startsWith(".")) {
                    editText.removeTextChangedListener(this);
                    String corrigido = "0" + texto;
                    editText.setText(corrigido);
                    editText.setSelection(corrigido.length());
                    editText.addTextChangedListener(this);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        };
    }
}
