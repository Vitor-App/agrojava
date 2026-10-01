import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double[] chuvas = new double[7];
        double[][] umidades = new double[4][4];
        int opcao;

        do {
            System.out.println("\n=== AGROJAVA ===");
            System.out.println("1 - Cadastrar Dados");
            System.out.println("2 - Exibir Mapa do Campo");
            System.out.println("3 - Relatorio de Alertas de Irrigacao");
            System.out.println("4 - Sair");
            System.out.print("Escolha uma opcao: ");
            opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    double totalChuva = 0;
                    int diaMaisChuvoso = 0;

                    for (int i = 0; i < chuvas.length; i++) {
                        System.out.print("Chuva do dia " + (i + 1) + " em mm: ");
                        chuvas[i] = scanner.nextDouble();
                        totalChuva = totalChuva + chuvas[i];

                        if (chuvas[i] > chuvas[diaMaisChuvoso]) {
                            diaMaisChuvoso = i;
                        }
                    }

                    for (int linha = 0; linha < umidades.length; linha++) {
                        for (int coluna = 0; coluna < umidades[linha].length; coluna++) {
                            System.out.print("Umidade do talhao [" + (linha + 1) + "][" + (coluna + 1) + "] em %: ");
                            umidades[linha][coluna] = scanner.nextDouble();
                        }
                    }

                    System.out.printf("Media semanal de chuva: %.2f mm%n", totalChuva / chuvas.length);
                    System.out.println("Dia com maior chuva: dia " + (diaMaisChuvoso + 1));
                    System.out.println("Dados cadastrados.");
                    break;
                case 2:
                    System.out.println("Mapa de umidade:");
                    for (int linha = 0; linha < umidades.length; linha++) {
                        for (int coluna = 0; coluna < umidades[linha].length; coluna++) {
                            System.out.printf("%.1f%% ", umidades[linha][coluna]);
                        }
                        System.out.println();
                    }
                    break;
                case 3:
                    System.out.println("Talhoes que precisam de irrigacao:");
                    for (int linha = 0; linha < umidades.length; linha++) {
                        for (int coluna = 0; coluna < umidades[linha].length; coluna++) {
                            if (umidades[linha][coluna] < 30) {
                                System.out.println("Talhao [" + (linha + 1) + "][" + (coluna + 1) + "]");
                            }
                        }
                    }
                    break;
                case 4:
                    System.out.println("Sistema encerrado.");
                    break;
                default:
                    System.out.println("Opcao invalida.");
            }
        } while (opcao != 4);

        scanner.close();
    }
}
