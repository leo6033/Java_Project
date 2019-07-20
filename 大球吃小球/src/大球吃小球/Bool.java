package ¥Û«Ú≥‘–°«Ú3;

public class Bool {
	private boolean[] bool=new boolean[] {false,false,false,false};
	
	public Bool() {
		
	}
	public Bool(boolean[] bool)
	{
		this.bool=bool;
	}
	public void change_bool(int x,boolean bool)
	{
		this.bool[x]=bool;
	}
	public boolean getbool(int x)
	{
		return this.bool[x];
	}
}
