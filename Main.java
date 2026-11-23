import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static double[] chuvas = new double[7];
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
            System.out.println("4 - Sair");
            System.out.print("Escolha uma opcao: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Opcao invalida. Digite um numero de 1 a 4.");
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
                    System.out.println("Sistema encerrado.");
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        } while (opcao != 4);
    }

    public static void cadastrarChuvas() {
        double totalChuva = 0;
        int diaMaisChuvoso = 0;

        for (int i = 0; i < chuvas.length; i++) {
            chuvas[i] = lerNumeroEntre("Chuva do dia " + (i + 1) + " em mm: ", 0, Double.MAX_VALUE);
            totalChuva = totalChuva + chuvas[i];

            if (chuvas[i] > chuvas[diaMaisChuvoso]) {
                diaMaisChuvoso = i;
            }
        }

        System.out.printf("Media semanal de chuva: %.2f mm%n", totalChuva / chuvas.length);
        System.out.println("Dia com maior chuva: dia " + (diaMaisChuvoso + 1));
    }

    public static void cadastrarTalhoes() {
        for (int linha = 0; linha < umidades.length; linha++) {
            for (int coluna = 0; coluna < umidades[linha].length; coluna++) {
                umidades[linha][coluna] = lerNumeroEntre(
                        "Umidade do talhao [" + (linha + 1) + "][" + (coluna + 1) + "] em %: ", 0, 100);
            }
        }
    }

    public static void exibirMapa() {
        System.out.println("Mapa de umidade:");
        System.out.println("       C1     C2     C3     C4");
        for (int linha = 0; linha < umidades.length; linha++) {
            System.out.print("L" + (linha + 1) + "   ");
            for (int coluna = 0; coluna < umidades[linha].length; coluna++) {
                System.out.printf("%.1f%% ", umidades[linha][coluna]);
            }
            System.out.println();
        }
    }

    public static void gerarRelatorioIrrigacao() {
        int quantidadeAlertas = 0;
        System.out.println("Talhoes que precisam de irrigacao:");
        for (int linha = 0; linha < umidades.length; linha++) {
            for (int coluna = 0; coluna < umidades[linha].length; coluna++) {
                if (umidades[linha][coluna] < 30) {
                    System.out.println("Talhao [" + (linha + 1) + "][" + (coluna + 1) + "]");
                    quantidadeAlertas++;
                }
            }
        }
        if (quantidadeAlertas == 0) {
            System.out.println("Nenhum talhao precisa de irrigacao.");
        }
        System.out.println("Total de talhoes em alerta: " + quantidadeAlertas);
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
