import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String args[]) {

        /**
         * @author Brita Pentsuk

         * Inspiratsiooni on saadud Krister Viirsaare koodist, mis asub videona aadressil
         * https://www.youtube.com/watch?v=WZdx5IJKuPE
         */

        // Mängulaua algseis ja mängijale nähtav osa
        int[][] playgroundAtFirst = new int[3][3];
        int[][] playgroundAtNow = new int[3][3];

        // 0 - meri, kus laeva pole
        // 1 - pommitamata laev
        // 2 - pihta saanud laev
        // 3 - pommitatud positsioon, kus laeva pole

        System.out.println("LAEVADE POMMITAMINE\n");
        System.out.println("Mängu kaart on mõõtkavas " + playgroundAtFirst.length + " (X/horisontaaltelg) * " + playgroundAtFirst[0].length + " (Y/vertikaaltelg) ruutu");
        System.out.println("Kaardi tingmärgid:\n 0 - meri\n 2 - uputatud laev\n 3 - mööda heidetud pomm\n");
        // Katsete arv
        int attemptCount = 0;
        // Laevade arv maatriksil
        int shipCount = 0;
        // Programmi käivitamise aeg
        long startTime = System.currentTimeMillis();
        int hit = 0;

        // Määrab ära kaks tsüklit teineteise sees, et saaks igat punkti eraldi määratleda ehk 1x2 laevad
        for (int i = 0; i < playgroundAtFirst.length; i++) {
            for (int j = 0; j < playgroundAtFirst[i].length; j++) {
                playgroundAtFirst[i][j] = (int) (Math.random() * 1.5); // (int) hoolitseb selle eest, tagastatud
                                                                     // komakohaga väärtusel n.ö raiutakse komakoht ära
                                                                     // Math.random() tagastab umbes pooleks 0-id ja 1-d
                                                                     // mida väiksem number, seda vähem 1-d e laevu, võib olla nt 1.3
            }
        }

        // Arvutab laevade hulga maatriksil
        for (int i = 0; i < playgroundAtFirst.length; i++) {
            for (int j = 0; j < playgroundAtFirst[i].length; j++) {
                if (playgroundAtFirst[i][j] == 1) {
                    shipCount++;
                }
            }
        }
        // Käivitatakse väline meetod PrintPlayground talle omastatud andmetega
        PrintPlayground(playgroundAtNow);

        // Suuname andmed skännerobjektile, kasutajalt sisendi saamiseks
        Scanner sc = new Scanner(System.in);

        //Mängu while-tsükkel, mis töötab nii kaua, kuni break-käsuni
        while (true) {
            System.out.println("\nMäära pommi heitmise koordinaadid formaadis x-y");
            attemptCount++;                                        // Katsete loenduri suurendamine
            String input = sc.nextLine();                            // Kasutajalt sisendi küsimine
            String[] xy = input.split("-");                   // Poolitab x-y string kaheks osaks
            int x = Integer.parseInt(xy[0]) - 1;                    // Muudab string int-iks
            int y = Integer.parseInt(xy[1]) - 1;                    // Muudab string int-iks
            try {
                hit = playgroundAtFirst[y][x];              // Küsib x-y positsioonilt numbrit
            } catch(ArrayIndexOutOfBoundsException e) {
                System.out.println("Vigane sisestus");
                System.exit(1);
            }
                if (hit == 1) {                                 // Kui tabamus on 1, st laev sai pihta, siis salvestatakse
                playgroundAtFirst[y][x] = 2;                    // tabamus mängulauale
                playgroundAtNow[y][x] = 2;                    // ja kuvatakse ka mängijale
                System.out.println("Pihtas-põhjas!");
            } else if (hit == 0) {                          // Kui tabamus on 0, st laev ei saanud pihta, siis kuvatakse
                playgroundAtFirst[y][x] = 3;
                playgroundAtNow[y][x] = 3;                   // mängijale sellele kohale nr 3 (taustlauale pole vaja märkida)
                System.out.println("Tabamus läks mööda!");
            } else if (hit == 2 || hit == 3) {                          // Kui ühte ja sama kohta on juba korduvalt pommitatud
                System.out.println("Seda kohta oled juba pommitanud!");
            }

            PrintPlayground(playgroundAtNow); // Iga mänguga kuvatakse mängijale uus mängulaud

            // Kontrollib kogu mängulaua pikkuses, kas mäng võib olla läbi: sellisel juhul,
            // kui mõni allesolev laev leitakse, mäng ei saa läbi veel olla
            boolean over = GameOver(playgroundAtFirst); //
            if  (over == true) {
                break;
            }
        }

        // Programmi töö kestvus
        long runTime = (System.currentTimeMillis() - startTime) / 1000;
        int runTimeMin = (int)runTime/60;
        int runTimeSec = (int)runTime%60;


        System.out.println("VÕITSID!");
        System.out.println("Pomme heidetud: " + attemptCount);
        System.out.println("Heitmise täpsus: " + Math.round((double)shipCount*100/attemptCount) + "%");
        System.out.println("Mängu kestvus: " + runTimeMin + " minutit ja " + runTimeSec + " sekundit");
    }

    // boolean - lõpuks tagastatakse meetodi tulemus, siinkohal korduvalt false ja lõpuks true
    // meetod võtab sisendiks maatriksi(?), nimetame selle "playgroundFirst" muutujaks
    private static boolean GameOver(int[][] playgroundOver) {
        for (int i = 0; i < playgroundOver.length; i++) {
            for (int j = 0; j < playgroundOver[i].length; j++) {
                if (playgroundOver[i][j] == 1) {
                    return false;
                }
            }

        }
        return true;
    }

    public static void PrintPlayground(int[][] playground) {
        for (int i = 0; i < playground.length; i++) {
            System.out.println(Arrays.toString(playground[i]));
        }
    }

}
