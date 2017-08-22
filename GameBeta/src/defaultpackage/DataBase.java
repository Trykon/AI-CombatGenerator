package defaultpackage;

import java.util.HashMap;

public class DataBase {

	public Monster Player = new Monster("Player");
	

	public HashMap<String, Monster> getMonsters() {
		return Monsters;
	}


	public static HashMap <String, Monster> Monsters = new HashMap<String, Monster>();
	Monster Dragon = new Monster("Dragon");
	Monster Warrior = new Monster("Warrior");
	Monster Mage = new Monster("Mage");
	Monster Archer = new Monster("Archer");
	Monster Goblin = new Monster("Goblin");
	public DataBase() {
		setMonsters();
	}

	private static void setMonsters(){
		Monsters.put("Dragon", new Monster("Dragon"));
		Monsters.put("Warrior", new Monster("Warrior"));
		Monsters.put("Mage", new Monster("Mage"));
		Monsters.put("Archer", new Monster("Archer"));
		Monsters.put("Goblin", new Monster("Goblin"));
		
		Monsters.get("Dragon").SetAll(20, 3, 7, 5, 100, -2, "physical", "fire", 3);
		Monsters.get("Warrior").SetAll(15, 4, 7, 5, 0, 0, "physical", null, 1);
		Monsters.get("Mage").SetAll(10, 3, 0, 4, 4, 4, "fire", "ice", 3);
		Monsters.get("Archer").SetAll(12, 3, 3, 3, 0, 0, "physical", null, 4);
		Monsters.get("Goblin").SetAll(5, 2, 1, 2, 1, 0, "physical", null, 2);
	}
}
