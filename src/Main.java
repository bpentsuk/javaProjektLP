/**
 * Lihtne konsoolipohine laevade pommitamise mang
 * @author Brita Pentsuk
 * @version 1.0
 * Inspiratsiooni on saadud Krister Viirsaare koodist, mis asub videona aadressil
 * @see <a href="https://www.youtube.com/watch?v=WZdx5IJKuPE">Krister Viirsaare naide</a>
 */

// 0 - laevata koordinaat
// 1 - pommitamata laev
// 2 - pommitatud laev
// 3 - pommitatud laevata koordinaat

// Vajaminevate moodulite importimine
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String args[]) {

        // Mangulaua nahtamatu ja mangijale nahtava massiivi loomine
        int[][] playgroundAtFirst = new int[3][3];
        int[][] playgroundAtNow = new int[3][3];

        // Sissejuhatuse kuvamine kasutajale
        System.out.println("======LAEVADE POMMITAMINE======\n");
        System.out.println("Mangu kaart on mootkavas " + playgroundAtFirst.length + " * " + playgroundAtFirst[0].length + " ruutu");
        System.out.println("Kaardi tingmargid:\n 0 - meri\n 2 - uputatud laev\n 3 - moodaheidetud pomm\n");

        // Pommituste arvu loendur
        int attemptCount = 0;

        // Laevade arvu loendur massiivis
        int shipCount = 0;

        // Programmi kaivitamise algusaeg
        long startTime = System.currentTimeMillis();

        // Muutuja "hit" (laeva tabamus) defineerimine erindi tootlemise tarbeks
        int hit = 0;

        /* Nested (Yksteise sees asuvad) for-tsyklid, millega kaiakse yle kahemootmeline massiiv ja paigutatakse
        laevad juhuslikele koordinaatidele (massiivi indeksitele)
        */
        for (int i = 0; i < playgroundAtFirst.length; i++) {
            for (int j = 0; j < playgroundAtFirst[i].length; j++) {
                // Juhusliku taisarvulise vaartuse andmine koordinaadile. Suurem number = suurem toenaosus
                playgroundAtFirst[i][j] = (int) (Math.random() * 1.5);
            }
        }

        // Nested (yksteise sees asuvad) for-tsyklid, millega loetakse kahemootmelisest massiivist laevade hulk
        for (int i = 0; i < playgroundAtFirst.length; i++) {
            for (int j = 0; j < playgroundAtFirst[i].length; j++) {
                if (playgroundAtFirst[i][j] == 1) {
                    shipCount++;
                }
            }
        }

        // Kutsutakse valja meetod, mis kuvab kasutajale nahtava kaardi (massiivi)
        PrintPlayground(playgroundAtNow);

        // Skannerobjekti loomine kasutaja sisendi saamiseks
        Scanner sc = new Scanner(System.in);

        // Mangu pohiloogika, mis toimib kuni break voi vea ilmnemisel exit kaskluseni
        while (true) {

            System.out.println("\nMaara pommi heitmise koordinaadid formaadis X-Y");

            // Pommituste loenduri suurendamine
            attemptCount++;

            // Kasutajalt sisendi kysimine
            String input = sc.nextLine();

            /* Kasutaja sisendi formaadi kontroll; regulaaravaldis kontrollib, kas rea alguses on yks number,
            siis sidekriips, seejarel taas yks number ning rea lopp
            */
            if (input.matches("^(\\d-\\d)$") == false){
                System.out.println("Vigane sisestus!");
                System.exit(1);
            }

            // Poolitab x-y stringi kaheks osaks
            String[] xy = input.split("-");

            // Konverteeritud (casting) stringid taisarvudeks (int)
            int x = Integer.parseInt(xy[0]) - 1;
            int y = Integer.parseInt(xy[1]) - 1;

            // Erindi kontroll juhuks, kui kasutaja sisend on suurem kui massiivi pikkus
            try {
                // Kysib x-y positsioonilt numbrit
                hit = playgroundAtFirst[y][x];
            } catch(ArrayIndexOutOfBoundsException e) {
                System.out.println("Vigane sisestus!");
                System.exit(1);
            }

            // Laev saab pihta, maaratakse pommitatuks
                if (hit == 1) {
                playgroundAtFirst[y][x] = 2;
                playgroundAtNow[y][x] = 2;
                System.out.println("Pihtas-pohjas!");
             // Laevale ei saa pihta
            } else if (hit == 0) {
                playgroundAtFirst[y][x] = 3;
                playgroundAtNow[y][x] = 3;
                System.out.println("Ei tabanud!");
            // Yhe ja sama koordinaadi korduv pommitamine
            } else if (hit == 2 || hit == 3) {
                System.out.println("Seda koordinaati oled juba pommitanud!");
            }

            // Kutsutakse valja meetod uuendatud mangulaua kuvamiseks
            PrintPlayground(playgroundAtNow);

            // Kutsub valja meetodi kontrollimaks, kas mang on loppenud (koik laevad pommitatud)
            boolean over = GameOver(playgroundAtFirst); //
            if  (over == true) {
                break;
            }
        }

        // Programmi too kestvuse arvutamine minutites ja sekundites (jagatise jaak - %)
        long runTime = (System.currentTimeMillis() - startTime) / 1000;
        int runTimeMin = (int)runTime/60;
        int runTimeSec = (int)runTime%60;

        // Loppkokkuvotte kuvamine kasutajale
        System.out.println("\n===VoITSID===");
        System.out.println("Heidetud pomme: " + attemptCount);
        System.out.println("Tabamusi: " + Math.round((double)shipCount*100/attemptCount) + "%");
        System.out.println("Mangu kestvus: " + runTimeMin + " minutit ja " + runTimeSec + " sekundit");
    }

    /*Meetod, mis kontrollib, kas manguvaljal (massiivis) on alles moni pommitamata laev
    True = koik pommitatud; mang voidetud. False = moni pommitamata; mang jatkub
    */
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

    // Meetod, mis kuvab mangukaardi
    public static void PrintPlayground(int[][] playground) {
        for (int i = 0; i < playground.length; i++) {
            System.out.println(Arrays.toString(playground[i]));
        }
    }
}