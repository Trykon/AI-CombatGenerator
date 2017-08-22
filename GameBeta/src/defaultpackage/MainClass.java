package defaultpackage;

import java.util.Scanner;

public class MainClass {
	static DataBase data = new DataBase();
	static int[][][] result = new int[3][2][5];
	static int[][][] amount = new int[3][2][5];
	static SetUp[][][] setUps = new SetUp[3][2][5];
	static int winInt = 0;
	static int amountInt = 0;
	static int genesArray[][] = new int[10][5];

	public static void setSetUps() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 2; j++)
				for (int k = 0; k < 5; k++) {
					String dist;
					if (j == 0)
						dist = "follow";
					else
						dist = "keep";
					String mon;
					if (k == 0)
						mon = "Dragon";
					else if (k == 1)
						mon = "Warrior";
					else if (k == 2)
						mon = "Mage";
					else if (k == 3)
						mon = "Archer";
					else
						mon = "Goblin";
					SetUp temp = new SetUp();
					temp.setAll(i + 1, dist, mon);
					setUps[i][j][k] = temp;
				}
	}

	public static int k10() {
		return (int) Math.ceil(Math.random() * 10);
	}

	public static String intTypeToString(int n) {
		if (n == 1)
			return "Warrior";
		if (n == 2)
			return "Mage";
		if (n == 3)
			return "Archer";
		return "";
	}

	public static int k(int n) {
		return (int) Math.ceil(Math.random() * n);
	}

	public static void print(String a) {
		System.out.println(a);
	}

	public static Monster attack(Monster attacker, Monster defender) {
		if (attacker.getSecondary() == null) {
			int res = 0;
			if (attacker.getPrimary().equals("physical"))
				res = defender.getArmor();
			else if (attacker.getPrimary().equals("ice"))
				res = defender.getIceRes();
			else
				res = defender.getFireRes();
			int damage = Math.max(0, (k10() + data.Player.getDamage()) - res);
			defender.setHP(defender.getHP() - damage);
		} else {
			int res1 = 0;
			int res2 = 0;
			if (attacker.getPrimary().equals("physical"))
				res1 = defender.getArmor();
			else if (attacker.getPrimary().equals("ice"))
				res1 = defender.getIceRes();
			else
				res1 = defender.getFireRes();
			if (attacker.getSecondary().equals("physical"))
				res2 = defender.getArmor();
			else if (attacker.getSecondary().equals("ice"))
				res2 = defender.getIceRes();
			else
				res2 = defender.getFireRes();
			int res = Math.min(res1, res2);
			int damage = Math.max(0, (k10() + data.Player.getDamage()) - res);
			defender.setHP(defender.getHP() - damage);
		}
		return defender;
	}

	public static boolean simulateFight(SetUp s) {
		if (s.type == 1)
			data.Player.SetAll(15, 4, 7, 5, 0, 0, "physical", null, 1);
		else if (s.type == 2)
			data.Player.SetAll(10, 3, 0, 4, 4, 4, "fire", "ice", 3);
		else
			data.Player.SetAll(12, 3, 2, 3, 0, 0, "physical", null, 4);
		int xDist = (int) Math.ceil(Math.random() * 5 + 5);
		int yDist = (int) Math.ceil(Math.random() * 5 + 5);
		Monster monster = new Monster(s.monster);
		monster.SetAll(data.getMonsters().get(s.monster).getHP(), data.getMonsters().get(s.monster).getSpeed(),
				data.getMonsters().get(s.monster).getArmor(), data.getMonsters().get(s.monster).getDamage(),
				data.getMonsters().get(s.monster).getFireRes(), data.getMonsters().get(s.monster).getIceRes(),
				data.getMonsters().get(s.monster).getPrimary(), data.getMonsters().get(s.monster).getSecondary(),
				data.getMonsters().get(s.monster).getDistance());
		int mMoves = monster.getSpeed();
		int pMoves = data.Player.getSpeed();
		boolean pAttack = true;
		boolean mAttack = true;
		for (int i = 0; i < 100; i++) {
			if (pMoves == 0 && mMoves == 0) {
				pAttack = true;
				mAttack = true;
				pMoves = data.Player.getSpeed();
				mMoves = monster.getSpeed();
			}
			if (i % 2 == 0 && pMoves > 0) {
				pMoves--;
				if (xDist > data.Player.getDistance() || yDist > data.Player.getDistance()) {
					if (xDist > yDist)
						xDist--;
					else
						yDist--;
				} else if (pAttack) {
					pAttack = false;
					monster = attack(data.Player, monster);
				} else if (s.keepDistance.equals("keep")) {
					if (xDist > yDist)
						yDist++;
					else
						xDist++;
				}
			} else if (i % 2 == 1 && mMoves > 0) {
				mMoves--;
				if (xDist > monster.getDistance() || yDist > monster.getDistance()) {
					if (xDist > yDist)
						xDist--;
					else
						yDist--;
				} else if (mAttack) {
					mAttack = false;
					data.Player = attack(monster, data.Player);
				}

			}
			if (data.Player.getHP() <= 0)
				return false;
			else if (monster.getHP() <= 0)
				return true;
		}
		return false;
	}

	public static void statsBasedSimulation() {
		setSetUps();
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 2; j++)
				for (int k = 0; k < 5; k++) {
					for (int t = 0; t < 10; t++) {
						if (simulateFight(setUps[i][j][k])) {
							result[i][j][k]++;
							winInt++;
						}
						amount[i][j][k]++;
						amountInt++;
					}
				}
		print("After initial ten in each combination: " + (double) winInt * 100 / amountInt + "%");
		print("");
		for (int i = 0; i < 5000000; i++) {
			float points = 0;
			for (int j = 0; j < 3; j++)
				for (int k = 0; k < 2; k++) {
					points += ((result[j][k][i % 5] * 100) / amount[j][k][i % 5]) + 10;
				}
			float rand = (float) (Math.random() * points);
			boolean done = false;
			for (int j = 0; j < 3; j++)
				for (int k = 0; k < 2; k++) {
					rand -= (((result[j][k][i % 5] * 100) / amount[j][k][i % 5]) + 10);
					if (rand <= 0 && !done) {
						if (simulateFight(setUps[j][k][i % 5])) {
							result[j][k][i % 5]++;
							winInt++;
						}
						amount[j][k][i % 5]++;
						done = true;
						amountInt++;
					}
				}
			if (amountInt % 1000 == 0) {
				print("After " + amountInt + " fights: " + (double) winInt * 100 / amountInt + "%");
			}
		}
		for (int i = 0; i < 5; i++) {
			int bestType = 0;
			int bestStance = 0;
			float bestRatio = 0;
			for (int j = 0; j < 2; j++)
				for (int k = 0; k < 3; k++) {
					if (result[k][j][i] * 100 / amount[k][j][i] > bestRatio) {
						bestRatio = result[k][j][i] * 100 / amount[k][j][i];
						bestType = k;
						bestStance = j;
					}
				}
			print(intTypeToString(setUps[bestType][bestStance][i].type) + " "
					+ setUps[bestType][bestStance][i].keepDistance + ": " + result[bestType][bestStance][i] + "/"
					+ amount[bestType][bestStance][i] + " " + setUps[bestType][bestStance][i].monster);
		}
		print("Total:" + amountInt + ". Percentage: " + (double) winInt * 100 / amountInt + "%");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				for (int k = 0; k < 5; k++) {
					result[i][j][k] = 0;
					amount[i][j][k] = 0;
				}
			}
		}
	}

	public static void statsBasedSimulationWithMutations() {
		setSetUps();
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 2; j++)
				for (int k = 0; k < 5; k++) {
					for (int t = 0; t < 10; t++) {
						if (simulateFight(setUps[i][j][k])) {
							result[i][j][k]++;
							winInt++;
						}
						amount[i][j][k]++;
						amountInt++;
					}
				}
		print("After initial ten in each combination: " + (double) winInt * 100 / amountInt + "%");
		print("");
		for (int i = 0; i < 5000000; i++) {
			float points = 0;
			for (int j = 0; j < 3; j++)
				for (int k = 0; k < 2; k++) {
					points += ((result[j][k][i % 5] * 100) / amount[j][k][i % 5]) + 10;
				}
			float rand = (float) (Math.random() * points);
			boolean done = false;
			for (int j = 0; j < 3; j++)
				for (int k = 0; k < 2; k++) {
					rand -= (((result[j][k][i % 5] * 100) / amount[j][k][i % 5]) + 10);
					if (rand <= 0 && !done) {
						if (simulateFight(setUps[j][k][i % 5])) {
							result[j][k][i % 5]++;
							winInt++;
						}
						chanceForMutation(setUps[j][k][i % 5].monster);
						amount[j][k][i % 5]++;
						done = true;
						amountInt++;
					}
				}
			if (amountInt % 1000 == 0) {
				print("After " + amountInt + " fights: " + (double) winInt * 100 / amountInt + "%");
			}
			if (amountInt % 10000 == 0) {
				mutateAll();
			}
		}
		for (int i = 0; i < 5; i++) {
			int bestType = 0;
			int bestStance = 0;
			float bestRatio = 0;
			for (int j = 0; j < 2; j++)
				for (int k = 0; k < 3; k++) {
					if (result[k][j][i] * 100 / amount[k][j][i] > bestRatio) {
						bestRatio = result[k][j][i] * 100 / amount[k][j][i];
						bestType = k;
						bestStance = j;
					}
				}
			print(intTypeToString(setUps[bestType][bestStance][i].type) + " "
					+ setUps[bestType][bestStance][i].keepDistance + ": " + result[bestType][bestStance][i] + "/"
					+ amount[bestType][bestStance][i] + " " + setUps[bestType][bestStance][i].monster);
		}
		print("Total:" + amountInt + ". Percentage: " + (double) winInt * 100 / amountInt + "%");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				for (int k = 0; k < 5; k++) {
					result[i][j][k] = 0;
					amount[i][j][k] = 0;
				}
			}
		}
	}
	
	public static void pickStrongestSimulation() {
		setSetUps();
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 2; j++)
				for (int k = 0; k < 5; k++) {
					for (int t = 0; t < 10; t++) {
						if (simulateFight(setUps[i][j][k])) {
							result[i][j][k]++;
							winInt++;
						}
						amount[i][j][k]++;
						amountInt++;
					}
				}
		print("After initial ten in each combination: " + (double) winInt * 100 / amountInt + "%");
		print("");
		for (int i = 0; i < 5000000; i++) {
			float strength = 0;
			int jLoc=0;
			int kLoc=0;
			for (int j = 0; j < 3; j++)
				for (int k = 0; k < 2; k++) {
					if(strength<(result[j][k][i % 5] * 100) / amount[j][k][i % 5])
					{
						strength = (result[j][k][i % 5] * 100) / amount[j][k][i % 5];
						jLoc = j;
						kLoc = k;
					}
				}
			if (simulateFight(setUps[jLoc][kLoc][i % 5])) {
				result[jLoc][kLoc][i % 5]++;
				winInt++;
			}
				amount[jLoc][kLoc][i % 5]++;
				amountInt++;
			if (amountInt % 1000 == 0) {
				print("After " + amountInt + " fights: " + (double) winInt * 100 / amountInt + "%");
			}
		}
		for (int i = 0; i < 5; i++) {
			int bestType = 0;
			int bestStance = 0;
			float bestRatio = 0;
			for (int j = 0; j < 2; j++)
				for (int k = 0; k < 3; k++) {
					if (result[k][j][i] * 100 / amount[k][j][i] > bestRatio) {
						bestRatio = result[k][j][i] * 100 / amount[k][j][i];
						bestType = k;
						bestStance = j;
					}
				}
			print(intTypeToString(setUps[bestType][bestStance][i].type) + " "
					+ setUps[bestType][bestStance][i].keepDistance + ": " + result[bestType][bestStance][i] + "/"
					+ amount[bestType][bestStance][i] + " " + setUps[bestType][bestStance][i].monster);
		}
		print("Total:" + amountInt + ". Percentage: " + (double) winInt * 100 / amountInt + "%");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				for (int k = 0; k < 5; k++) {
					result[i][j][k] = 0;
					amount[i][j][k] = 0;
				}
			}
		}
	}
	
	public static void pickStrongestSimulationWithMutations() {
		setSetUps();
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 2; j++)
				for (int k = 0; k < 5; k++) {
					for (int t = 0; t < 10; t++) {
						if (simulateFight(setUps[i][j][k])) {
							result[i][j][k]++;
							winInt++;
						}
						amount[i][j][k]++;
						amountInt++;
					}
				}
		print("After initial ten in each combination: " + (double) winInt * 100 / amountInt + "%");
		print("");
		for (int i = 0; i < 5000000; i++) {
			float strength = 0;
			int jLoc=0;
			int kLoc=0;
			for (int j = 0; j < 3; j++)
				for (int k = 0; k < 2; k++) {
					if(strength<(result[j][k][i % 5] * 100) / amount[j][k][i % 5])
					{
						strength = (result[j][k][i % 5] * 100) / amount[j][k][i % 5];
						jLoc = j;
						kLoc = k;
					}
				}
			if (simulateFight(setUps[jLoc][kLoc][i % 5])) {
				result[jLoc][kLoc][i % 5]++;
				winInt++;
			}
				chanceForMutation(setUps[jLoc][kLoc][i % 5].monster);
				amount[jLoc][kLoc][i % 5]++;
				amountInt++;
			if (amountInt % 1000 == 0) {
				print("After " + amountInt + " fights: " + (double) winInt * 100 / amountInt + "%");
			}
			if (amountInt % 10000 == 0) {
				mutateAll();
			}
		}
		for (int i = 0; i < 5; i++) {
			int bestType = 0;
			int bestStance = 0;
			float bestRatio = 0;
			for (int j = 0; j < 2; j++)
				for (int k = 0; k < 3; k++) {
					if (result[k][j][i] * 100 / amount[k][j][i] > bestRatio) {
						bestRatio = result[k][j][i] * 100 / amount[k][j][i];
						bestType = k;
						bestStance = j;
					}
				}
			print(intTypeToString(setUps[bestType][bestStance][i].type) + " "
					+ setUps[bestType][bestStance][i].keepDistance + ": " + result[bestType][bestStance][i] + "/"
					+ amount[bestType][bestStance][i] + " " + setUps[bestType][bestStance][i].monster);
		}
		print("Total:" + amountInt + ". Percentage: " + (double) winInt * 100 / amountInt + "%");
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 2; j++) {
				for (int k = 0; k < 5; k++) {
					result[i][j][k] = 0;
					amount[i][j][k] = 0;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		while (true) {
			print("Welcome!");
			print("Chose one of the options:");
			print("1 - Statistic based simulation.");
			print("2 - GAs based simulation.");
			print("3 - Pick Strongest simulation.");
			print("4 - Statistic based simulation with monster mutations.");
			print("5 - GAs based simulation with monster mutations.");
			print("6 - Pick Strongest simulation with monster mutations.");
			print("9 - Reset Satistics.");
			print("0 - Exit.");
			int intChoice = in.nextInt();
			if (intChoice == 1)
			{
				statsBasedSimulation();
				break;
			}
			else if (intChoice == 2)
			{
				GAsBasedSimulation();
				break;
			}
			else if (intChoice == 3)
			{
				pickStrongestSimulation();
				break;
			}
			else if (intChoice == 4)
			{
				statsBasedSimulationWithMutations();
				break;
			}
			else if (intChoice == 5)
			{
				GAsBasedSimulationWithMutations();
				break;
			}
			else if (intChoice == 6)
			{
				pickStrongestSimulationWithMutations();
				break;
			}
			else if (intChoice == 9) {
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 2; j++) {
						for (int k = 0; k < 5; k++) {
							result[i][j][k] = 0;
							amount[i][j][k] = 0;
						}
					}
				}
			} else if (intChoice == 0)
				break;
			else
				continue;
		}
		in.close();
	}

	private static void GAsBasedSimulation() {
		setSetUps();
		// Randomisation of starting genes
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 5; j++) {
				genesArray[i][j] = (int) Math.floor(Math.random() * 6);
			}
		}
		int totalTests=0;
		int totalWin=0;
		for (int a = 0; a < 10000; a++) {
			int results[] = new int[10];
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 5; j++) {
					for (int k = 0; k < 10; k++) {
//						String dist = "";
//						int temp = genesArray[i][j] % 2;
//						if (temp == 1)
//							dist = "follow";
//						else
//							dist = "keep";
//						String monster = "";
//						switch (j) {
//						case 0:
//							monster = "Dragon";
//							break;
//						case 1:
//							monster = "Warrior";
//							break;
//						case 2:
//							monster = "Mage";
//							break;
//						case 3:
//							monster = "Archer";
//							break;
//						case 4:
//							monster = "Goblin";
//						}
						if (simulateFight(setUps[genesArray[i][j] / 2][genesArray[i][j] % 2][j])) {
							results[i]++;
							totalWin++;
						}
						totalTests++;
						if(totalTests%1000==0)
						{
							print("Total amount - " + totalTests + ". Percentage: " + (double)totalWin*100/totalTests + "%");
						}
					}
				}
			}
			sortGenes(results);
			crossovers();
			mutations();
		}
		for(int i=0;i<5;i++)
		{
			String fightClass;
			String fightStance;
			String fightMonster;
			if(genesArray[0][i]/2==0)
			{
				fightClass = "Warrior";
			}
			else if(genesArray[0][i]/2==1)
			{
				fightClass = "Mage";
			}
			else
			{
				fightClass = "Archer";
			}
			if(genesArray[0][i]%2==0)
			{
				fightStance = "follow";
			}
			else
			{
				fightStance = "keep";
			}
			if(i==0)
				fightMonster = "Dragon";
			else if(i==1)
				fightMonster = "Warrior";
			else if(i==2)
				fightMonster = "Mage";
			else if(i==3)
				fightMonster = "Archer";
			else
				fightMonster = "Goblin";
			print(fightMonster + ": " + fightClass + " " + fightStance);
		}
	}

	private static void GAsBasedSimulationWithMutations() {
		setSetUps();
		// Randomisation of starting genes
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 5; j++) {
				genesArray[i][j] = (int) Math.floor(Math.random() * 6);
			}
		}
		int totalTests=0;
		int totalWin=0;
		for (int a = 0; a < 10000; a++) {
			int results[] = new int[10];
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 5; j++) {
					for (int k = 0; k < 10; k++) {
						if (simulateFight(setUps[genesArray[i][j] / 2][genesArray[i][j] % 2][j])) {
							results[i]++;
							totalWin++;
						}
						chanceForMutation(setUps[genesArray[i][j] / 2][genesArray[i][j] % 2][j].monster);
						totalTests++;
						if(totalTests%1000==0)
						{
							print("Total amount - " + totalTests + ". Percentage: " + (double)totalWin*100/totalTests + "%");
						}
						if (totalTests % 10000 == 0) {
							mutateAll();
						}
					}
				}
			}
			sortGenes(results);
			crossovers();
			mutations();
		}
		for(int i=0;i<5;i++)
		{
			String fightClass;
			String fightStance;
			String fightMonster;
			if(genesArray[0][i]/2==0)
			{
				fightClass = "Warrior";
			}
			else if(genesArray[0][i]/2==1)
			{
				fightClass = "Mage";
			}
			else
			{
				fightClass = "Archer";
			}
			if(genesArray[0][i]%2==0)
			{
				fightStance = "follow";
			}
			else
			{
				fightStance = "keep";
			}
			if(i==0)
				fightMonster = "Dragon";
			else if(i==1)
				fightMonster = "Warrior";
			else if(i==2)
				fightMonster = "Mage";
			else if(i==3)
				fightMonster = "Archer";
			else
				fightMonster = "Goblin";
			print(fightMonster + ": " + fightClass + " " + fightStance);
		}
	}
	
	private static void mutations() {
		int[] mutationArray = {0,0,1,2};
		for(int i=6;i<10;i++)
		{
			boolean changed=true;
			while(changed)
			{
				for(int j=0;j<5;j++)
				{
					if((int)Math.ceil(Math.random()*5)==1)
					{
						genesArray[i][j]=(int)Math.floor(Math.random()*6);
						changed=false;
					}
					else
					{
						genesArray[i][j]=genesArray[mutationArray[i-6]][j];
					}
				}
			}
		}
	}

	private static Monster mutateMonster(Monster M){
		int rand = k(4);
		int rand3 = k(4);
		if(rand==1)
			M.setSpeed(k(5));
		else if(rand==2)
			M.setArmor(k(10));
		else if(rand==3)
			M.setDamage(k(10));
		else if(rand==4)
			M.setDistance(k(5));
		
		if(rand3==1)
			M.setIceRes(k(20));
		else if(rand3==2)
		{
			int rand2 = k(3);
			if(rand2==1)
				M.setPrimary("physical");
			else if(rand2==2)
				M.setPrimary("fire");
			else
				M.setPrimary("ice");
		}
		else if(rand3==3)
		{
			int rand2 = k(3);
			if(rand2==1)
				M.setSecondary("physical");
			else if(rand2==2)
				M.setSecondary("fire");
			else
				M.setSecondary("ice");
		}
		else if(rand3==4)
			M.setFireRes(k(20));
		return M;
	}
	
	private static void mutateAll(){
		data.getMonsters().get("Goblin").copy(mutateMonster(data.getMonsters().get("Goblin")));
		data.getMonsters().get("Archer").copy(mutateMonster(data.getMonsters().get("Archer")));
		data.getMonsters().get("Mage").copy(mutateMonster(data.getMonsters().get("Mage")));
		data.getMonsters().get("Warrior").copy(mutateMonster(data.getMonsters().get("Warrior")));
		data.getMonsters().get("Dragon").copy(mutateMonster(data.getMonsters().get("Dragon")));
		
	}
	
	private static void chanceForMutation(String m){
		int rand = (int) Math.ceil(Math.random()*50);
		if(rand==1)
		{
			data.getMonsters().get(m).copy(mutateMonster(data.getMonsters().get(m)));
		}
	}
	
	private static void crossovers() {
		int[] mother = { 0, 1, 2 };
		int[] father = { 1, 2, 0 };
		for (int i = 0; i < 3; i++) {
			int flipMother = (int) Math.ceil(Math.random() * 2);
			int flipBreak = (int) Math.ceil(Math.random() * 3);
			for (int j = 0; j < 5; j++) {
				if (flipMother % 2 == 0) {
					if (flipBreak > 0) {
						genesArray[i+3][j] = genesArray[mother[i]][j];
						flipBreak--;
					} else
						genesArray[i+3][j] = genesArray[father[i]][j];
				} else {
					if (flipBreak > 0) {
						genesArray[i+3][j] = genesArray[father[i]][j];
						flipBreak--;
					} else
						genesArray[3][j] = genesArray[mother[i]][j];
				}
			}
		}
	}

	private static void sortGenes(int[] res) {
		int temp = res[0];
		int[] tempArray = new int[5];
		for (int i = 0; i < 9; i++) {
			for (int j = i + 1; j < 10; j++) {
				if (res[j] > res[i]) {
					temp = res[i];
					for (int k = 0; k < 5; k++) {
						tempArray[k] = genesArray[i][k];
					}
					res[i] = res[j];
					for (int k = 0; k < 5; k++) {
						genesArray[i][k] = genesArray[j][k];
					}
					res[j] = temp;
					for (int k = 0; k < 5; k++) {
						genesArray[j][k] = tempArray[k];
					}
				}

			}
		}
	}
}
