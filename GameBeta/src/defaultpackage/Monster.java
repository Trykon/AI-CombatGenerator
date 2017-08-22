package defaultpackage;

public class Monster {

	public int HP;
	public String Name;
	public int Speed;
	public int Armor;
	public int Damage;
	public int FireRes;
	public int IceRes;
	public String Primary;
	public String Secondary;
	public int Distance;
	
	public Monster(String name) {
		this.Name = name;
		this.HP = 10;
		this.Speed = 3;
		this.Armor = 3;
		this.Damage = 3;
		this.FireRes = 0;
		this.IceRes = 0;
		this.Primary = "physical";
		this.Secondary = null;
		this.Distance = 0;
		}

	public Monster(String name,Monster other) {
		this.Name = name;
		this.HP = other.getHP();
		this.Speed = other.getSpeed();
		this.Armor = other.getArmor();
		this.Damage = other.getDamage();
		this.FireRes = other.getFireRes();
		this.IceRes = other.getIceRes();
		this.Primary = other.getPrimary();
		this.Secondary = other.getSecondary();
		this.Distance = other.getDistance();
		}
	
	public void copy(Monster other){
		this.HP = other.getHP();
		this.Speed = other.getSpeed();
		this.Armor = other.getArmor();
		this.Damage = other.getDamage();
		this.FireRes = other.getFireRes();
		this.IceRes = other.getIceRes();
		this.Primary = other.getPrimary();
		this.Secondary = other.getSecondary();
		this.Distance = other.getDistance();
	}
	
	public void SetAll(int hp, int speed, int armor, int damage, int fireres, int iceres, String primary, String secondary, int distance){
		this.HP = hp;
		this.Speed = speed;
		this.Armor = armor;
		this.Damage = damage;
		this.FireRes = fireres;
		this.IceRes = iceres;
		this.Primary = primary;
		this.Secondary = secondary;
		this.Distance = distance;
	}
	
	public String getPrimary() {
		return Primary;
	}

	public void setPrimary(String primary) {
		Primary = primary;
	}

	public String getSecondary() {
		return Secondary;
	}

	public void setSecondary(String secondary) {
		Secondary = secondary;
	}

	public int getDistance() {
		return Distance;
	}

	public void setDistance(int distance) {
		Distance = distance;
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int hP) {
		HP = hP;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getSpeed() {
		return Speed;
	}

	public void setSpeed(int speed) {
		Speed = speed;
	}

	public int getArmor() {
		return Armor;
	}

	public void setArmor(int armor) {
		Armor = armor;
	}

	public int getDamage() {
		return Damage;
	}

	public void setDamage(int damage) {
		Damage = damage;
	}

	public int getFireRes() {
		return FireRes;
	}

	public void setFireRes(int fireRes) {
		FireRes = fireRes;
	}

	public int getIceRes() {
		return IceRes;
	}

	public void setIceRes(int iceRes) {
		IceRes = iceRes;
	}

}
