# Como executar o AgroJava

O AgroJava é um programa simples de terminal feito em Java. Ele utiliza `Scanner`, um array de sete posições para as chuvas e uma matriz 4x4 para a umidade dos talhões.

## Compilar

Na pasta do arquivo `Main.java`, execute:

```bash
javac Main.java
```

## Executar

Depois da compilação, execute:

```bash
java Main
```

## Funcionamento

Escolha a opção 1 para cadastrar os sete volumes de chuva e as umidades dos 16 talhões. Depois do cadastro, a opção 2 mostra o mapa formatado e a opção 3 lista os talhões com umidade abaixo de 30%. A opção 4 encerra o programa.

O sistema aceita chuva entre 0 mm e o maior valor numérico permitido e umidade entre 0% e 100%. Entradas inválidas são solicitadas novamente.
