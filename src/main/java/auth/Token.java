package auth;

public class Token {
	private String secureToken;

	public Token() {

	}

	public Token(String secureToken) {
		this.secureToken = secureToken;
	}

	public boolean validateToken(String testToken){
		if(testToken.compareTo(this.secureToken)==0)
			return true;
		else
			return false;
	}

	public String getToken(){
		return secureToken;
	}
}
