import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

class Wezel {
	public Character wartosc;
	public Wezel lewa;
	public Wezel prawa;
}

public class Main {

	Wezel korzen = new Wezel();
	String tmp = "a";

	private void budujDrzewo(Wezel wezel, String sciezka, char wartosc) {
		if (sciezka.length() == 0)
			wezel.wartosc = wartosc;
		else if (sciezka.charAt(0) == 'l') {
			if (wezel.lewa == null)
				wezel.lewa = new Wezel();
			budujDrzewo(wezel.lewa, sciezka.substring(1), wartosc);
		} else {
			if (wezel.prawa == null)
				wezel.prawa = new Wezel();
			budujDrzewo(wezel.prawa, sciezka.substring(1), wartosc);
		}
	}

	private void skanujPlik() {
		try (Scanner scan = new Scanner(new File("tree.txt"))) {
			while (scan.hasNextLine()) {
				String wiersz = scan.nextLine().toLowerCase();
				Character wartosc = wiersz.charAt(wiersz.length() - 1);
				String sciezka = "";
				if (wiersz.length() > 2)
					sciezka = wiersz.substring(0, wiersz.length() - 2);
				budujDrzewo(korzen, sciezka, wartosc);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private String znajdzNajstarszy(Wezel wezel, String najstarszy, StringBuilder word) {
        word.append(wezel.wartosc);
        if (wezel.lewa == null && wezel.prawa == null) {
            String reversed = new StringBuilder(word).reverse().toString();
            najstarszy = (najstarszy.compareTo(reversed) >= 0) ? najstarszy : reversed;
        } else {
            if (wezel.lewa != null) najstarszy = znajdzNajstarszy(wezel.lewa, najstarszy, word);
            if (wezel.prawa != null) najstarszy = znajdzNajstarszy(wezel.prawa, najstarszy, word);
        }
        word.deleteCharAt(word.length()-1);
        return najstarszy;
    }

	public static void main(String[] args) {
		Main main = new Main();
		main.skanujPlik();
        System.out.println(main.znajdzNajstarszy(main.korzen, "", new StringBuilder()));
	}

}