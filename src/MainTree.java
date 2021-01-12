import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class MainTree {

	Wezel korzen = new Wezel();

	private void budujDrzewo(Wezel wezel, String sciezka, Character wartosc) {
		if (sciezka.length() == 0) {
			wezel.wartosc = wartosc;
		} else if (sciezka.charAt(0) == 'l') {
			if (wezel.lewa == null)
				wezel.lewa = new Wezel();
			budujDrzewo(wezel.lewa, sciezka.substring(1), wartosc);
		} else {
			if (wezel.prawa == null)
				wezel.prawa = new Wezel();
			budujDrzewo(wezel.prawa, sciezka.substring(1), wartosc);
		}
	}

	private void budujWyrazy(Wezel wezel, Set<String> slowa, StringBuilder slowo) {
		slowo.append(wezel.wartosc);
		if (wezel.lewa == null && wezel.prawa == null) {
			slowa.add(new StringBuilder(slowo).reverse().toString());
		} else {
			if (wezel.lewa != null)
				budujWyrazy(wezel.lewa, slowa, slowo);
			if (wezel.prawa != null)
				budujWyrazy(wezel.prawa, slowa, slowo);
		}
		slowo.deleteCharAt(slowo.length() - 1);
	}

	private void skanujPlik() {
		try (Scanner scan = new Scanner(new File("drzewo.txt"))) {
			while (scan.hasNextLine()) {
				String line = scan.nextLine().toLowerCase();
				Character wartosc = line.charAt(line.length() - 1);
				String sciezka = "";
				if (line.length() > 2)
					sciezka = line.substring(0, line.length() - 2);
				budujDrzewo(korzen, sciezka, wartosc);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void znajdzNajstarszy() {
		Set<String> slowa = new TreeSet<String>();
		budujWyrazy(korzen, slowa, new StringBuilder());
        System.out.println(slowa);
		System.out.println(slowa.toArray()[slowa.size() - 1]);
	}

	public static void main(String[] args) {
		long millisActualTime = System.currentTimeMillis(); // początkowy czas w milisekundach.
		MainTree main = new MainTree();
		main.skanujPlik();
		main.znajdzNajstarszy();
		long executionTime = System.currentTimeMillis() - millisActualTime; // czas wykonania programu w milisekundach.
		System.out.println("Tree: "+executionTime + " ms");
	}

}