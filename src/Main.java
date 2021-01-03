import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Main {

	private class Wezel {
		public Character wartosc;
		public Wezel lewa;
		public Wezel prawa;
	}

	Wezel root = new Wezel();

	public static void main(String[] args) {
		Main main = new Main();
		main.skanujPlik();
		main.znajdzNajstarszy();
	}

	private void skanujPlik() {
		try (Scanner scan = new Scanner(new File("drzewo.txt"))) {
			while (scan.hasNextLine()) {
				String wiersz = scan.nextLine().toLowerCase();
				Character wartosc = wiersz.charAt(wiersz.length() - 1);
				String sciezka = "";
				if (wiersz.length() > 2)
					sciezka = wiersz.substring(0, wiersz.length() - 2);
				budujDrzewo(root, sciezka, wartosc);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void budujDrzewo(Wezel wezel, String sciezka, Character wartosc) {
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

	private void znajdzNajstarszy() {
		Set<String> slowa = new TreeSet<String>();
		budujWyrazy(root, slowa, new StringBuilder());
//		System.out.println(slowa); // lista wszystkich wyrazów
		System.out.println(slowa.toArray()[slowa.size() - 1]); // zwrot ostatniego wyrazu
	}

	private void budujWyrazy(Wezel wezel, Set<String> slowa, StringBuilder slowo) {
		slowo.append(wezel.wartosc);
		if (wezel.lewa == null && wezel.prawa == null)
			slowa.add(new StringBuilder(slowo).reverse().toString());
		else {
			if (wezel.lewa != null)
				budujWyrazy(wezel.lewa, slowa, slowo);
			if (wezel.prawa != null)
				budujWyrazy(wezel.prawa, slowa, slowo);
		}
		slowo.deleteCharAt(slowo.length() - 1);
	}
}