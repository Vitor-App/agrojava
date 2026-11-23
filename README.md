# AgroJava

Sistema Integrado do Agronegócio desenvolvido em Java básico para praticar arrays, matrizes, busca, acumulação, estruturas de repetição e leitura/escrita de arquivos.

## Escopo inicial

1. Registro de chuva dos 7 dias da semana usando `double[7]`, com cálculo da média e identificação do dia mais chuvoso.
2. Mapeamento da umidade do solo em uma matriz `double[4][4]`, com alerta para talhões que precisam de irrigação.
3. Menu interativo com cadastro de dados, exibição do mapa, relatório de alertas e saída.

## Evolução 1 - AgroJava Analytics

- RF04: cada semana cadastrada (data e chuva de segunda a domingo) é gravada no final do arquivo `chuvas.csv`, com média e total.
- RF05: consulta de todas as semanas salvas, mesmo depois de fechar o programa.
- RF06: identificação da semana mais chuvosa.
- RF07: dashboard climático com semanas cadastradas, volume acumulado, maior chuva, menor chuva e média geral.

## Evolução 2 - AgroJava SmartFarm

- RF08: o mapa de umidade dos 16 talhões (A1 até D4) é salvo no arquivo `talhoes.csv`.
- RF09: cada talhão recebe uma classificação: Critico (abaixo de 20%), Atencao (de 20% a 30%) e Adequado (acima de 30%).
- RF10: relatório de irrigação com os talhões Critico e Atencao e o total de alertas.
- RF11: relatório gerencial com média de umidade, total de talhões por classificação, percentual de irrigação necessária, talhão mais seco e talhão mais úmido.
- RF12: exportação do relatório gerencial para o arquivo `relatorio_fazenda.txt`.

A aplicação funciona no terminal, usando `Scanner`, arrays, matrizes, `BufferedReader` e `BufferedWriter`.
