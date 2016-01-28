/**
 * 
 */
package ro.sci.group2.domain;



/**
 * This class mimics a password which is stored by its hashcode (and checked likewise).
 * <p>The actual {@link String string} which is the password is not stored effectively by security reasons.
 * @author Razvan Radu
 *
 */
class UserPassword {
	private String passText; 
	
	
	public UserPassword(String passText){
		
		this.passText=passText;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((passText == null) ? 0 : passText.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserPassword other = (UserPassword) obj;
		if (passText == null) {
			if (other.passText != null)
				return false;
		} else if (!passText.equals(other.passText))
			return false;
		return true;
	}

	
	
}
