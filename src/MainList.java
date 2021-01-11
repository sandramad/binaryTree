import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MainList {

	Wezel korzen = new Wezel();
	String tmp = "a";

	private void budujDrzewo(Wezel wezel, String sciezka, String wartosc) {
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

	private List<String> budujWyrazy(Wezel wezel) {
		List<String> slowa = new ArrayList<>();
		String najstarsze = "";
		if (wezel.lewa != null)
			slowa.addAll(budujWyrazy(wezel.lewa)); // rekurencyjnie budujemy wyrazy z lewej strony

		if (wezel.prawa != null)
			slowa.addAll(budujWyrazy(wezel.prawa)); // rekurencyjnie budujemy wyrazy z prawej strony

		if (slowa.isEmpty()) {
			najstarsze = wezel.wartosc;
				tmp = najstarsze;
				slowa.add(wezel.wartosc); // buduje słowa tylko z najwyżej znalezionych wartości
		} else
			slowa = slowa.stream().map(s -> s + wezel.wartosc).collect(Collectors.toList());
		return slowa;
	}

	public static void main(String[] args) {
		MainList main = new MainList();
		main.skanujPlik("tree.txt");
        ArrayList<String> najstarszy = new ArrayList<String>();
		najstarszy.addAll(main.budujWyrazy(main.korzen));
		Collections.sort(najstarszy);
		System.out.println(najstarszy.get(najstarszy.size()-1));
	}

}