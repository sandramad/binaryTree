import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	Wezel korzen = new Wezel();

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

	private void skanujPlik(String plik) {
		try (Scanner scan = new Scanner(new File(plik))) {
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

	private String znajdzNajstarszy(Wezel wezel, String najstarszy, StringBuilder slowo) {
		slowo.append(wezel.wartosc);
		if (wezel.lewa == null && wezel.prawa == null) {
			String tmp = new StringBuilder(slowo).reverse().toString();
//        	System.out.print(tmp+", "); // wszystkie słowa
			najstarszy = (najstarszy.compareTo(tmp) > 0) ? najstarszy : tmp;
		} else {
			if (wezel.lewa != null)
				najstarszy = znajdzNajstarszy(wezel.lewa, najstarszy, slowo);
			if (wezel.prawa != null)
				najstarszy = znajdzNajstarszy(wezel.prawa, najstarszy, slowo);
		}
		slowo.deleteCharAt(slowo.length() - 1);
		return najstarszy;
	}

	public static void main(String[] args) {
		long millisActualTime = System.currentTimeMillis(); // początkowy czas w milisekundach.
		Main main = new Main();
		main.skanujPlik("drzewo.txt");
		System.out.println(main.znajdzNajstarszy(main.korzen, "", new StringBuilder()));
		long executionTime = System.currentTimeMillis() - millisActualTime; // czas wykonania programu w milisekundach.
		System.out.println("Main: "+executionTime + " ms");
	}

}