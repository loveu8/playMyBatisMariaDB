package pojo.game;

public enum PokemonDB {
	
	Pikachu(new Pokemon("皮卡丘",50,"打雷","5","電系")) ,
	
	Bulbasaur(new Pokemon("傑尼龜",60,"水炮","5","水系")) ,
	
	Charmander(new Pokemon("小火龍",40,"噴射火焰","5","火系")) ,
	
	Squirtle(new Pokemon("妙花種子",55,"飛葉快刀","5","草系")) 
	;
	
	Pokemon pokemon;
	
	PokemonDB(Pokemon pokemon){
		this.pokemon = pokemon;
	}
	
	public Pokemon getPokemon(){
		return this.pokemon;
	}
	
	// 找出神奇寶貝
	public static Pokemon findPokemon(String pokemonName) {
	    for (PokemonDB pokemon : PokemonDB.values()) {
	    	System.out.println(pokemon.toString());
	        if(pokemon.toString().equals(pokemonName)){
	           return pokemon.valueOf(pokemonName).getPokemon();
	        }
	    }
	    return null;
	}
}
