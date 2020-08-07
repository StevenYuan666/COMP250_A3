import java.util.*;

public class TestIterator {
	public static void main(String[] args) {
		CatInfo a = new CatInfo("A", 100, 40, 243, 40);
		CatInfo b = new CatInfo("B", 50, 60, 240, 30);
		CatInfo c = new CatInfo("C", 125, 75, 248, 10);
		CatInfo d = new CatInfo("D", 100, 15, 245, 50);
		CatInfo e = new CatInfo("E", 40, 25, 245, 20);
		CatInfo f = new CatInfo("F", 50, 35, 247, 15);
		CatInfo g = new CatInfo("G", 75, 40, 247, 27);
		CatInfo h = new CatInfo("H", 100, 50, 247, 27);
		CatInfo i = new CatInfo("I", 110, 65, 247, 100);
		CatInfo j = new CatInfo("J", 125, 75, 247, 100);
		CatInfo k = new CatInfo("K", 130, 15, 247, 100);
					
		CatTree t = new CatTree(a);
		t.addCat(b);
		t.addCat(c);
		t.addCat(d);
		t.addCat(e);
		t.addCat(f);
		t.addCat(g);
		t.addCat(h);
		t.addCat(i);
		t.addCat(j);
		t.addCat(k);
	}
}
