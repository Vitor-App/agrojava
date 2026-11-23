import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static double[] chuvas = new double[7];
    static String[] diasSemana = {"segunda", "terca", "quarta", "quinta", "sexta", "sabado", "domingo"};
    static double[][] umidades = new double[4][4];
    static boolean dadosCadastrados = false;

    public static void main(String[] args) {
        menuPrincipal();
        scanner.close();
    }

    public static void menuPrincipal() {
        int opcao;

        do {
            System.out.println("\n=== AGROJAVA ===");
            System.out.println("1 - Cadastrar Dados");
            System.out.println("2 - Exibir Mapa do Campo");
            System.out.println("3 - Relatorio de Alertas de Irrigacao");
            System.out.println("4 - Consultar Historico de Chuvas");
            System.out.println("5 - Semana Mais Chuvosa");
            System.out.println("6 - Dashboard Climatico");
            System.out.println("7 - Relatorio Gerencial da Fazenda");
            System.out.println("8 - Exportar Relatorio");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opcao: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Opcao invalida. Digite um numero do menu.");
                scanner.next();
                System.out.print("Escolha uma opcao: ");
            }
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    cadastrarChuvas();
                    cadastrarTalhoes();
                    dadosCadastrados = true;
                    System.out.println("Dados cadastrados.");
                    break;
                case 2:
                    if (!dadosCadastrados) {
                        System.out.println("Cadastre os dados primeiro.");
                    } else {
                        exibirMapa();
                    }
                    break;
                case 3:
                    if (!dadosCadastrados) {
                        System.out.println("Cadastre os dados primeiro.");
                    } else {
                        gerarRelatorioIrrigacao();
                    }
                    break;
                case 4:
                    consultarHistoricoChuvas();
                    break;
                case 5:
                    identificarSemanaMaisChuvosa();
                    break;
                case 6:
                    gerarDashboardClimatico();
                    break;
                case 7:
                    if (!dadosCadastrados) {
                        System.out.println("Cadastre os dados primeiro.");
                    } else {
                        gerarRelatorioGerencial();
                    }
                    break;
                case 8:
                    if (!dadosCadastrados) {
                        System.out.println("Cadastre os dados primeiro.");
                    } else {
                        exportarRelatorio();
                    }
                    break;
                case 0:
                    System.out.println("Sistema encerrado.");
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    public static void cadastrarChuvas() {
        System.out.print("Data da semana (dd/mm/aaaa): ");
        String data = scanner.next();
        while (!data.matches("\\d{2}/\\d{2}/\\d{4}")) {
            System.out.println("Data invalida. Use o formato dd/mm/aaaa.");
            System.out.print("Data da semana (dd/mm/aaaa): ");
            data = scanner.next();
        }

        double totalChuva = 0;
        int diaMaisChuvoso = 0;

        for (int i = 0; i < chuvas.length; i++) {
            chuvas[i] = lerNumeroEntre("Chuva de " + diasSemana[i] + " em mm: ", 0, Double.MAX_VALUE);
            totalChuva = totalChuva + chuvas[i];

            if (chuvas[i] > chuvas[diaMaisChuvoso]) {
                diaMaisChuvoso = i;
            }
        }

        double media = totalChuva / chuvas.length;
        System.out.printf("Media semanal de chuva: %.2f mm%n", media);
        System.out.printf("Total de chuva na semana: %.2f mm%n", totalChuva);
        System.out.println("Dia com maior chuva: " + diasSemana[diaMaisChuvoso]);

        salvarHistoricoChuvas(data, media, totalChuva);
    }

    public static void salvarHistoricoChuvas(String data, double media, double total) {
        File arquivo = new File("chuvas.csv");
        boolean arquivoNovo = !arquivo.exists() || arquivo.length() == 0;

        try {
            BufferedWriter escritor = new BufferedWriter(new FileWriter(arquivo, true));
            if (arquivoNovo) {
                escritor.write("data,seg,ter,qua,qui,sex,sab,dom,media,total");
                escritor.newLine();
            }

            String linha = data;
            for (int i = 0; i < chuvas.length; i++) {
                linha = linha + "," + formatarNumero(chuvas[i]);
            }
            linha = linha + "," + formatarNumero(media) + "," + formatarNumero(total);

            escritor.write(linha);
            escritor.newLine();
            escritor.close();
            System.out.println("Semana salva no arquivo chuvas.csv");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o historico de chuvas.");
        }
    }

    public static String formatarNumero(double numero) {
        if (numero == (long) numero) {
            return String.valueOf((long) numero);
        }
        return String.valueOf(Math.round(numero * 10) / 10.0);
    }

    public static ArrayList<String> lerHistoricoChuvas() {
        ArrayList<String> linhas = new ArrayList<>();
        File arquivo = new File("chuvas.csv");
        if (!arquivo.exists()) {
            return linhas;
        }

        try {
            BufferedReader leitor = new BufferedReader(new FileReader(arquivo));
            String linha = leitor.readLine();
            while ((linha = leitor.readLine()) != null) {
                if (linha.split(",").length == 10) {
                    linhas.add(linha);
                }
            }
            leitor.close();
        } catch (IOException e) {
            System.out.println("Erro ao ler o historico de chuvas.");
        }
        return linhas;
    }

    public static void consultarHistoricoChuvas() {
        ArrayList<String> linhas = lerHistoricoChuvas();
        if (linhas.size() == 0) {
            System.out.println("Nenhuma semana cadastrada.");
            return;
        }

        System.out.println("===== HISTORICO DE CHUVAS =====");
        for (int i = 0; i < linhas.size(); i++) {
            String[] campos = linhas.get(i).split(",");
            System.out.println("Semana: " + campos[0]);
            System.out.println("Media: " + campos[8] + " mm");
            System.out.println("Total: " + campos[9] + " mm");
            System.out.println();
        }
    }

    public static void identificarSemanaMaisChuvosa() {
        ArrayList<String> linhas = lerHistoricoChuvas();
        if (linhas.size() == 0) {
            System.out.println("Nenhuma semana cadastrada.");
            return;
        }

        String semanaMaior = "";
        double maiorTotal = -1;
        for (int i = 0; i < linhas.size(); i++) {
            String[] campos = linhas.get(i).split(",");
            double total = Double.parseDouble(campos[9]);
            if (total > maiorTotal) {
                maiorTotal = total;
                semanaMaior = campos[0];
            }
        }

        System.out.println("Semana mais chuvosa:");
        System.out.println(semanaMaior);
        System.out.println("Total:");
        System.out.println(formatarNumero(maiorTotal) + " mm");
    }

    public static void gerarDashboardClimatico() {
        ArrayList<String> linhas = lerHistoricoChuvas();
        if (linhas.size() == 0) {
            System.out.println("Nenhuma semana cadastrada.");
            return;
        }

        double volumeTotal = 0;
        double maiorChuva = -1;
        double menorChuva = Double.MAX_VALUE;
        String semanaMaisChuvosa = "";
        String semanaMaisSeca = "";

        for (int i = 0; i < linhas.size(); i++) {
            String[] campos = linhas.get(i).split(",");
            double total = Double.parseDouble(campos[9]);
            volumeTotal = volumeTotal + total;

            if (total > maiorChuva) {
                maiorChuva = total;
                semanaMaisChuvosa = campos[0];
            }
            if (total < menorChuva) {
                menorChuva = total;
                semanaMaisSeca = campos[0];
            }
        }

        double mediaGeral = volumeTotal / linhas.size();

        System.out.println("===== DASHBOARD =====");
        System.out.println("Semanas cadastradas: " + linhas.size());
        System.out.println("Volume acumulado: " + formatarNumero(volumeTotal) + " mm");
        System.out.println("Maior chuva:");
        System.out.println(formatarNumero(maiorChuva) + " mm (" + semanaMaisChuvosa + ")");
        System.out.println("Menor chuva:");
        System.out.println(formatarNumero(menorChuva) + " mm (" + semanaMaisSeca + ")");
        System.out.println("Media geral:");
        System.out.printf("%.1f mm%n", mediaGeral);
    }

    public static void cadastrarTalhoes() {
        for (int linha = 0; linha < umidades.length; linha++) {
            for (int coluna = 0; coluna < umidades[linha].length; coluna++) {
                umidades[linha][coluna] = lerNumeroEntre(
                        "Umidade do talhao " + nomeTalhao(linha, coluna) + " em %: ", 0, 100);
            }
        }

        salvarMapaTalhoes();
    }

    public static String nomeTalhao(int linha, int coluna) {
        char letra = (char) ('A' + linha);
        return letra + "" + (coluna + 1);
    }

    public static String classificarTalhao(double umidade) {
        if (umidade < 20) {
            return "Critico";
        } else if (umidade <= 30) {
            return "Atencao";
        } else {
            return "Adequado";
        }
    }

    public static void salvarMapaTalhoes() {
        try {
            BufferedWriter escritor = new BufferedWriter(new FileWriter("talhoes.csv"));
            escritor.write("talhao,umidade,status");
            escritor.newLine();
            for (int linha = 0; linha < umidades.length; linha++) {
                for (int coluna = 0; coluna < umidades[linha].length; coluna++) {
                    double umidade = umidades[linha][coluna];
                    escritor.write(nomeTalhao(linha, coluna) + "," + formatarNumero(umidade) + "," + classificarTalhao(umidade));
                    escritor.newLine();
                }
            }
            escritor.close();
            System.out.println("Mapa salvo no arquivo talhoes.csv");
        } catch (IOException e) {
            System.out.println("Erro ao salvar o mapa de umidade.");
        }
    }

    public static void exibirMapa() {
        System.out.println("Mapa de umidade:");
        System.out.println("          1        2        3        4");
        for (int linha = 0; linha < umidades.length; linha++) {
            System.out.print((char) ('A' + linha) + " ");
            for (int coluna = 0; coluna < umidades[linha].length; coluna++) {
                System.out.printf("%8.1f%%", umidades[linha][coluna]);
            }
            System.out.println();
        }

        System.out.println();
        System.out.println("Classificacao dos talhoes:");
        for (int linha = 0; linha < umidades.length; linha++) {
            for (int coluna = 0; coluna < umidades[linha].length; coluna++) {
                double umidade = umidades[linha][coluna];
                System.out.printf("%s - %.1f%% - %s%n", nomeTalhao(linha, coluna), umidade, classificarTalhao(umidade));
            }
        }
    }

    public static void gerarRelatorioIrrigacao() {
        int quantidadeAlertas = 0;
        System.out.println("===== ALERTAS =====");
        for (int linha = 0; linha < umidades.length; linha++) {
            for (int coluna = 0; coluna < umidades[linha].length; coluna++) {
                String status = classificarTalhao(umidades[linha][coluna]);
                if (status.equals("Critico") || status.equals("Atencao")) {
                    System.out.println(nomeTalhao(linha, coluna) + " - " + status);
                    quantidadeAlertas++;
                }
            }
        }
        if (quantidadeAlertas == 0) {
            System.out.println("Nenhum talhao precisa de irrigacao.");
        }
        System.out.println("Total de alertas: " + quantidadeAlertas);
    }

    public static String montarRelatorioGerencial() {
        double soma = 0;
        int criticos = 0;
        int atencao = 0;
        int adequados = 0;
        int totalTalhoes = 0;
        int linhaSeco = 0, colunaSeco = 0;
        int linhaUmido = 0, colunaUmido = 0;

        for (int linha = 0; linha < umidades.length; linha++) {
            for (int coluna = 0; coluna < umidades[linha].length; coluna++) {
                double umidade = umidades[linha][coluna];
                soma = soma + umidade;
                totalTalhoes++;

                String status = classificarTalhao(umidade);
                if (status.equals("Critico")) {
                    criticos++;
                } else if (status.equals("Atencao")) {
                    atencao++;
                } else {
                    adequados++;
                }

                if (umidade < umidades[linhaSeco][colunaSeco]) {
                    linhaSeco = linha;
                    colunaSeco = coluna;
                }
                if (umidade > umidades[linhaUmido][colunaUmido]) {
                    linhaUmido = linha;
                    colunaUmido = coluna;
                }
            }
        }

        double media = soma / totalTalhoes;
        double percentualIrrigacao = (criticos + atencao) * 100.0 / totalTalhoes;

        String relatorio = "";
        relatorio = relatorio + String.format("Media Geral: %.0f%%%n", media);
        relatorio = relatorio + "Talhoes Criticos: " + criticos + "\n";
        relatorio = relatorio + "Talhoes em Atencao: " + atencao + "\n";
        relatorio = relatorio + "Talhoes Adequados: " + adequados + "\n";
        relatorio = relatorio + "Necessidade de Irrigacao:\n";
        relatorio = relatorio + String.format("%.0f%%%n", percentualIrrigacao);
        relatorio = relatorio + "Talhao mais seco:\n";
        relatorio = relatorio + String.format("%s - %.0f%%%n", nomeTalhao(linhaSeco, colunaSeco), umidades[linhaSeco][colunaSeco]);
        relatorio = relatorio + "Talhao mais umido:\n";
        relatorio = relatorio + String.format("%s - %.0f%%%n", nomeTalhao(linhaUmido, colunaUmido), umidades[linhaUmido][colunaUmido]);
        return relatorio;
    }

    public static void gerarRelatorioGerencial() {
        System.out.println("===== RELATORIO DA FAZENDA =====");
        System.out.print(montarRelatorioGerencial());
    }

    public static void exportarRelatorio() {
        String data = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        try {
            BufferedWriter escritor = new BufferedWriter(new FileWriter("relatorio_fazenda.txt"));
            escritor.write("===== RELATORIO DA FAZENDA =====");
            escritor.newLine();
            escritor.write("Data: " + data);
            escritor.newLine();
            escritor.write(montarRelatorioGerencial());
            escritor.close();
            System.out.println("Relatorio exportado para relatorio_fazenda.txt");
        } catch (IOException e) {
            System.out.println("Erro ao exportar o relatorio.");
        }
    }

    public static double lerNumeroEntre(String mensagem, double minimo, double maximo) {
        double valor;
        do {
            System.out.print(mensagem);
            while (!scanner.hasNextDouble()) {
                System.out.println("Valor invalido. Digite um numero.");
                scanner.next();
                System.out.print(mensagem);
            }
            valor = scanner.nextDouble();
            if (!Double.isFinite(valor) || valor < minimo || valor > maximo) {
                System.out.println("Valor fora do limite permitido.");
            }
        } while (!Double.isFinite(valor) || valor < minimo || valor > maximo);
        return valor;
    }
}
