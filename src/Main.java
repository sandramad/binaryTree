import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
	static String korzen = "";
	static List<Instrukcje> Instrukcje = new ArrayList<>();
	static int i = 0;
	static String wartosc[] = new String[2];
	static char tmp = 'a';

	public static void main(String[] args) {
		Scanner scan = null;
		ArrayList<String> liniaPliku = new ArrayList<String>();

		try {
//			scan = new Scanner(new File("tree.txt")); 
			scan = new Scanner(new File("magda.txt")); // możesz sobie wybać plik
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (scan.hasNextLine())
			liniaPliku.add(scan.nextLine()); // skanujemy plik i linijki dodajemy do listy w celu dalszej obróbki

		for (i = 0; i < liniaPliku.size(); i++)
			if (liniaPliku.get(i).length() > 1) {
				wartosc = liniaPliku.get(i).split(" ");
				Instrukcje.add(new Instrukcje(wartosc[0].toLowerCase(), wartosc[1].toLowerCase())); // Aby nie zwracało
																									// uwagi na wielkość
																									// liter w pliku
			} else
				korzen = liniaPliku.get(i); // znajdujemy korzeń
		ZawartoscDrzewa<String> drzewo = new ZawartoscDrzewa<>(korzen);
		budujDrzewo(drzewo, Instrukcje); // budujemy drzewo
		String najstarszy[] = budujWyrazy(drzewo).toArray(new String[0]);
		for (int i = 0; i < najstarszy.length; i++) {
			int p = i;
			String element = najstarszy[i];
			for (int j = i + 1; j < najstarszy.length; j++) {
				if (element.compareTo(najstarszy[j]) > 0) {
					element = najstarszy[j];
					p = j;
				}
			}
			najstarszy[p] = najstarszy[i];
			najstarszy[i] = element;
		}
//      for(String s : najstarszy) System.out.println(s);
		System.out.println(najstarszy[najstarszy.length - 1]);
	}

	private static List<String> budujWyrazy(ZawartoscDrzewa<String> pozycjaStartowa) {
		List<String> slowa = new ArrayList<>();
		if (pozycjaStartowa.getLewa() != null)
			slowa.addAll(budujWyrazy(pozycjaStartowa.getLewa())); // rekurencyjnie budujemy wyrazy z lewej strony

		if (pozycjaStartowa.getPrawa() != null)
			slowa.addAll(budujWyrazy(pozycjaStartowa.getPrawa())); // rekurencyjnie budujemy wyrazy z prawej strony

		if (slowa.isEmpty())
			slowa.add(pozycjaStartowa.getWartosc());
		else
			slowa = slowa.stream().map(s -> s + pozycjaStartowa.getWartosc()).collect(Collectors.toList());
		return slowa;
	}

	private static void budujDrzewo(ZawartoscDrzewa<String> pozycja, List<Instrukcje> Instrukcje) { // rekurencyjnie
																									// budujemy drzewo
		List<Instrukcje> lewa = Instrukcje.stream().filter(i -> i.pozycja.startsWith("l")).collect(Collectors.toList());
		List<Instrukcje> prawa = Instrukcje.stream().filter(i -> i.pozycja.startsWith("r"))
				.collect(Collectors.toList()); // odczytujemy instrukcje...

		Instrukcje lewoStart = lewa.stream().filter(i -> i.pozycja.length() == 1).findFirst().orElse(null);
		Instrukcje prawoStart = prawa.stream().filter(i -> i.pozycja.length() == 1).findFirst().orElse(null);

		if (lewoStart != null) {
			pozycja.setLewa(new ZawartoscDrzewa<String>(lewoStart.wartosc));
			List<Instrukcje> zLewej = lewa.stream().map(i -> new Instrukcje(i.pozycja.substring(1), i.wartosc))
					.collect(Collectors.toList());
			budujDrzewo(pozycja.getLewa(), zLewej);
		}

		if (prawoStart != null) {
			pozycja.setPrawa(new ZawartoscDrzewa<String>(prawoStart.wartosc));
			List<Instrukcje> zPrawej = prawa.stream().map(i -> new Instrukcje(i.pozycja.substring(1), i.wartosc))
					.collect(Collectors.toList());
			budujDrzewo(pozycja.getPrawa(), zPrawej);

		}

	}
}
