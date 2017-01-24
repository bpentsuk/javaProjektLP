import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String args[]) {

        System.out.println("Laevade pommitamine!");
        System.out.println("Brita Pentsuk");
        System.out.println("I200");

        // Mängulaua algseis ja mängijale nähtav osa
        int[][] playgroundAtFirst = new int[5][5];
        int[][] playgroundNow = new int[5][5];

        // 0 - meri
        // 1 - laev
        // 2 - pihta saanud laev

        // Määrab ära kaks tsüklit teineteise sees, et saaks igat punkti eraldi määratleda ehk 1x2 laevad
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                playgroundAtFirst[i][j] = (int) (Math.random() * 2); // (int) hoolitseb selle eest, tagastatud
                                                                     // komakohaga väärtusel n.ö raiutakse komakoht ära
                                                                     // Math.random() tagastab umbes pooleks 0-id ja 1-d
                                                                     // mida väiksem number, seda vähem 1-d e laevu, võib olla nt 1.3
            }
        }

        // Käivitatakse väline meetod PrintPlayground talle omastatud andmetega
        PrintPlayground(playgroundNow);

        // Suuname andmed skännerobjektile, kasutajalt sisendi saamiseks
        Scanner sc = new Scanner(System.in);

        //Mängu while-tsükkel, mis töötab nii kaua, kuni break-käsuni
        while (true) {
            System.out.println("Vali, kuhu pommitada? Kas x-y");
            String input = sc.nextLine();                            // Kasutajalt sisendi küsimine
            String[] xy = input.split("-");                   // Poolitab x-y string kaheks osaks
            int x = Integer.parseInt(xy[0]) - 1;                    // Muudab string int-iks
            int y = Integer.parseInt(xy[1]) - 1;


            int hit = playgroundAtFirst[y][x];              // Küsib x-y positsioonilt numbrit
            if (hit == 1) {                                 // Kui tabamus on 1, st laev sai pihta, siis salvestatakse
                playgroundNow[y][x] = 2;                    // tabamus mängulauale
                playgroundNow[y][x] = 2;                    // ja kuvatakse ka mängijale
                System.out.println("Pihtas-põhjas!");
            } else if (hit == 0) {                          // Kui tabamus on 0, st laev ei saanud pihta, siis kuvatakse
                playgroundNow[y][x] = 3;                    // mängijale sellele kohale nr 3 (taustlauale pole vaja märkida)
                System.out.println("Tabamus läks mööda!");
            } else if (hit == 2) {                          // Kui ühte ja sama kohta on juba korduvalt pommitatud
                System.out.println("Seda kohta oled juba pommitanud!");
            } else {
                System.out.println("Midagi läks viltu! Proovi uuesti!");
            }

            PrintPlayground(playgroundNow); // Iga mänguga kuvatakse mängijale uus mängulaud

            // Kontrollib kogu mängulaua pikkuses, kas mäng võib olla läbi: sellisel juhul,
            // kui mõni allesolev laev leitakse, mäng ei saa läbi veel olla
            boolean over = GameOver(playgroundAtFirst); //
            if  (over == true) {
                break;
            }
        }
        System.out.println("Mäng läbi!");
    }

    // boolean - lõpuks tagastatakse meetodi tulemus, siinkohal korduvalt false ja lõpuks true
    // meetod võtab sisendiks maatriksi(?), nimetame selle "playgroundFirst" muutujaks
    private static boolean GameOver(int[][] playgroundAtFirst) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (playgroundAtFirst[i][j] == 1) {
                    return false;
                }
            }

        }
        return true;
    }

    public static void PrintPlayground(int[][] playground) {
        for (int i = 0; i < 5; i++) {
            System.out.println(Arrays.toString(playground[i]));
        }
    }

}
