package services;

import java.util.Collection;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Transactional
@Service
public class ActorService {
	
	// Managed repository
	@Autowired
	private ActorRepository actorRepository;
	
	public Actor findByUserAccount(UserAccount userAccount){
		Actor result;
		result = actorRepository.findByUserAccount(userAccount);
		return result;
	}
	/**Checks if the principal has the authority {@code authority}*/
	public void checkAuhtority(String authority){
		boolean authIsValid = Pattern.matches("^ADMIN|CUSTOMER|PRODUCER$", authority.toUpperCase());
		Assert.isTrue(authIsValid, "checkAuthority: input authority not valid!");
		
		UserAccount principal = LoginService.getPrincipal();
		Collection<Authority> auths = principal.getAuthorities();
		Authority auth = (Authority) auths.toArray()[0];
		Assert.notNull(auth, "checkAuthority: Authorit object null");
		String principalAuth = auth.getAuthority();
		Assert.isTrue(principalAuth.equalsIgnoreCase(authority), "checkAuthority: check failed!");
	}
	
	/**Check if the principal exists (is authenticated)
	 * If principal is not authenticated, an assert in getPrincipal will be triggered
	 * and an IllegalArgumentException will be raised*/
	public void checkIsAuthenticated(){
		LoginService.getPrincipal();
	}
}
