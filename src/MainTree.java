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

	private void budujWyrazy(Wezel wezel, Set<String> words, StringBuilder word) {
		word.append(wezel.wartosc);
		if (wezel.lewa == null && wezel.prawa == null) {
			words.add(new StringBuilder(word).reverse().toString());
		} else {
			if (wezel.lewa != null)
				budujWyrazy(wezel.lewa, words, word);
			if (wezel.prawa != null)
				budujWyrazy(wezel.prawa, words, word);
		}
		word.deleteCharAt(word.length() - 1);
	}

	private void skanujPlik() {
		try (Scanner scan = new Scanner(new File("tree.txt"))) {
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
		Set<String> words = new TreeSet<String>();
		budujWyrazy(korzen, words, new StringBuilder());
//        System.out.println(words);
		System.out.println(words.toArray()[words.size() - 1]);
	}

	public static void main(String[] args) {
		MainTree main = new MainTree();
		main.skanujPlik();
		main.znajdzNajstarszy();
	}

}