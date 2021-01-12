import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class W {
	public String wartosc;
	public W lewa;
	public W prawa;
}

public class MainList {

	W korzen = new W();

	private void budujDrzewo(W wezel, String sciezka, String wartosc) {
		if (sciezka.length() == 0)
			wezel.wartosc = wartosc;
		else if (sciezka.charAt(0) == 'l') {
			if (wezel.lewa == null)
				wezel.lewa = new W();
			budujDrzewo(wezel.lewa, sciezka.substring(1), wartosc);
		} else {
			if (wezel.prawa == null)
				wezel.prawa = new W();
			budujDrzewo(wezel.prawa, sciezka.substring(1), wartosc);
		}
	}

	private void skanujPlik() {
		try (Scanner scan = new Scanner(new File("drzewo.txt"))) {
			while (scan.hasNextLine()) {
				String wiersz = scan.nextLine().toLowerCase();
				String wartosc = wiersz.substring(wiersz.length() - 1);
				String sciezka = "";
				if (wiersz.length() > 2)
					sciezka = wiersz.substring(0, wiersz.length() - 2);
				budujDrzewo(korzen, sciezka, wartosc);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private List<String> budujWyrazy(W wezel) {
		List<String> slowa = new ArrayList<>();
		if (wezel.lewa != null)
			slowa.addAll(budujWyrazy(wezel.lewa)); // rekurencyjnie budujemy wyrazy z lewej strony

		if (wezel.prawa != null)
			slowa.addAll(budujWyrazy(wezel.prawa)); // rekurencyjnie budujemy wyrazy z prawej strony

		if (slowa.isEmpty())
			slowa.add(wezel.wartosc);
		else
			slowa = slowa.stream().map(s -> s + wezel.wartosc).collect(Collectors.toList());
		return slowa;
	}

	public static void main(String[] args) {
		long millisActualTime = System.currentTimeMillis(); // początkowy czas w milisekundach.
		MainList main = new MainList();
		main.skanujPlik();
		ArrayList<String> najstarszy = new ArrayList<String>(main.budujWyrazy(main.korzen));
		Collections.sort(najstarszy);
//		System.out.println(najstarszy); // wszystkie wyrazy
		System.out.println(najstarszy.get(najstarszy.size() - 1));
		long executionTime = System.currentTimeMillis() - millisActualTime; // czas wykonania programu w milisekundach.
		System.out.println(executionTime + " ms");
	}

}