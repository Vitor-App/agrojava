# Como executar o AgroJava

O AgroJava é um programa simples de terminal feito em Java. Ele utiliza `Scanner`, um array de sete posições para as chuvas, uma matriz 4x4 para a umidade dos talhões e arquivos CSV para guardar os dados.

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

## Menu

```
1 - Cadastrar Dados
2 - Exibir Mapa do Campo
3 - Relatorio de Alertas de Irrigacao
4 - Consultar Historico de Chuvas
5 - Semana Mais Chuvosa
6 - Dashboard Climatico
7 - Relatorio Gerencial da Fazenda
8 - Exportar Relatorio
0 - Sair
```

## Funcionamento

Na opção 1 o programa pede a data da semana (dd/mm/aaaa), a chuva de segunda a domingo e a umidade dos 16 talhões. A semana é acrescentada no arquivo `chuvas.csv` e o mapa é salvo em `talhoes.csv`. Os arquivos são criados automaticamente na mesma pasta do programa.

As opções 4, 5 e 6 usam o histórico salvo em `chuvas.csv`. As opções 2, 3, 7 e 8 usam o mapa de umidade, que é carregado do `talhoes.csv` quando o programa inicia. A opção 8 gera o arquivo `relatorio_fazenda.txt`.

O sistema aceita chuva a partir de 0 mm e umidade entre 0% e 100%. Entradas inválidas são solicitadas novamente.
