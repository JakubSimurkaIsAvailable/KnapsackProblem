
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Hlavná trieda, v ktorej sa načítava zo zadaných súborov, prebieha v nej algoritmus a zapisujú sa údaje do výstupného súboru.
 * @author simur
 */
public final class Heur
 {
    int[] xVychadzajuce = new int[500];
    int[] xC = new int[500];
    int cenaBatohu = 0;
    int previsBatohu;
    int pocetPredmetov;
    int pocetPredmetovVBatohu;
    int[][] predmety;
    int prvotnyPrevis;
    int pozadovanyPocet;
    public Heur(int previsBatohu, int pocetPredmetov, int pocetPredmetovVBatohu) 
    {
        this.previsBatohu = previsBatohu;
        this.prvotnyPrevis = this.previsBatohu;
        this.pocetPredmetov = pocetPredmetov;
        this.pocetPredmetovVBatohu = pocetPredmetovVBatohu;
        this.pozadovanyPocet = this.pocetPredmetovVBatohu;
        predmety = new int[pocetPredmetov][2];
        this.loadItems("H2_a.txt", "H2_c.txt");
        
        this.MainLoop();
        for(int i = 0; i < pocetPredmetov; i++) {
            System.out.println(predmety[i][0] + " " + predmety[i][1] + " " + xC[i]);
        }
        int hmotnost = this.prvotnyPrevis - this.previsBatohu;
        int pocPr = this.pozadovanyPocet - this.pocetPredmetovVBatohu;
        System.out.println(hmotnost + " " + pocPr + " " + cenaBatohu);
        vypisDoSuboru();
    }

    /**
     * V metóde main loop sa kontroluje či je previs batohu väčší ako nula (v batohu nie je požadovaná hmotnosť alebo viac)
     * a či je počet predmetov v batohu rovný alebo viac ako požadovaný počet.
     * Ak tomu tak nieje, algoritmus nájde predmet s najmenším ziskom a vloží ho do batohu. Toto sa opakuje až pokiaľ sa batoh nenaplní do požadovanej hmotnosti
     * s požadovaným počtom predmetov.
     */
    public void MainLoop() 
    {
        while (this.previsBatohu > 0 || this.pocetPredmetovVBatohu > 0) {
            int zisk = 99999;
            int indexPredmetu = 0;
            for (int i = 0; i < this.pocetPredmetov; i++) {
                if(this.predmety[i][1] < zisk && xC[i] == 0) {
                    zisk = this.predmety[i][1];
                    indexPredmetu = i;
                }
            }
            xC[indexPredmetu] = 1;
            this.previsBatohu -= this.predmety[indexPredmetu][0];
            this.cenaBatohu += this.predmety[indexPredmetu][1];
            this.pocetPredmetovVBatohu--;
        }
    }


    /**
     * Metóda loadItems nastaví váhu a cenu všetkých predmetov.
     * Nastaví vychádzajúce X na neprípustné riešenie (v batohu sa nenachádza žiadny predmet) 
     * @param itemsSource
     * @param itemsPrice
     */
    public void loadItems(String itemsSource, String itemsPrice)
    {
        
        File itemsSourceFile = new File(itemsSource);
        File itemsPriceFile = new File(itemsPrice);
        try {
            Scanner sc = new Scanner(itemsSourceFile);
            int index = 0;
            while(sc.hasNextLine()) {
                this.predmety[index][0] = Integer.parseInt(sc.nextLine());
                index++;
            }
        } catch (FileNotFoundException | NumberFormatException e) {
            System.out.println("Source file not found");
        }
        try {
            Scanner sc = new Scanner(itemsPriceFile);
            int index = 0;
            while (sc.hasNextLine()) {
                this.predmety[index][1] = Integer.parseInt(sc.nextLine());
                index++;
            }
        } catch (FileNotFoundException | NumberFormatException e) {
        }
        for(int i = 0; i < this.pocetPredmetov; i++) {
            this.xVychadzajuce[i] = 0;
            this.xC[i] = this.xVychadzajuce[i];
        }
        
        
    }

    /**
     * Vypysuje výsledok do súboru result.txt
     */
    public void vypisDoSuboru() {
        try {
            File writeFile = new File("result.txt");
            FileWriter fw = new FileWriter("result.txt");
            int hmotnost = this.prvotnyPrevis - this.previsBatohu;
            int pocPr = this.pozadovanyPocet - this.pocetPredmetovVBatohu;
            fw.write("Hmotnosť predmetov v batohu: " + hmotnost + "\nPočet predmetov v batohu: " + pocPr+ "\nCena predmetov v batohu: " + this.cenaBatohu + "\n");
            for (int i = 0; i < this.pocetPredmetov; i++) {
                fw.write(this.predmety[i][0] + " " + this.predmety[i][1] + " " + this.xC[i] + "\n");
            }
            fw.close();
        } catch (Exception e) {
        }
    }
    
}
