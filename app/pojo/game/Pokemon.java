package pojo.game;

public class Pokemon {
	
	// 怪獸名
	private String name;
	
	// 生命點數
	private int hp;
	
	// 技能
	private String skill;
	
	// 等級
	private String lv;
	
	// 屬性
	private String type;
	
	
	public Pokemon(String name, int hp , String skill , String lv ,String type) {
		this.name 	= name;
		this.hp   	= hp;
		this.skill 	= skill;
		this.lv   	= lv;
		this.type 	= type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}
	
	public String getLv() {
		return lv;
	}

	public void setLv(String lv) {
		this.lv = lv;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
