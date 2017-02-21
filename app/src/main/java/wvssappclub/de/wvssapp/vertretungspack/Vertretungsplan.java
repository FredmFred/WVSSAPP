package wvssappclub.de.wvssapp.vertretungspack; /**
 * Created by Fred on 15.02.2017.
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
//import java.time.Year;
import java.util.ArrayList;

public class Vertretungsplan {
    private File lokal;
    private URL vertretungsplan;

    public Vertretungsplan() throws IOException{
        this.vertretungsplan = new URL("http://www.siemensschule-wetzlar.de/fileadmin/wvss/website/media/Aktuelle_Vertretungs_und_Blockplaene/vertretung.pdf");
        this.lokal = new File(System.getProperty("java.io.tmpdir") + "/Vertretung.pdf");

        this.downloadVertretungsplan();
    }

    private void downloadVertretungsplan() throws IOException{
        ReadableByteChannel rbc = Channels.newChannel(this.vertretungsplan.openStream());
        FileOutputStream fos = new FileOutputStream(this.lokal);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }

    public ArrayList<Vertretung> getVertretungen(){
        ArrayList<Vertretung> vertretungen = new ArrayList<Vertretung>();
        String[] zeilen = this.parsePdfToString().split("\n");
        for(int i = 0; i<zeilen.length; i++){
            //Zeilen rausfiltern, die wirklich eine Vertretung sind -> REGEX, Bitch!
            String zeile = zeilen[i];
            if(zeile.matches("\\d\\d\\.\\d\\d\\. .*")){
                //System.out.println(zeile);
                vertretungen.add(this.parseStringToVertretung(zeile));
            }
        }
        return vertretungen;
    }

    private String parsePdfToString(){
        PdfParser p = new PdfParser(this.lokal );
        System.out.println(p.toString());
        return p.getParsedText();
    }

    private Vertretung parseStringToVertretung(String v){
        Vertretung vrt = new Vertretung();
        int indexAkt = 0;
        if(v.substring(0,20).contains(",")){
            //Zwei Klassen, pain in the ass
            indexAkt = v.indexOf(" ", v.indexOf(", ", 7)+2)+1;
            vrt.setKlasse(v.substring(7,indexAkt));
            //System.out.println("Klasse: " + vrt.getKlasse());
        } else {
            indexAkt = v.indexOf(" ", 7)+1;
            vrt.setKlasse(v.substring(7,indexAkt));
            //System.out.println("Klasse: " + vrt.getKlasse());
        }
        String datum = v.substring(0, 5);
        String[] teile = datum.split("\\.");
        //vrt.setTag(new GregorianCalendar(Year.now().getValue(), Integer.parseInt(teile[1])-1, Integer.parseInt(teile[0])));
        //vrt.setTag(v.substring(indexAkt, indexAkt+2) + ", " + v.substring(0, 6));
        //System.out.println("Datum: " + vrt.getTag());
        indexAkt += 3;
        if(v.substring(10, 30).contains(" - ")){
            vrt.setStunde(v.substring(indexAkt, v.indexOf(" ", indexAkt+4)));
            indexAkt += 6;
        } else {
            vrt.setStunde(v.substring(indexAkt, indexAkt+1));
            indexAkt += 3;
        }
        //System.out.println("Stunde: " + vrt.getStunde());
        String lehrer = v.substring(indexAkt, v.indexOf(" ", indexAkt+1));
        if(!lehrer.equals("---")){
            vrt.setLehrer(lehrer);
        }
        //System.out.println("Lehrer: " + vrt.getLehrer());
        indexAkt = v.indexOf(" ", indexAkt+1)+1;
        String vrtLehrer = v.substring(indexAkt, v.indexOf(" ", indexAkt+1));
        if(!vrtLehrer.equals("---")){
            vrt.setVertretungsLehrer(vrtLehrer);
        }
        //System.out.println("Vertretung: " + vrt.getVertretungsLehrer());
        indexAkt = v.indexOf(" ", indexAkt+1)+1;
        if (v.indexOf(" ", indexAkt+1) > 0){
            if(v.substring(indexAkt, v.indexOf(" ", indexAkt+1)).equals("Trotz")){
                int indexZws = v.indexOf(" ", indexAkt+1);
                vrt.setArt(v.substring(indexAkt, v.indexOf(" ", indexZws+1)));
                indexAkt = v.indexOf(" ", indexZws+1)+1;
            } else {
                vrt.setArt(v.substring(indexAkt, v.indexOf(" ", indexAkt+1)));
                indexAkt = v.indexOf(" ", indexAkt+1)+1;
            }
            //System.out.println("Art: " + vrt.getArt());
            if (v.indexOf(" ", indexAkt+1)>0){
                vrt.setFach(v.substring(indexAkt, v.indexOf(" ", indexAkt+1)));
                //System.out.println("Fach: " + vrt.getFach());
                indexAkt = v.indexOf(" ", indexAkt+1)+1;
                //System.out.println("IndexAkt: " + v.indexOf(" ", indexAkt+1) + " LÃ¤ngeGes: " + v.length());
                String raum = null;
                if(!(v.indexOf(" ", indexAkt+1) == -1)){
                    vrt.setText(v.substring(v.indexOf(" ", indexAkt+1)));
                    raum = v.substring(indexAkt, v.indexOf(" ", indexAkt+1));
                } else {
                    raum = v.substring(indexAkt);
                }
                if(!raum.equals("---")){
                    vrt.setRaum(raum);
                }
            }
        }
        return vrt;
    }
}
